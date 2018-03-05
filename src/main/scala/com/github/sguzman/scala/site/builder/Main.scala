package com.github.sguzman.scala.site.builder

object Main {
  def main(args: Array[String]): Unit =
    Schemer.args(args).par.map(Schemer.uri)
      .toList foreach println
}
