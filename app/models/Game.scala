package models

import models.rockpaperscissors.RockPaperScissors
import models.rockpaperscissorslizardspock.RockPaperScissorsLizardSpock
import play.api.libs.json._



trait Game[T] {
  implicit def jsonformat: Format[T]

  def rounds: Seq[Round[T]]

  def gameEngine: GameEngine[T]

  lazy val (won, tie, lost) = {
    val wtl = WonTieLoss(this)
    (wtl.won, wtl.tie, wtl.lost)
  }

  def playNewRound(choice: String): Option[Game[T]] = gameEngine.validate(choice).map(addRound)

  def addRound(choice: T): Game[T] = addRound(Round[T](choice, gameEngine.randomChoice))

  def addComputerRound(): Game[T] = addRound(Round[T](gameEngine.randomChoice, gameEngine.randomChoice))

  def addRound(g: Round[T]): Game[T]
}

object Game {
  def writeGame: PartialFunction[Game[_], JsValue] = {
    case x: RockPaperScissors => Json.toJson(x)
    case x: RockPaperScissorsLizardSpock => Json.toJson(x)
  }

  def readGame(json: JsValue): JsResult[Game[_]] = {
    (json \ "game").validate[String].flatMap {
      case "rockpaperscissors" => json.validate[RockPaperScissors]
      case "rockpaperscissorslizardspock" => json.validate[RockPaperScissorsLizardSpock]
      case _ => JsError("invalid game name")
    }
  }
}

trait GameEngine[T] extends ModuloArithmeticGame[T] {
  def id : String
  def createNewGame: Game[T]
}