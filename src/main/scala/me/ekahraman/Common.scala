package me.ekahraman

object Roles {
  val Seed = "seed"
  val Consumer = "consumer"
  val Producer = "producer"
}

sealed trait Message
case class SimpleMessage() extends Message
