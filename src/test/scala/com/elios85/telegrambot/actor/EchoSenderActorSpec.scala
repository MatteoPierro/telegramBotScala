package com.elios85.telegrambot.actor

import akka.actor.{ActorRef, ActorRefFactory, ActorSystem, Props}
import akka.testkit.{TestActorRef, ImplicitSender, TestKit, TestProbe}
import org.mockito.Mockito
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}
import com.elios85.telegrambot.api.TelegramAPI
import akka.actor._

class EchoSenderActorSpec extends TestKit(ActorSystem("EchoSenderActorSpec"))
with WordSpecLike
with BeforeAndAfterAll
with ImplicitSender{
  
  override def afterAll() {
    system.shutdown()
  }
  
  "EchoSenderActor" should {
     "send a telegram message when a Message arrives" in new scope{
       val chatId = 1
       val text = "a message"
       echoSender ! Message(chatId, text)
       verify(telegramApi).sendMessage(chatId, text)
     }    
  }
  
  private trait scope {
    val telegramApi = Mockito.mock(classOf[TelegramAPI])
    val echoSender = TestActorRef(new EchoSenderActor(telegramApi))
  }
}