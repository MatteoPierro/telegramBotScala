package com.elios85.telegrambot.actor

import akka.actor.{ActorRef, ActorRefFactory, ActorSystem, Props}
import akka.testkit.{TestActorRef, ImplicitSender, TestKit, TestProbe}
import org.mockito.Mockito
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}
import com.elios85.telegrambot.api.TelegramAPI

class TelegramBotActorSpec extends TestKit(ActorSystem("TelegramBotActorSpec"))
with WordSpecLike
with BeforeAndAfterAll
with ImplicitSender{
  
  override def afterAll() {
    system.shutdown()
  }
  
  "TelegramBot" should {
     "retrive telgram messages if an Update message arrives" in new scope{
       telegramBot ! Update
       verify(telegramApi).getUpdates(any(classOf[Integer]), any(classOf[Integer]))
     }
  }
  
  "TelegramBot" should {
    "send a telegram message when a Message arrives" in new scope{
      val chatId = 1
      val text = "test"
      val message = Message(chatId, text)
      telegramBot ! message
      verify(telegramApi).sendMessage(chatId, text)
    }
  }
  
  private trait scope {
    val telegramApi = Mockito.mock(classOf[TelegramAPI])
    val telegramBot = TestActorRef(new TelegramBotActor(telegramApi))
  }
}