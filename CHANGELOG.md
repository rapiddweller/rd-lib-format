# Release 1.1.1-jdk-11

## Release Highlights

N/A

## Important Notes

N/A

## Breaking Changes

* upgraded org.apache.poi needed some more adjustments, added missing OpenXML Schema dependencies and switched default
  create workbook format from HSSF to XSSF new standard.

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


