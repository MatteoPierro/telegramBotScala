package com.elios85.telegrambot.actor

import com.elios85.telegrambot.api._
import akka.actor._

class EchoSenderActor(telegramApi: TelegramAPI) extends Actor with ActorLogging{
  def receive = {
    case Message(chatId, text) => telegramApi.sendMessage(chatId, text)
    case _ =>  
  }
}