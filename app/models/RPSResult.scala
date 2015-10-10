package models

sealed trait RPSResult

case object Won extends RPSResult

case object Lost extends RPSResult

case object Tie extends RPSResult

object RPSResult {
  def compute(player1: Hand, player2: Hand): RPSResult = {
    (player1, player2) match {
      case (h1, h2) if h1 == h2 => Tie
      case (Rock, Scissors) | (Scissors, Paper) | (Paper, Rock) => Won
      case _ => Lost
    }
  }
}
