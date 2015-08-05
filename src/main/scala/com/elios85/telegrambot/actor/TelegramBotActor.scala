package com.elios85.telegrambot.actor

import akka.actor._
import com.elios85.telegrambot.api._
import com.google.gson.JsonObject
import scala.collection.JavaConversions._

class TelegramBotActor(telegramApi: TelegramAPI) extends Actor with ActorLogging{    
  def receive = receiveBotMessage(0)
  
  def receiveBotMessage(lastUpdateId: Int):Receive ={
    case Update => handleTelegramResponse(telegramApi.getUpdates(lastUpdateId, 10)) 
    case Ack(updateId) => context become receiveBotMessage(updateId + 1)
    case Message(chatId, text) => telegramApi.sendMessage(chatId, text)
    case _ =>
  }
  
  private def handleTelegramResponse(response: Response) = response match {
    case Response(true, messages) => 
      for(message <- messages iterator){
        val updateId = message.getAsJsonObject.get("update_id").getAsInt
        val messageObject = message.getAsJsonObject.get("message").getAsJsonObject
        val text = messageObject get("text") getAsString
        val chatId = messageObject.get("chat").getAsJsonObject().get("id").getAsInt
        self ! Ack(updateId)
        self ! Message(chatId, text)
      }
      
    case _ => log.debug("ko reponse from Telegram") 
  }
}

case class Ack(updateId:Int)
case class Update()
case class Message(chatId:Integer, text: String)