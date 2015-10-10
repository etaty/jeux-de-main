import models._
import org.specs2.mutable.Specification

class RockPaperScissorsSpec extends Specification {

  "RockPaperScissors rules" >> {
    "Rock beats Scissors" >> {
      RPSResult.compute(Rock, Scissors) mustEqual Won
      RPSResult.compute(Scissors, Rock) mustEqual Lost
    }
    "Scissors beats Paper" >> {
      RPSResult.compute(Scissors, Paper) mustEqual Won
      RPSResult.compute(Paper, Scissors) mustEqual Lost
    }
    "Paper beats Rock" >> {
      RPSResult.compute(Paper, Rock) mustEqual Won
      RPSResult.compute(Rock, Paper) mustEqual Lost
    }

    "tie if same hand" >> {
      RPSResult.compute(Rock, Rock) mustEqual Tie
      RPSResult.compute(Scissors, Scissors) mustEqual Tie
      RPSResult.compute(Paper, Paper) mustEqual Tie
    }
  }
}