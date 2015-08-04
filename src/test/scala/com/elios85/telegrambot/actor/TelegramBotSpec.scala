package com.elios85.telegrambot.actor

import akka.actor.{ActorRef, ActorRefFactory, ActorSystem, Props}
import akka.testkit.{TestActorRef, ImplicitSender, TestKit, TestProbe}
import org.mockito.Mockito
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}
import com.elios85.telegrambot.api.TelegramAPI

class TelegramBotActorSpec extends TestKit(ActorSystem("UserProfileActorSpec"))
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
  
  private trait scope {
    val telegramApi = Mockito.mock(classOf[TelegramAPI])
    val telegramBot = TestActorRef(new TelegramBotActor(telegramApi))
  }
}