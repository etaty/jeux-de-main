package controllers

import models._
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

class Application extends Controller with RPSHistorySessionHelper {

  def index = Action { implicit request =>
    Ok(views.html.index())
  }

  def showComputerVsComputer(rounds: Option[Int]) = Action { request =>

    val fightResult = rounds.map { r =>
      RPSHistory(
        games = (1 to r).map { _ =>
          RPSGame.create(Hand.randomHand, Hand.randomHand)
        }
      )
    }
    val roundsInForm = rounds.getOrElse(3)
    Ok(views.html.computerVsComputer(roundsInForm, fightResult))
  }

  def showPlayerVsComputer = Action { implicit request =>
    val last = request.flash.get("last")
    Ok(views.html.playerVsComputer(last, getRPSHistory))
  }

  val rpsFormConstraints = Form(
    single(
      "rps" -> text
    )
  )

  def playPlayerVsComputer = Action { implicit request =>
    val rps = rpsFormConstraints.bindFromRequest().get

    val r = for {
      playerHand <- Hand.validate(rps)
    } yield {
        val computerHand = Hand.randomHand
        val r = RPSGame.create(playerHand, computerHand)
        val newHistory = getRPSHistory.addGame(r)
        Redirect(routes.Application.showPlayerVsComputer)
          .withSession("history" -> writeRPSHistory(newHistory))
          .flashing("last" -> s"${r.p1} - ${r.p2} => ${r.result}")
      }
    r.getOrElse(Redirect(routes.Application.showPlayerVsComputer))
  }

  def resetPlayerVsComputer = Action { implicit request =>
    Redirect(routes.Application.showPlayerVsComputer).withSession("history" -> writeRPSHistory(RPSHistory()))
  }
}

trait RPSHistorySessionHelper {
  def getRPSHistory(implicit request: Request[_]) = {
    readRPSHistory(request.session.get("history"))
  }

  def readRPSHistory(s: Option[String]): RPSHistory = {
    s.filter(_.nonEmpty)
      .flatMap(Json.parse(_).validate[RPSHistory].asOpt)
      .getOrElse(RPSHistory())
  }

  def writeRPSHistory(h: RPSHistory): String = {
    Json.toJson(h).toString()
  }
}
