package controllers

import models._
import models.rockpaperscissors.RockPaperScissorsEngine
import models.rockpaperscissorslizardspock.RockPaperScissorsLizardSpockEngine
import play.api.data.Forms._
import play.api.data._
import play.api.libs.json._
import play.api.mvc._

class Application extends Controller with GameSessionHelper {

  val games: Map[String, GameEngine[_]] =
    Vector(RockPaperScissorsEngine, RockPaperScissorsLizardSpockEngine)
      .map(g => g.id -> g).toMap

  def index = Action { implicit request =>
    Ok(views.html.index(games.keys.toVector))
  }

  def showComputerVsComputer(gameEngineId: String, rounds: Option[Int]) = Action { request =>
    games.get(gameEngineId).map { gameEngine =>
      val fightResult = rounds.map { r =>
        (1 to r).foldLeft(gameEngine.createNewGame) { (game, _) =>
          game.addComputerRound()
        }
      }
      val roundsInForm = rounds.getOrElse(3)
      Ok(views.html.computerVsComputer(roundsInForm, fightResult))
    }.getOrElse(Redirect(routes.Application.index))
  }

  def showPlayerVsComputer(gameEngineId: String) = Action { implicit request =>
    val last = request.flash.get("last")
    games.get(gameEngineId).map { gameEngine =>
      Ok(views.html.playerVsComputer(last, getGame(gameEngine)))
    }.getOrElse(Redirect(routes.Application.index))
  }

  val rpsFormConstraints = Form(
    single(
      "rps" -> text
    )
  )

  def playPlayerVsComputer(gameEngineId: String) = Action { implicit request =>
    games.get(gameEngineId).map { gameEngine =>
      val rps = rpsFormConstraints.bindFromRequest().get

      val game = getGame(gameEngine)
      val r: Option[Result] = for {
        updatedGame <- game.playNewRound(rps)
      } yield {
          Redirect(routes.Application.showPlayerVsComputer(gameEngine.id))
            .withSession("game" -> writeGame(updatedGame))
            .flashing("last" -> Json.toJson("").toString()) // todo fix
        }
      r.getOrElse(Redirect(routes.Application.showPlayerVsComputer(gameEngine.id)))
    }.getOrElse(Redirect(routes.Application.index))
  }

  def resetPlayerVsComputer(game: String) = Action { implicit request =>
    games.get(game).map { gameEngine =>
      Redirect(routes.Application.showPlayerVsComputer(gameEngine.id))
        .withSession("game" -> writeGame(gameEngine.createNewGame))
    }.getOrElse(Redirect(routes.Application.index))
  }
}

trait GameSessionHelper {
  def getGame(gameEngine: GameEngine[_])(implicit request: Request[_]): Game[_] = {
    val g = readGame(request.session.get("game"))
    if (g.gameEngine != gameEngine)
      gameEngine.createNewGame
    else
      g
  }

  def readGame(s: Option[String]): Game[_] = {
    s.filter(_.nonEmpty)
      .flatMap(s => Game.readGame(Json.parse(s)).asOpt)
      .getOrElse(rockpaperscissors.RockPaperScissors()) // todo fix
  }

  def writeGame(h: Game[_]): String = {
    Game.writeGame.applyOrElse(h, { _: Game[_] => JsString("") }).toString()
  }
}
