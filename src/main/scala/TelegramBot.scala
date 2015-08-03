import com.elios85.telegrambot.api.TelegramAPI
import scala.concurrent.Await
import scala.concurrent.duration._

object TelegramBot extends App{
  val updates = TelegramAPI.getUpdates("10")
  Await.result(updates, 5 seconds)
}