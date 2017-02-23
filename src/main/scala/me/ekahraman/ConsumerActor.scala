package me.ekahraman

import akka.actor.{Actor, ActorLogging}

class ConsumerActor extends Actor with ActorLogging {
  override def receive(): Receive = {
    case msg => log.info(s"""${sender.path.address} : $msg""")
  }
}
