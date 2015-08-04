import com.elios85.telegrambot.api.TelegramAPI
import com.elios85.telegrambot.actor.TelegramBotActor
import com.elios85.telegrambot.actor.Update
import akka.actor._

object TelegramBot extends App{
  val system = ActorSystem("TelegramBot")
  val telegramBot = system.actorOf(Props(new TelegramBotActor(TelegramAPI.service)), "telgramBot")
  telegramBot ! Update
}