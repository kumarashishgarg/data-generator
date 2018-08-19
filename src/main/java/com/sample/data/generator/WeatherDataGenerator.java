package com.sample.data.generator;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridEnvelope2D;
import org.geotools.coverage.grid.GridGeometry2D;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.OverviewPolicy;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.geometry.Envelope2D;
import org.opengis.parameter.GeneralParameterValue;
import org.opengis.parameter.ParameterValue;
import org.opengis.referencing.operation.TransformException;

import com.sample.data.generator.exception.DataGeneratorException;
import com.sample.data.generator.util.ConfigReader;
import com.sample.data.generator.util.Constants;
import com.sample.data.generator.util.IATAUtil;
import com.sample.data.generator.util.RandomUtil;

/**
 * This is the main entry point for the weather data generator application class
 * 
 * @author akuma921
 *
 */
public class WeatherDataGenerator {

	private static final Log LOGGER = LogFactory.getLog(WeatherDataGenerator.class);

	public static void main(String[] args) {
		try {
			if (args.length != 1) {
				throw new DataGeneratorException("Invalid Number of argument");
			}
			String configFile = args[0];
			ConfigReader.readConfig(configFile);
			generateData();
		} catch (DataGeneratorException | IOException exception) {
			LOGGER.error("Exception while running weather data generator application", exception);
			System.exit(0);
		}
	}

	/**
	 * Generate weather data based on the configuration provided
	 * 
	 * @throws DataGeneratorException
	 */
	public static void generateData() throws DataGeneratorException {
		System.out.println("Data Generation Starts at " + new Date());
		ParameterValue<OverviewPolicy> policy = AbstractGridFormat.OVERVIEW_POLICY.createValue();
		policy.setValue(OverviewPolicy.IGNORE);

		// this will basically read 4 tiles worth of data at once from the disk...
		ParameterValue<String> gridsize = AbstractGridFormat.SUGGESTED_TILE_SIZE.createValue();
		// gridsize.setValue(512 * 4 + "," + 512);

		// Setting read type: use JAI ImageRead (true) or ImageReaders read methods
		// (false)
		ParameterValue<Boolean> useJaiRead = AbstractGridFormat.USE_JAI_IMAGEREAD.createValue();
		useJaiRead.setValue(true);

		// reader.read(new GeneralParameterValue[] { policy, gridsize, useJaiRead });
		File file = new File(ConfigReader.getProperties().getProperty(Constants.INPUT_TOPOGRAPHY_FILE));
		BufferedWriter bufferedWriter = null;
		try {
			GridCoverage2D image = new GeoTiffReader(file)
					.read(new GeneralParameterValue[] { policy, gridsize, useJaiRead });
			Rectangle2D bounds2D = image.getEnvelope2D().getBounds2D();
			bounds2D.getCenterX();
			// calculate zoom level for the image
			GridGeometry2D geometry = image.getGridGeometry();

			BufferedImage img = ImageIO.read(file);
			// ColorModel colorModel = img.getColorModel(
			WritableRaster raster = img.getRaster();

			int numBands = raster.getNumBands();

			int width = img.getWidth();
			int height = img.getHeight();
			IATAUtil.loadCityMap();
			DecimalFormat decimalFormat = new DecimalFormat(Constants.DOUBLE_FORMAT);
			int pixel_precision = Integer.parseInt(ConfigReader.getProperties().getProperty(Constants.PIXEL_READ_PRECISION));
			bufferedWriter = new BufferedWriter(new FileWriter(ConfigReader.getProperties().getProperty(Constants.OUTPUT_DATA_FILE)));
			for (int i = 0; i < width; i=i+pixel_precision) {
				for (int j = 0; j < height; j=j+pixel_precision) {
					double[] latlon = geo(geometry, i, j);
					double lat = latlon[0];
					double lon = latlon[1];

					Double s = 0d;
					String originalBands = "";
					for (int k = 0; k < numBands; k++) {
						double d = raster.getSampleDouble(i, j, k);
						originalBands += d + ",";
						s += d;
					}
					originalBands = originalBands.substring(0, originalBands.length() - 1);
					if (s.compareTo(0d) == 0) {
						continue;
					}
					String location = IATAUtil.getCity(lat, lon);
					Boolean includeDefault = Boolean.parseBoolean(
							ConfigReader.getProperties().getProperty(Constants.INCLUDE_RECORD_WITHOUT_LOCATION_NAME));
					if (!includeDefault && location.equals(Constants.DEFAULT_CITY)) {
						continue;
					}
					Object[] climate = RandomUtil.randomTemperature();
					String recordData = location + Constants.SEPARATOR + lat + "," + lon + Constants.SEPARATOR
							+ RandomUtil.randomTime() + Constants.SEPARATOR + climate[0] + Constants.SEPARATOR
							+ Double.valueOf(decimalFormat.format(climate[1])) + Constants.SEPARATOR
							+ Double.valueOf(decimalFormat.format(climate[2])) + Constants.SEPARATOR
							+ Double.valueOf(decimalFormat.format(climate[3])) + Constants.LINE_SEPARATOR;
					//System.out.println(recordData);
					bufferedWriter.write(recordData);
				}
			}
		} catch (Exception exception) {
			LOGGER.error("Exception while parsing tif file", exception);
			throw new DataGeneratorException(exception.getMessage());
		} finally {
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				LOGGER.error("Exception while closing the output file", e);
			}
		}
		System.out.println("Data Generation Ends at " + new Date());
	}

	/**
	 * Return the latitude/longitude for the given pixel points
	 * 
	 * @param geometry
	 * @param x
	 * @param y
	 * @return double[]
	 * @throws TransformException
	 */
	private static double[] geo(GridGeometry2D geometry, int x, int y) throws TransformException {
		Envelope2D pixelEnvelop = geometry.gridToWorld(new GridEnvelope2D(x, y, 1, 1));
		return new double[] { pixelEnvelop.getCenterY(), pixelEnvelop.getCenterX() };
	}

}
