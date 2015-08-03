package com.elios85.telegrambot.api

import retrofit.http.GET
import retrofit.http.POST
import retrofit.http.Query
import retrofit.RestAdapter
import com.typesafe.config._
import scala.async.Async._
import scala.concurrent.{ ExecutionContext, Future, Promise }
import ExecutionContext.Implicits.global
import com.google.gson.annotations.SerializedName

trait TelegramAPI {
  @GET("/getUpdates")
  def getUpdates(@Query("offset") offset:String): Response
  @POST("/getMessage")
  def getMessage(message: Message)
}

class Response(){
  var ok: Boolean = false
  var result: Any = null
}

class Message() {
  
} 

object TelegramAPI {
  private val conf = ConfigFactory.load()
  private val token = conf.getString("telegramApi.token")
  private val BASE_URL = "https://api.telegram.org/bot"
  private val restAdapter = new RestAdapter.Builder()
                            .setEndpoint(BASE_URL+token)
                            .setLogLevel(RestAdapter.LogLevel.FULL)
                            .build()
  val service = restAdapter.create(classOf[TelegramAPI])
  
  def getUpdates(offset:String): Future[Response] = async {
    service.getUpdates(offset)
  }
}