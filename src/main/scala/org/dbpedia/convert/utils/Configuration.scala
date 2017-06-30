package org.dbpedia.convert.utils

import java.io.FileInputStream
import java.util.Properties
import org.apache.spark.SparkFiles

object Configuration {

  private val properties = new Properties()
  properties.load(new FileInputStream(SparkFiles.get(Constants.CONFIG_DIR)))
	 
  lazy val inputFiles: String = properties.getProperty(Constants.DATA_INPUT);
  
}