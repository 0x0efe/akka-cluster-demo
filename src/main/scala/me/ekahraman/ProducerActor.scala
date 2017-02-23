package me.ekahraman

import akka.actor.{Actor, ActorLogging, RootActorPath, ActorRef, Terminated}
import akka.cluster.{Cluster}
import akka.cluster.ClusterEvent.{MemberUp}
import akka.util.Timeout
import scala.util.{Try, Success, Failure}
import Roles.Consumer

object ProducerActor {

  trait RouterStrategy {
      def addRoutee(ref: ActorRef): Unit;
      def removeRoutee(ref: ActorRef): Unit;
      def sendMessage[M >: Message](msg: M): Unit;
  }
}

trait ProducerActor extends Actor with ActorLogging {

  val strategy: ProducerActor.RouterStrategy
  val cluster = Cluster(context.system)
  var counter = 0

  override def preStart(): Unit = cluster.subscribe(self, classOf[MemberUp])
  override def postStop(): Unit = cluster.unsubscribe(self)

  def registerConsumer(refTry: Try[ActorRef]): Unit = refTry match {
    case Success(ref) =>
      context watch ref
      strategy.addRoutee(ref)
    case Failure(_) => log.error("Couldn't find consumer on path!")
  }

  override def receive(): Receive = {
    case MemberUp(member) if (member.roles.contains(Consumer)) =>
      log.info(s"""Received member up event for ${member.address}""")
      val consumerRootPath = RootActorPath(member.address)
      val consumerSelection = context.actorSelection(consumerRootPath / "user" / Consumer)

      import scala.concurrent.duration.DurationInt
      import context.dispatcher
      consumerSelection.resolveOne(5.seconds).onComplete(registerConsumer)
    case Terminated(actor) => strategy.removeRoutee(actor)
    case SimpleMessage =>
      strategy.sendMessage(s"#$counter")
      counter += 1
    case _ =>
  }
}
