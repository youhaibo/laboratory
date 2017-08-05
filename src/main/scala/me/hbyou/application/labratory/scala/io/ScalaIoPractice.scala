package me.hbyou.application.labratory.scala.io

import scala.io.Source

/**
  * @author Tony(YouHaibo)
  * @version 1.0 2017-08-05 20:47
  *
  */
class ScalaIoPractice {

  def readFile(): Unit = {
    val file = "C:\\Users\\Tony\\work\\code\\laboratory\\README.md"
    Source.fromFile(file).getLines().foreach(println)
  }

}
