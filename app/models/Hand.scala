package models

import java.security.SecureRandom

import play.api.data.validation.ValidationError
import play.api.libs.json.{JsResult, JsString, JsValue, Format}

sealed trait Hand

case object Rock extends Hand

case object Paper extends Hand

case object Scissors extends Hand

object Hand {
  val random = new SecureRandom()

  val hands = Vector(Rock, Paper, Scissors)

  def randomHand = hands(random.nextInt(hands.length))

  def validate(hand: String): Option[Hand] = {
    hands.find(_.toString.equalsIgnoreCase(hand))
  }

  implicit val formatHand = new Format[Hand] {
    override def writes(o: Hand): JsValue = o match {
      case Rock => JsString("rock")
      case Paper => JsString("paper")
      case Scissors => JsString("scissors")
    }

    override def reads(json: JsValue): JsResult[Hand] =
      json.validate[String].collect(ValidationError(s"expected hand: $hands"))({
        case "rock" => Rock
        case "paper" => Paper
        case "scissors" => Scissors
      })
  }
}