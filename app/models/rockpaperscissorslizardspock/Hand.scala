package models.rockpaperscissorslizardspock

import play.api.data.validation.ValidationError
import play.api.libs.json.{Format, JsResult, JsString, JsValue}

sealed trait Hand

case object Rock extends Hand

case object Paper extends Hand

case object Scissors extends Hand

case object Lizard extends Hand

case object Spock extends Hand

object Hand {
  val hands = Vector(Scissors, Paper, Rock, Lizard, Spock)

  implicit val formatHand = new Format[Hand] {
    override def writes(o: Hand): JsValue = o match {
      case Rock => JsString("rock")
      case Paper => JsString("paper")
      case Scissors => JsString("scissors")
      case Lizard => JsString("lizard")
      case Spock => JsString("spock")
    }

    override def reads(json: JsValue): JsResult[Hand] =
      json.validate[String].collect(ValidationError(s"expected hand: $hands"))({
        case "rock" => Rock
        case "paper" => Paper
        case "scissors" => Scissors
        case "lizard" => Lizard
        case "spock" => Spock
      })
  }
}


