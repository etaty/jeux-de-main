package models

sealed trait RPSResult

case object Won extends RPSResult

case object Lost extends RPSResult

case object Tie extends RPSResult
