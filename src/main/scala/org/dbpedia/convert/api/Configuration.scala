package org.dbpedia.convert.api

import java.io.FileInputStream
import java.util.Properties
import org.apache.spark.SparkFiles

object Configuration {

  
  private val properties = new Properties()
  properties.load(new FileInputStream(SparkFiles.get(Constants.CONFIG_DIR)))
	 
  lazy val inputFile: String = properties.getProperty(Constants.DATA_INPUT);
  lazy val outputFile: String = properties.getProperty(Constants.DATA_OUTPUT);
  
}