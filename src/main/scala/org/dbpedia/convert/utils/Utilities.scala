package org.dbpedia.convert.utils

import java.io.File

object Utils {

  val merge = (x: List[String], y: List[String]) => {
    x ::: (y)
  }

  val listFiles = (x: File) => {
    if (x.isFile())
      List[String](x.toString())
    else if (x.isDirectory())
      getFilesFromDirectory(x.toString())
    else
      List[String]()
  }

  /**
   * Return all the files reachable from the paths provided in the list.
   * If an item in the list is a file simple append it to result,
   * if an item is a directory append all the files in it to the result.
   */
  def getFiles(filesOrDirs: List[File]): List[String] = {
    filesOrDirs.map(listFiles).reduce(merge)
  }

  /**
   * Return all the files in the directory (using recursively search).
   */
  def getFilesFromDirectory(dir: String): List[String] = {
    val dirContents = new File(dir).listFiles()

    if (dirContents.isEmpty)
      List[String]()
    else
      dirContents.toList.map(listFiles).filter(!_.isEmpty).reduce(merge)

  }

  def main(args: Array[String]) = {

    val filesStr: String = "/media/ryan/FC4E37634E3715BC/DBPedia/JSONDump/"
    val fileList: List[File] = filesStr.split(",").map(x => new File(x.trim())).toList

    val result = getFiles(fileList)
    result.foreach(f => println(f))

  }
}