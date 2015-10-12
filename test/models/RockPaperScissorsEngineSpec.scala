package models

import models.rockpaperscissors._
import org.specs2.mutable.Specification

class RockPaperScissorsEngineSpec extends Specification {

  "Rock Paper Scissors rules" >> {
    "Rock beats Scissors" >> {
      RockPaperScissorsEngine.findWinner(Rock, Scissors) must be beSome Rock
      RockPaperScissorsEngine.findWinner(Scissors, Rock) must be beSome Rock
    }
    "Scissors beats Paper" >> {
      RockPaperScissorsEngine.findWinner(Scissors, Paper) must be beSome Scissors
      RockPaperScissorsEngine.findWinner(Paper, Scissors) must be beSome Scissors
    }
    "Paper beats Rock" >> {
      RockPaperScissorsEngine.findWinner(Paper, Rock) must be beSome Paper
      RockPaperScissorsEngine.findWinner(Rock, Paper) must be beSome Paper
    }

    "tie if same hands" >> {
      RockPaperScissorsEngine.findWinner(Rock, Rock) must beNone
      RockPaperScissorsEngine.findWinner(Scissors, Scissors) must beNone
      RockPaperScissorsEngine.findWinner(Paper, Paper) must beNone
    }
  }
}