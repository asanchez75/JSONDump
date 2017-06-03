package org.dbpedia.convert.api

import java.io.FileInputStream
import java.util.Properties

object Configuration {

  
  private val properties = new Properties()
  properties.load(new FileInputStream(Constants.CONFIG_DIR))
	 
  lazy val inputFile: String = properties.getProperty(Constants.DATA_INPUT);
  
}