package com.github.sguzman.scala.site.builder

import java.net.URL

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.elementList

import scala.util.{Failure, Success}
import scalaj.http.Http

object Schemer {
  def url(args: Array[String]) =
    Config().parse(args, init = CmdArgs()) match {
      case Some(v) => v.url
      case None => throw new Exception()
    }

  def port(u: URL) = if (u.getPort == -1) "" else ":" ++ u.getPort.toString
  def cons(u: URL) = s"${u.getProtocol}://${u.getAuthority}${port(u)}"
  def consp(u: URL) = s"${u.getProtocol}://${u.getAuthority}${port(u)}${u.getPath}"

  @scala.annotation.tailrec
  def http(u: URL): String = util.Try(Http(u.toString).asString) match {
    case Success(v) => v.body
    case Failure(_) => http(u)
  }

  def scrape(u: URL) = JsoupBrowser()
    .parseString(http(u))
    .>>(elementList("""a[href]"""))
    .map(_.attr("href"))
    .map(t => if (t.startsWith("/")) cons(u) ++ t else t)
    .map(t => if (t.startsWith("#")) consp(u) else t)

  def args(arg: Array[String]) = scrape(new URL(url(arg)))
  def uri(func: String => Unit = println) (str: String) = {
    func(str)
    scrape(new URL(str))
  }
}
