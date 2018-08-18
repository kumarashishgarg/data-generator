# Weather Data Generator

This is test data generator utility application. It uses topography geo file (in .tif format) to calculate the latitude/longitude information. Further it use IATA service to tag location (City, Country) for the generated latitude and longitudes.

## Application Packaging 

Application can be packaged with a simple maven command: 

```
mvn clean package
```

If you wish to skip the test cases, to create a faster build:

```
mvn clean package -DskipTest
```
