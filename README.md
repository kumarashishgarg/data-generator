# Weather Data Generator

This is test data generator utility application. It uses topography geo file (in .tif format) to calculate the latitude/longitude information. Further it use IATA service to tag location (City, Country) for the generated latitude and longitudes.

## Application Packaging 

Application can be packaged with a simple maven command.

```
mvn clean package
```

If you wish to skip the test cases, to create a faster build.

```
mvn clean package -DskipTest
```

## Run

Application jar can be run with the below command.

```
java -jar data-generator-0.0.1.jar <config.properties>
```

## Configuration file

To run the application, we need to configure certain configuration in form of a properties file. This file is the only parameter required to run this application. A sample file can be referred from the `src/main/resources` folder.

Below are required properties

```
INPUT_TOPOGRAPHY_FILE - Input Topography file in .tif format.

OUTPUT_DATA_FILE - Output file location, where the results need to be stored

INCLUDE_RECORD_WITHOUT_LOCATION_NAME - A flag to indicate whether we need to include the records for which we couldn't find the location (city, country) for given latitude/longitude.

IATA_FILE - IATA file to map city(and country code) to IATA code

IATA_SERVICE - IATA service rest URL

TIME_RANGE - Time range in which random record time should be generated

WEATHER_CONDITIONS - Allowed weather conditions

RAIN_CONDITION - climate range for rainy weather

SNOW_CONDITION - climate range for snow weather

SUNNY_CONDITION - climate range for sunny weather
```