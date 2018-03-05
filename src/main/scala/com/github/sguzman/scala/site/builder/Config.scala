package com.github.sguzman.scala.site.builder

case class Config(title: String = "SiteBuilder") extends scopt.OptionParser[CmdArgs](title) {
  head(title, "1.0")

  opt[String]('u', "url")
    .text("URL")
    .required()
    .valueName("<url>")
    .minOccurs(1)
    .maxOccurs(1)
    .action((x, c) => c.copy(url = x))

  opt[Int]('d', "depth")
    .text("Depth")
    .required()
    .valueName("<dep>")
    .minOccurs(1)
    .maxOccurs(1)
    .action((x, c) => c.copy(depth = x))

  help("help")
    .text("this menu")
}