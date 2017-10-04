package org.dbpedia.convert;
import org.apache.jena.rdf.model.ResourceFactory
import org.apache.spark.{SparkConf, SparkContext}
import org.dbpedia.extraction.destinations.Quad
import org.dbpedia.convert.api.Configuration

object Example {
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setAppName("sort-example")
      .registerKryoClasses(Array(Class.forName("org.dbpedia.extraction.destinations.Quad")));
    // Create a Scala Spark Context.
    val sc = new SparkContext(conf)
    // Load our input data.
    val input =  sc.textFile(Configuration.inputFile)
    input
    input
      //Sort all lines as strings
      //.sortBy(s => s)
      // convert lines to Quads
      .filter(s => !s.startsWith("#"))
      .flatMap( str => {
        try {
          str match {
            case Quad(quad) => Some(quad)
            case _ => None
          }
        } catch {
          case e: Exception => None
        }
      }
      )
      .filter(_.subject.nonEmpty)
      .map( q => JsonVal(q.subject, getJsonKey(q), q.value))
      .sortBy(_.subject)
      .groupBy(_.subject)
      .map ( vals => jsonValsToJson(vals._1, vals._2))

      .saveAsTextFile("/home/dimitris/data/dbpedia/export.jsonl")


  }

  case class JsonVal (subject: String, key: String, value: String)
  case class JsonVals (subject: String, key: String, values: Seq[String])

  def getJsonKey(quad: Quad): String = {
    val key = ResourceFactory.createProperty(quad.predicate).getLocalName
    if (quad.datatype != null && quad.datatype.equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#langString")) key + "_" + quad.language else key
  }

  import com.fasterxml.jackson.databind.ObjectMapper
  import com.fasterxml.jackson.module.scala.DefaultScalaModule
  val objectMapper = new ObjectMapper()
  objectMapper.registerModule(DefaultScalaModule)

  def jsonValsToJson(subject: String, vals: Iterable[JsonVal]): String = {

    val values: Map[String, Any] = vals.toList
      .map(v => (v.key, v.value))
      .sortBy(_._1)
      .groupBy(_._1)
      .map(g =>
        g._1 -> {if (g._2.size == 1) g._2.head._2 else  g._2.map(_._2).distinct.toSeq})


    val jstring  = objectMapper.writeValueAsString( Map("@id" -> subject) ++ values)
    println(jstring)
    jstring.replaceAll("\n", "")
  }
}