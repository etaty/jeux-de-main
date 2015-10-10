package models

import play.api.libs.json._

case class WonTieLoss(won: Int = 0, tie: Int = 0, lost: Int = 0) {
  def incWon = copy(won = won + 1)

  def incTie = copy(tie = tie + 1)

  def incLost = copy(lost = lost + 1)
}

case class RPSHistory(games: Seq[RPSGame] = Seq.empty) {
  def rounds = games.length

  lazy val (won, tie, lost) = {
    val wtl = games.foldLeft(WonTieLoss()) { (sum, game) =>
      game.result match {
        case Won => sum.incWon
        case Tie => sum.incTie
        case Lost => sum.incLost
      }
    }
    (wtl.won, wtl.tie, wtl.lost)
  }

  def addGame(g: RPSGame) = copy(games = games :+ g)
}

object RPSHistory {
  implicit val formatRPSHistory = Json.format[RPSHistory]
}