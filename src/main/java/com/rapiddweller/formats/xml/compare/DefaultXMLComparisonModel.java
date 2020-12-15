/*
 * Copyright (C) 2011-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rapiddweller.formats.xml.compare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import com.rapiddweller.commons.ArrayBuilder;
import com.rapiddweller.commons.ConfigurationError;
import com.rapiddweller.commons.NullSafeComparator;
import com.rapiddweller.commons.StringUtil;
import com.rapiddweller.commons.xml.XMLUtil;
import com.rapiddweller.commons.xml.XPathUtil;
import com.rapiddweller.formats.compare.KeyExpression;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/**
 * XML comparison model based on element names.
 * Created: 03.06.2014 13:46:59
 * @since 1.0.5
 * @author Volker Bergmann
 */

public class DefaultXMLComparisonModel extends AbstractXMLComparisonModel {
	
	private List<KeyExpression> keyExpressions;
	private Map<Element, String> keys;
	private boolean initialized;
	
	public DefaultXMLComparisonModel() {
		this.keyExpressions = new ArrayList<KeyExpression>();
		this.keys = new HashMap<Element, String>();
		this.initialized = false;
	}
	
	@Override
	public void addKeyExpression(String locator, String keyExpression) {
		this.keyExpressions.add(new KeyExpression(locator, keyExpression));
	}
	
	@Override
	public List<KeyExpression> getKeyExpressions() {
		return keyExpressions;
	}
	
	@Override
	public void init(Document document1, Document document2) {
		try {
			this.keys.clear();
			for (KeyExpression keyExpression : keyExpressions) {
				if (document1 != null) {
					List<Element> elements1 = XPathUtil.queryElements(document1, keyExpression.getLocator());
					for (Element element : elements1) {
						String key1 = XPathUtil.queryString(element, keyExpression.getExpression());
						this.keys.put(element, key1);
					}
				}
				if (document2 != null) {
					List<Element> elements2 = XPathUtil.queryElements(document2, keyExpression.getLocator());
					for (Element element : elements2) {
						String key2 = XPathUtil.queryString(element, keyExpression.getExpression());
						this.keys.put(element, key2);
					}
				}
			}
			this.initialized = true;
		} catch (XPathExpressionException e) {
			this.keys.clear();
			throw new ConfigurationError("Error evaluating key expression", e);
		}
	}
	
	@Override
	public boolean equal(Object o1, Object o2) {
		assertInitialized();
		return equalNodes((Node) o1, (Node) o2);
	}
	
	@Override
	public boolean correspond(Object o1, Object o2) {
		assertInitialized();
		Node n1 = (Node) o1;
		Node n2 = (Node) o2;
		if (n1 instanceof Text && n2 instanceof Text)
			return true;
		if (n1 instanceof Element && n2 instanceof Element) {
			Element e1 = (Element) n1;
			Element e2 = (Element) n2;
			String ln1 = e1.getLocalName();
			String ln2 = e2.getLocalName();
			if (ln1 != null) {
				if (!ln1.equals(ln2))
					return false;
			} else if (ln2 != null) {
				return false;
			}
			String key1 = keys.get(e1);
			if (key1 == null)
				return true;
			return (key1.equals(keys.get(e2)));
		}
		return (n1.getNodeName().equals(n2.getNodeName()));
	}
	
	@Override
	public String subPath(Object[] array, int index) {
		assertInitialized();
		Node node = (Node) array[index];
		String name = name(node);
		if (name.length() == 0) {
			return "[" + (index + 1) + "]";
		} else {
			int nameOccurrences = 0;
			int indexAmongHomonyms = -1;
			for (int i = 0; i < array.length; i++) {
				if (name((Node) array[i]).equals(name)) {
					if (i == index)
						indexAmongHomonyms = nameOccurrences;
					nameOccurrences++;
				}
			}
			String result = "/" + name;
			if (nameOccurrences > 1)
				result += "[" + (indexAmongHomonyms + 1) + "]";
			return result;
		}
	}
	
	public boolean equalNodes(Node n1, Node n2) {
		assertInitialized();
		// compare node names
		if (n1 instanceof Element && n2 instanceof Element) {
			if (!elementNamesMatch((Element) n1, (Element) n2))
				return false;
		} else if (!n1.getNodeName().equals(n2.getNodeName())) {
			return false;
		}
		
		// compare element attributes
		if (n1 instanceof Element) {
			if (n2 instanceof Element) {
				Element e1 = (Element) n1;
				Element e2 = (Element) n2;
				if (!XMLUtil.getAttributes(e1).equals(XMLUtil.getAttributes(e2)))
					return false;
			} else {
				return false;
			}
		}
		
		// compare child nodes
		Node[] c1 = childNodes(n1);
		Node[] c2 = childNodes(n2);
		if (c1.length != c2.length)
			return false;
		for (int i = 0; i < c1.length; i++)
			if (!equalNodes(c1[i], c2[i]))
				return false;
		return n1.getTextContent().equals(n2.getTextContent());
	}

	@Override
	public Node[] childNodes(Node parent) {
		assertInitialized();
		if (!(parent instanceof Element))
			return new Node[0];
        NodeList childNodes = parent.getChildNodes();
        ArrayBuilder<Node> builder = new ArrayBuilder<Node>(Node.class);
        for (int i = 0; i < childNodes.getLength(); i++) {
        	Node child = childNodes.item(i);
        	if (child instanceof Element) {
        		builder.add(child);
        	} else if (child instanceof ProcessingInstruction) {
        		if (processingInstructionRelevant)
        			builder.add(child);
        	} else if (child instanceof CDATASection) {
        		if (cdataRelevant)
        			builder.add(child);
        		else
        			builder.add(parent.getOwnerDocument().createTextNode(child.getTextContent()));
        	} else if (child instanceof Text) {
        		if (StringUtil.trim(child.getTextContent()).isEmpty()) {
        			if (whitespaceRelevant) // white space
        				builder.add(child);
        		} else {
        			// text with content
        			builder.add(child);
        		}
        	} else if (child instanceof Comment) {
        		if (commentRelevant)
        			builder.add(child);
        	} else {
        		throw new UnsupportedOperationException("Unsupported node type: " + child.getClass().getName());
        	}
        }
        return builder.toArray();
    }

	private void assertInitialized() {
		if (!initialized)
			throw new IllegalStateException(getClass().getName() + " is not initialized, call the init() method before using it");
	}

	private boolean elementNamesMatch(Element e1, Element e2) {
		String ln1 = e1.getLocalName();
		String ln2 = e2.getLocalName();
		if (ln1 != null) {
			if (!ln1.equals(ln2))
				return false;
		} else if (ln2 != null) {
			return false;
		}
		if (namespaceRelevant && !NullSafeComparator.equals(e1.getNamespaceURI(), e2.getNamespaceURI()))
			return false;
		return true;
	}
	
	private static String name(Node node) {
		if (node instanceof Text)
			return "text()";
		else if (node instanceof Comment)
			return "comment()";
		else
			return node.getNodeName();
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [keyExpressions=" + keyExpressions + "]";
	}

}
