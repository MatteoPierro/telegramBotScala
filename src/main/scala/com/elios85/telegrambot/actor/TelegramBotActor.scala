package com.elios85.telegrambot.actor

import akka.actor._
import com.elios85.telegrambot.api._
import com.google.gson.JsonObject
import scala.collection.JavaConversions._

class TelegramBotActor(telegramApi: TelegramAPI) extends Actor with ActorLogging{
  
  def receive = {
    case Update => handleTelegramResponse(telegramApi.getUpdates(0, 10)) 
    case Message(chatId, text) => telegramApi.sendMessage(chatId, text)
    case _ =>
  }
  
  private def handleTelegramResponse(response: Response) = response match {
    case Response(true, messages) => 
      for(message <- messages iterator){
        val messageObject = message.getAsJsonObject.get("message").getAsJsonObject
        val text = messageObject get("text") getAsString
        val chatId = messageObject.get("chat").getAsJsonObject().get("id").getAsInt
        self ! Message(chatId, text)
      }
      
    case _ => log.debug("ko reponse from Telegram") 
  }
}

case class Update()
case class Message(chatId:Integer, text: String)