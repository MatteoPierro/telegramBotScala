import com.elios85.telegrambot.api.TelegramAPI
import com.elios85.telegrambot.actor._
import com.elios85.telegrambot.actor.Update
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import akka.actor._

object TelegramBot extends App{
  val system = ActorSystem("TelegramBot")
  val echoSender = system.actorOf(Props(new EchoSenderActor(TelegramAPI.service)), "senderActor")
  val telegramBot = system.actorOf(Props(new TelegramBotActor(TelegramAPI.service, echoSender)), "telgramBot")
   system.scheduler.schedule(0 second, 1 second) {
    telegramBot ! Update
  }
}