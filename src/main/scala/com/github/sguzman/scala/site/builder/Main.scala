package com.github.sguzman.scala.site.builder

object Main {
  def main(args: Array[String]): Unit = {
    val urls = Schemer.args(args)
      .par
      .flatMap(Schemer.uri({t => {}})(_)).distinct
      .flatMap(Schemer.uri(println)(_)).distinct
      .toList.sorted

    urls foreach println
    println(urls.length)
  }
}
