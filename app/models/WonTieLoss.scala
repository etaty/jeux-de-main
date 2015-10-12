package models

case class WonTieLoss(won: Int = 0, tie: Int = 0, lost: Int = 0) {
  def incWon = copy(won = won + 1)

  def incTie = copy(tie = tie + 1)

  def incLost = copy(lost = lost + 1)
}

object WonTieLoss {
  def apply[T](game : Game[T]) : WonTieLoss = {
    game.rounds.foldLeft(WonTieLoss()) { (sum, round) =>
      game.gameEngine.findWinner(round.p1, round.p2) match {
        case Some(round.p1) => sum.incWon
        case Some(round.p2) => sum.incLost
        case None => sum.incTie
      }
    }
  }
}