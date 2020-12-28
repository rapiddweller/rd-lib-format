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
package com.rapiddweller.formats.dot;

/**
 * Enumerates Dot node shapes.
 * Created: 24.05.2014 06:51:37
 * @since 0.8.2
 * @author Volker Bergmann
 */

public enum NodeShape {
	ellipse, record, box, rect,
	circle, polygon,
	point, egg, triangle, plaintext, diamond, trapezium, parallelogram, house, pentagon, hexagon, septagon, octagon,
	doublecircle, doubleoctagon, tripleoctagon, invtriangle, invtrapezium, invhouse, Mdiamond, Msquare, Mcircle,
	note, tab, folder, box3d, component
}
