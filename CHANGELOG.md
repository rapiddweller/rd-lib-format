# Release 1.1.7-jdk-11

Changes are related to rapiddweller-benerator-ce 3.2.1 release check CHANGE_LOG.md for more details.

---

# Release 1.1.6-jdk-11

Changes are related to rapiddweller-benerator-ce 3.2.0 release check CHANGE_LOG.md for more details.

---

# Release 1.1.5-jdk-11

Changes are related to rapiddweller-benerator-ce 3.0.0 release check CHANGE_LOG.md for more details.


---

# Release 1.1.4-jdk-11

## Release Highlights

* Removed IOException from the signature of the init() method
* Upgraded to rd-lib-common 1.1.3-jdk-11
* Switched back to slf4j
* New static convenience method writeTable()

---

# Release 1.1.3-jdk-11

## Release Highlights

* Introduce checkstyle
* upgrade dependencies rd-lib-common, log4j, JUnit
* improve pom
* refactor JDK 11
* remove useless tests

---

# Release 1.1.2-jdk-11

## Release Highlights

* Upgrade rd-lib-commons to 1.1.1
* Removed unnecessary GraalVM dependencies

## Important Notes

N/A

## Breaking Changes

N/A

---

# Release 1.1.1-jdk-11

## Release Highlights

N/A

## Important Notes

N/A

## Breaking Changes

* upgraded org.apache.poi needed some more adjustments, added missing OpenXML Schema dependencies and switched default
  create workbook format from HSSF to XSSF new standard.

---

# Release 1.1.0-jdk-11

## Release Highlights

* several new test cases to assure refactoring is more safe
* migrated to Java 11 language features

## Important Notes

N/A

## Breaking Changes

* upgraded org.apache.poi from 3.x to 5.0.0 (Excel handling)
* removed Oracle Nashorn script engine and replaced it with GraalVM (https://github.com/oracle/graal)
* reworked ScriptUtils and ScriptFactory because Oracle Nashorn was hard implemented


