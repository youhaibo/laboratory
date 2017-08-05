package me.hbyou.application.labratory.scala

import me.hbyou.application.labratory.scala.io.ScalaIoPractice

/**
  * Created by Tony(YouHaibo) on 2017-08-05 16:42.
  */
object ScalaLaboratoryApplication {

  def main(args: Array[String]): Unit = {
    println("Hello Scala.")
    val array:Array[String] = new Array(5)
    array(0) = "you"
    //array.flatMap()
    var d:Double = 100.0
    var d1 = d

    d1 = 200
    println(d)
    println(d1)

    val ra:Range = 1 to 10 by 2

    ra.foreach(println)

    val ddd = new ScalaIoPractice
    ddd.readFile()
  }

}
