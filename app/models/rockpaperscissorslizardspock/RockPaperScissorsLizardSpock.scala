package models.rockpaperscissorslizardspock

import models.{Game, GameEngine, Round}
import play.api.libs.json._

object RockPaperScissorsLizardSpockEngine extends GameEngine[Hand] {
  override def choices: Seq[Hand] = Hand.hands

  override def createNewGame: Game[Hand] = RockPaperScissorsLizardSpock()

  override def id: String = "RockPaperScissorsLizardSpock"
}

case class RockPaperScissorsLizardSpock(rounds: Seq[Round[Hand]] = Seq.empty) extends Game[Hand] {
  override def addRound(g: Round[Hand]): Game[Hand] = copy(rounds = rounds :+ g)

  override def gameEngine: GameEngine[Hand] = RockPaperScissorsLizardSpockEngine

  override implicit def jsonformat: Format[Hand] = Hand.formatHand
}

object RockPaperScissorsLizardSpock {
  implicit val writeRockPaperScissorsLizardSpock: Writes[RockPaperScissorsLizardSpock] = new Writes[RockPaperScissorsLizardSpock] {
    override def writes(o: RockPaperScissorsLizardSpock): JsValue = {
      Json.obj(
        "game" -> "rockpaperscissorslizardspock",
        "rounds" -> Json.toJson(o.rounds)
      )
    }
  }

  implicit val readRockPaperScissorsLizardSpock: Reads[RockPaperScissorsLizardSpock] = Json.reads[RockPaperScissorsLizardSpock]
}
