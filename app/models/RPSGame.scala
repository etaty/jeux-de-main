package models

import play.api.libs.json.Json


case class RPSGame(p1: Hand, p2: Hand) {
  lazy val result = RPSResult.compute(p1, p2)
}

object RPSGame {
  def create(p1: Hand, p2: Hand): RPSGame = {
    RPSGame(p1, p2)
  }

  implicit val formatRPSGame = Json.format[RPSGame]

}