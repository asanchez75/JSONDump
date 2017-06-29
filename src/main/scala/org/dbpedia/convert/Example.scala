package org.dbpedia.convert;
import org.apache.spark.{SparkConf, SparkContext}
import org.dbpedia.extraction.destinations.Quad

import org.dbpedia.convert.utils.Configuration

// Use this code for reference
object Example {
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setAppName("sort-example")
      .registerKryoClasses(Array(Class.forName("org.dbpedia.extraction.destinations.Quad")));
    // Create a Scala Spark Context.
    val sc = new SparkContext(conf)
    // Load our input data.
    val input =  sc.textFile(Configuration.inputFiles)
    input
      //Sort all lines as strings
      .sortBy(identity)
      // convert lines to Quads
      .flatMap(line => {
      line match {
        case Quad(quad) => Some(quad)
        case _ => None
      }
    })
      // Group quads by subject
      .groupBy(_.subject)
      // have them again sorted to save them as sorted
      .sortBy(_._1)
      // quads should be converted to json-ld
      .foreach{
      case (subject, quads) => {
        println( subject)
        quads.foreach(println(_))
      }}
  }
}