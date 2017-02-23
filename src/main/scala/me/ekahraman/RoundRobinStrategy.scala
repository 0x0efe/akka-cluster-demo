package me.ekahraman

import akka.actor.ActorContext
import akka.routing.RoundRobinGroup

import me.ekahraman.ProducerActor.RouterStrategy

class RoundRobinStrategy(context: ActorContext) extends RouterStrategy {

  var routees = Set[String]()

  def buildRouter() = context.actorOf(RoundRobinGroup(routees).props)
  var router = buildRouter

  def addRoutee(ref: akka.actor.ActorRef): Unit = {
    routees += ref.path.toString
    router = buildRouter
  }

  def removeRoutee(ref: akka.actor.ActorRef): Unit = {
    routees -= ref.path.toString
    router = buildRouter
  }

  def sendMessage[M >: Message](msg: M): Unit = router ! msg
}

class RoundRobinProducerActor extends ProducerActor {
  override val strategy = new RoundRobinStrategy(context)
}
