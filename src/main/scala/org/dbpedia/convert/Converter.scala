package org.dbpedia.convert

import org.dbpedia.convert.utils.{ Utils, Configuration }
import java.io.File
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.dbpedia.extraction.destinations.Quad

object Converter {
  def main(args: Array[String]) {
    // spark app config
    val conf = new SparkConf()
      .setAppName("sort-example")
      .registerKryoClasses(Array(Class.forName("org.dbpedia.extraction.destinations.Quad")));
    // Create a Scala Spark Context.
    val sc = new SparkContext(conf)

    // list all the files from configuration
    val paths: String = Configuration.inputFiles
    val pathsList: List[File] = paths.split(",").map(x => new File(x.trim())).toList

    val files = Utils.getFiles(pathsList)

    files.foreach((file: String) => {
      val input = sc.textFile(file)
      
      // TODO: make the code more elegant
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
        .foreach {
          case (subject, quads) => {
            println(subject)
            quads.foreach(println(_))
          }
        }
      
      // TODO add code to convert to JSON-ld
    })
  }

}