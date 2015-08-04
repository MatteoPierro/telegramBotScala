package com.elios85.telegrambot.actor

import akka.actor._
import com.elios85.telegrambot.api.TelegramAPI

class TelegramBotActor(telegramApi: TelegramAPI) extends Actor{
  
  def receive = {
    case Update => telegramApi.getUpdates(0, 0) 
    case _ =>
  }
}

case class Update()