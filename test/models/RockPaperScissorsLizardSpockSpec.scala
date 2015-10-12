package models

import models.rockpaperscissorslizardspock._
import org.specs2.mutable.Specification

class RockPaperScissorsLizardSpockSpec extends Specification {

  "Rock Paper Scissors Lizard Spock rules" >> {
    "Rock beats Scissors and Lizard" >> {
      RockPaperScissorsLizardSpockEngine.findWinner(Rock, Scissors) must be beSome Rock
      RockPaperScissorsLizardSpockEngine.findWinner(Rock, Lizard) must be beSome Rock
      RockPaperScissorsLizardSpockEngine.findWinner(Scissors, Rock) must be beSome Rock
      RockPaperScissorsLizardSpockEngine.findWinner(Lizard, Rock) must be beSome Rock
    }
    "Scissors beats Paper and Lizard" >> {
      RockPaperScissorsLizardSpockEngine.findWinner(Scissors, Paper) must be beSome Scissors
      RockPaperScissorsLizardSpockEngine.findWinner(Scissors, Lizard) must be beSome Scissors
      RockPaperScissorsLizardSpockEngine.findWinner(Paper, Scissors) must be beSome Scissors
      RockPaperScissorsLizardSpockEngine.findWinner(Lizard, Scissors) must be beSome Scissors
    }
    "Paper beats Rock and Spock" >> {
      RockPaperScissorsLizardSpockEngine.findWinner(Paper, Rock) must be beSome Paper
      RockPaperScissorsLizardSpockEngine.findWinner(Paper, Spock) must be beSome Paper
      RockPaperScissorsLizardSpockEngine.findWinner(Rock, Paper) must be beSome Paper
      RockPaperScissorsLizardSpockEngine.findWinner(Spock, Paper) must be beSome Paper
    }
    "Lizard beats Spock and Paper" >> {
      RockPaperScissorsLizardSpockEngine.findWinner(Lizard, Spock) must be beSome Lizard
      RockPaperScissorsLizardSpockEngine.findWinner(Lizard, Paper) must be beSome Lizard
      RockPaperScissorsLizardSpockEngine.findWinner(Spock, Lizard) must be beSome Lizard
      RockPaperScissorsLizardSpockEngine.findWinner(Paper, Lizard) must be beSome Lizard
    }
    "Spock beats Scissors and Rock" >> {
      RockPaperScissorsLizardSpockEngine.findWinner(Spock, Scissors) must be beSome Spock
      RockPaperScissorsLizardSpockEngine.findWinner(Spock, Rock) must be beSome Spock
      RockPaperScissorsLizardSpockEngine.findWinner(Scissors, Spock) must be beSome Spock
      RockPaperScissorsLizardSpockEngine.findWinner(Rock, Spock) must be beSome Spock
    }

    "tie if same hands" >> {
      RockPaperScissorsLizardSpockEngine.findWinner(Rock, Rock) must beNone
      RockPaperScissorsLizardSpockEngine.findWinner(Scissors, Scissors) must beNone
      RockPaperScissorsLizardSpockEngine.findWinner(Paper, Paper) must beNone
      RockPaperScissorsLizardSpockEngine.findWinner(Lizard, Lizard) must beNone
      RockPaperScissorsLizardSpockEngine.findWinner(Spock, Spock) must beNone
    }
  }
}
