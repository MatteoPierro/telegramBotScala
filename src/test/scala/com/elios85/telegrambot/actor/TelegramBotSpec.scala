package com.elios85.telegrambot.actor

import akka.actor.{ActorRef, ActorRefFactory, ActorSystem, Props}
import akka.testkit.{TestActorRef, ImplicitSender, TestKit, TestProbe}
import org.mockito.Mockito
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}
import com.elios85.telegrambot.api.TelegramAPI
import com.elios85.telegrambot.api.Response
import com.google.gson._


class ReceiverActorSpec extends TestKit(ActorSystem("ReceiverActorSpec"))
with WordSpecLike
with BeforeAndAfterAll
with ImplicitSender{
  
  override def afterAll() {
    system.shutdown()
  }
  
  "ReceiverBot" should {
     "retrive telegram messages if an Update message arrives" in new scope{
       telegramBot ! Update
       verify(telegramApi).getUpdates(any(classOf[Integer]), any(classOf[Integer]))
     }
  }
  
  "ReceiverBot" should {
    "send a Message to sender actor when receive at least a telegram message" in new scope{
      val chatId = 1
      val text = "a message"
      val chat = new JsonObject()
      chat.addProperty("id", chatId)
      val message = new JsonObject()
      message.add("chat", chat)
      message.addProperty("text", text)
      val result = new JsonObject()
      result.addProperty("update_id", 333582340)
      result.add("message", message)
      val results = new JsonArray()
      results.add(result)
      val response = Response(true, results)
      when(telegramApi.getUpdates(anyInt(), anyInt())).thenReturn(response)
      telegramBot ! Update
      senderProbe.expectMsg(Message(chatId, text))
    }
  }
  
  private trait scope {
    val telegramApi = Mockito.mock(classOf[TelegramAPI])
    val senderProbe = TestProbe()
    val telegramBot = TestActorRef(new ReceiverActor(telegramApi, senderProbe.ref))
  }
}