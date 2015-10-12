package models.rockpaperscissors

import models.{Game, GameEngine, Round}
import play.api.libs.json._

object RockPaperScissorsEngine extends GameEngine[Hand] {
  override def choices: Seq[Hand] = Hand.hands

  override def createNewGame: Game[Hand] = RockPaperScissors()

  override def id: String = "RockPaperScissors"
}

case class RockPaperScissors(rounds: Seq[Round[Hand]] = Seq.empty) extends Game[Hand] {
  override def addRound(g: Round[Hand]): Game[Hand] = copy(rounds = rounds :+ g)

  override def gameEngine: GameEngine[Hand] = RockPaperScissorsEngine

  override implicit def jsonformat: Format[Hand] = Hand.formatHand
}

object RockPaperScissors {
  implicit val writeRockPaperScissors: Writes[RockPaperScissors] = new Writes[RockPaperScissors] {
    override def writes(o: RockPaperScissors): JsValue = {
      Json.obj(
        "game" -> "rockpaperscissors",
        "rounds" -> Json.toJson(o.rounds)
      )
    }
  }

  implicit val readRockPaperScissors: Reads[RockPaperScissors] = Json.reads[RockPaperScissors]

}