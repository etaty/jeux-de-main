package models

import play.api.libs.json._
import play.api.libs.functional.syntax._


case class Round[T: Format](p1: T, p2: T)

object Round {
  implicit def formatRPSGame[T](implicit fmt: Format[T]): Format[Round[T]] = {
    ((JsPath \ "p1").format[T] and
      (JsPath \ "p2").format[T]
      )(Round.apply[T], unlift(Round.unapply[T]))
  }
}