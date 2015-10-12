package models

import java.security.SecureRandom

trait ModuloArithmeticGameRule[T] {
  def choices: Seq[T]

  val handsIndex = choices.zipWithIndex.toMap

  def findWinner(player1: T, player2: T): Option[T] = {
    if (player1 != player2) {
      for {
        indexP1 <- handsIndex.get(player1)
        indexP2 <- handsIndex.get(player2)
      } yield {
        // @see https://en.wikipedia.org/wiki/Rock-paper-scissors#Additional_weapons

        val diff = indexP1 - indexP2
        val r2 = if (diff < 0) diff + choices.length else diff

        if ((r2 % choices.length) % 2 == 0)
          player1
        else
          player2
      }
    } else None
  }
}

trait ModuloArithmeticGameHelper[T] {
  private val random = new SecureRandom()

  def choices: Seq[T]

  def randomChoice: T = choices(random.nextInt(choices.length))


  // TODO FIX toString
  def validate(choice: String): Option[T] = {
    choices.find(_.toString.equalsIgnoreCase(choice))
  }
}

trait ModuloArithmeticGame[T]
  extends ModuloArithmeticGameHelper[T]
  with ModuloArithmeticGameRule[T]
