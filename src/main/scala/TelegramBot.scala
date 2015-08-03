import com.elios85.telegrambot.api.TelegramAPI
import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global
import scala.util.{Success, Failure}
import scala.concurrent.Await
import scala.concurrent.duration._

object TelegramBot extends App{
  val updates = TelegramAPI.getUpdates(333582343)
  updates onComplete { 
    case Success(response) => println("ok "+response.ok)
    case Failure(t) => println("An error has occured: " + t.getMessage)
  }
  Await.result(updates, 5 seconds)
  TelegramAPI.sendMessage(101616586, "Fido")
}