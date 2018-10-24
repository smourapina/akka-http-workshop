package io.github.jlprat.akka.http.workshop.actors

import akka.actor.typed.ActorRef
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.{Directives, HttpApp, Route}
import io.github.jlprat.akka.http.workshop.actors.CatalogRouteExample.CatalogRoute
import io.github.jlprat.akka.http.workshop.actors.Model.{Author, Book}
import spray.json.DefaultJsonProtocol

/**
  * This class showcases how to integrate with Akka Typed
  * Created by @jlprat on 20/04/2017.
  */
class CatalogRouteExample(override val catalogBehavior: ActorRef[CatalogBehavior.Command]) extends HttpApp with CatalogRoute {
  override protected[actors] def routes: Route = catalogRoute
}

object CatalogRouteExample {

  trait CatalogRoute extends Directives with JsonProtocol {

    val catalogBehavior: ActorRef[CatalogBehavior.Command]
    val catalogRoute: Route = path("???"){complete("")}
  }

  trait JsonProtocol extends SprayJsonSupport with DefaultJsonProtocol {
    implicit val authorFormat = jsonFormat1(Author)
    implicit val bookFormat = jsonFormat4(Book)
  }

  def main(args: Array[String]): Unit = {
    new CatalogRouteExample().startServer("localhost", 9000)
  }
}
