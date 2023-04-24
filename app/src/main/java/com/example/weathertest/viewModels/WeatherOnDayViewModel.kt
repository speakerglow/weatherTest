package com.example.weathertest.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertest.Logi
import com.example.weathertest.models.MessagesDto
import com.example.weathertest.network.NetProvider
import com.example.weathertest.storage.StorageProvider
import com.microsoft.signalr.Action1
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.microsoft.signalr.HubConnectionState
import com.microsoft.signalr.TransportEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class WeatherOnDayViewModel @Inject constructor(
    private val netProvider: NetProvider,
    private val storageProvider: StorageProvider
) : ViewModel() {

//    private val sharedFlow = MutableSharedFlow<ApiWeatherResult>(
//        replay = 1
//    )

    var hubConnection: HubConnection? = null
    val loginFlow = MutableStateFlow(0)
    val connectFlow = MutableStateFlow(HubConnectionState.DISCONNECTED)
    val msgFlow = MutableStateFlow("")
    private var currentJob: Job? = null

//    fun load() {
//        currentJob = viewModelScope.launch {
//            Log.e("Tag", "weatherOnDay viewModel send loading")
//            sharedFlow.emit(ApiWeatherResult.Loading())
//            try {
//                if (storageProvider.getCurrentCity() == null)
//                    sharedFlow.emit(
//                        ApiWeatherResult.Error(
//                            "No current city"
//                        )
//                    )
//                else {
//                    delay(1000)
//                    sharedFlow.emit(
//                        ApiWeatherResult.Success(
//                            Weather.fromJson(
//                                netProvider.getForecast(
//                                    storageProvider.getCurrentCity()!!.name
//                                )
//                            )
//                        )
//                    )
//                    Log.e("Tag", "weather on day viewmodel result sended")
//                }
//            } catch (ex: Exception) {
//                sharedFlow.emit(ApiWeatherResult.Error(ex.message ?: "oshibka"))
//            }
//        }
//    }

    fun login() {
        viewModelScope.launch {
            loginFlow.emit(netProvider.login())
            connectToHub()
        }
    }

    fun connectToHub() {
        hubConnection = HubConnectionBuilder.create("ws://demo.girngm.ru/mobile_inspector_vlg/chat")
            .withTransport(TransportEnum.WEBSOCKETS)
            .withTransport(TransportEnum.LONG_POLLING)
            .withAccessTokenProvider(io.reactivex.rxjava3.core.Single.defer {
                val d: DisposableSingleObserver<String?> = Single.just("finalToken")
                    .delay(10, TimeUnit.SECONDS, Schedulers.io())
                    .subscribeWith(object : DisposableSingleObserver<String?>() {
                        override fun onStart() {

                            Logi.makeLog("connection start")
//                            Log.e("SignalR", "-----------inspectorLoadListener--------------------[ChatService] SignalR token started  service " + this@WebSocketR  +  "   " + this)

                        }


                        override fun onError(error: Throwable) {

                            Logi.makeLog("connection error ${error.message}")
//                            Log.e("SignalR", "----------------------Error: ${error.message}")


                        }

                        override fun onSuccess(t: String) {
                            Logi.makeLog("connection success  $t")
                        }
                    })
                io.reactivex.rxjava3.core.Single.just("")
            }).build()

//            .withAccessTokenProvider(Single.defer {
//                val d: Disposable = Single.just("finalToken")
//                    .delay(10, TimeUnit.SECONDS, Schedulers.io())
//                    .subscribeWith(object : DisposableSingleObserver<String?>() {
//                        override fun onStart() {
//
////                            Log.e("SignalR", "-----------inspectorLoadListener--------------------[ChatService] SignalR token started  service " + this@WebSocketR  +  "   " + this)
//
//                        }
//
//
//
//                        override fun onError(error: Throwable) {
//
////                            Log.e("SignalR", "----------------------Error: ${error.message}")
//
//
//                        }
//
//                        override fun onSuccess(t: String) {
////                            Log.e("SignalR", "-------------------------Success  t=$t")
////
////                            hubConnection?.let {
////                                checkSuccess = true
////                                WebSocketR.ConnectProxyTask(it).execute("")
////                            }
////                            if (GlobalVars.isCheckNetWork) {
////                                App.data.chat_online = true
////                                MainActivity.chat_user.setStateConnected(true)
////                                WebSocketR.unixTimeConnected = System.currentTimeMillis() / 1000L + 60;
////                                //ожидание после подключения 60 секунд, после отправка доступна
////                            }
//                        }
//
//
//                    })
//                Single.just("finalToken")
//            }).build()

        hubConnection?.serverTimeout = 1000 * 60 * 5.toLong()

        Logi.makeLog("hub state = ${hubConnection?.connectionState}")
        hubConnection?.connectionState?.let {
            connectFlow.tryEmit(it)
        }

        hubConnection?.on("Send", Action1 { msg: MessagesDto ->
            Logi.makeLog("[Send] Message From Server: ${msg.content}")
            msgFlow.tryEmit(msg.content ?: "oshibka")
//            Toast.makeText(App.activity, "${msg.Content}", Toast.LENGTH_SHORT).show()
//            MainActivity.chatKisView.updateMessage(msg.content)
        }, MessagesDto::class.java)

        hubConnection?.onClosed {
            Logi.makeLog("hub closed")
            connectFlow.tryEmit(HubConnectionState.DISCONNECTED)
        }
        hubConnection?.start()
    }

    fun sendMessage() {
        Logi.makeLog("${hubConnection?.connectionId}")
        Logi.makeLog("send message")
//        val n = JSONObject()
//        n.put("FromUserId", "acd1896b-3f66-429c-bfb5-39a61c793b61")
//        n.put("ToUserId", "d826b010-0614-4256-bd47-b5070f1cb2aa")
//        n.put("Date", "2023-04-24T09:12:39.524Z")
//        n.put( "Content", "wdwd")
//        Logi.makeLog("msg = $n")
        val k = SendMessage(
            "acd1896b-3f66-429c-bfb5-39a61c793b61",
            "d826b010-0614-4256-bd47-b5070f1cb2aa",
            "2023-04-24T09:12:39.524Z",
            "wdwd"
        )
        Logi.makeLog("msg = $k")
        hubConnection?.invoke("Send", k)
            ?.subscribeWith(object : DisposableCompletableObserver(),
                CompletableObserver {
                override fun onStart() {
                    Logi.makeLog("start send msg")
                }

                override fun onError(error: Throwable) {
                    Logi.makeLog("mag send error ${error.message}")
                }

                override fun onSubscribe(d: Disposable) {
                    Logi.makeLog("onSubscribe")
                }

                override fun onComplete() {
                    Logi.makeLog("msg sended ")
                }
            })?.dispose()
    }

//    fun searchWeather(context: Context) {
//        viewModelScope.launch {
//            val intent = Intent().apply {
//                action = Intent.ACTION_WEB_SEARCH
//                putExtra(
//                    SearchManager.QUERY,
//                    "${storageProvider.getCurrentCity()?.name.orEmpty()} weather"
//                )
//            }
//            context.startActivity(Intent.createChooser(intent, null))
//        }
//    }
//
//    fun getFlow(): SharedFlow<ApiWeatherResult> = sharedFlow

    data class SendMessage(
        var FromUserId: String,
        var ToUserId: String,
        var Date: String,
        var Content: String
    )

    fun clearJob() {
        currentJob?.cancel()
    }


    companion object {
        const val MSG = """
            {
            "FromUserId": "acd1896b-3f66-429c-bfb5-39a61c793b61",
            "ToUserId": "d826b010-0614-4256-bd47-b5070f1cb2aa",
            "Date": "2023-04-24T09:12:39.524Z",
            "Content": "wdwd"
        }
        """
    }
}