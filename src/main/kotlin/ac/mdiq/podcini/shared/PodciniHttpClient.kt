package ac.mdiq.podcini.shared

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.HttpTimeoutConfig
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.util.AttributeKey
import kotlinx.io.IOException
import okhttp3.Call
import okhttp3.Connection
import okhttp3.ConnectionPool
import okhttp3.Credentials.basic
import okhttp3.EventListener
import okhttp3.Protocol
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.TimeUnit
import kotlin.io.encoding.Base64

const val USER_AGENT = "Mozilla/5.0 (Linux; Android 14; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/156.0.0.0 Mobile Safari/537.36"

object PodciniHttpClient {
    private val TAG: String = PodciniHttpClient::class.simpleName ?: "Anonymous"

    var proxyConfig: ProxyConfig? = null

    private var ktorClient: HttpClient? = null

    fun getKtorClient(): HttpClient {
        if (ktorClient == null) ktorClient = createKtorClient()
        return ktorClient!!
    }

    val documentKtor = getKtorClient().config {
        install(HttpCache)
    }

    val mediaKtor = getKtorClient().config {
        expectSuccess = false
        install(HttpTimeout) {
            connectTimeoutMillis = 15_000
            socketTimeoutMillis = 30_000
            requestTimeoutMillis = HttpTimeoutConfig.INFINITE_TIMEOUT_MS
        }
    }

    fun resetClient() {
        ktorClient = null
    }

    val CredentialsKey = AttributeKey<HasCredentials>("Credentials")

    private fun createKtorClient(): HttpClient {
        val baseClient = HttpClient(OkHttp) {
            expectSuccess = false
            install(DefaultRequest) {
                attributes.getOrNull(CredentialsKey)?.let { creds ->
                    val user = creds.username.orEmpty()
                    val pass = creds.password.orEmpty()
                    if (user.isNotEmpty()) header(HttpHeaders.Authorization, "Basic ${Base64.encode("$user:$pass".encodeToByteArray())}")
                }
            }
            install(HttpCookies) { storage = AcceptAllCookiesStorage() }
            install(HttpRedirect) { checkHttpMethod = false }
            install(HttpTimeout) {
                connectTimeoutMillis = 15_000
                socketTimeoutMillis = 30_000
                requestTimeoutMillis = 45_000
            }
            engine {
                config {
                    followRedirects(false)
                    followSslRedirects(false)
                    retryOnConnectionFailure(true)
                    connectTimeout(15, TimeUnit.SECONDS)
                    connectionPool(ConnectionPool(5, 2, TimeUnit.MINUTES))
                    eventListener(object : EventListener() {
                        override fun connectionAcquired(call: Call, connection: Connection) {
                            //                            Log.d(TAG, "connectionAcquired: $connection")
                        }
                        override fun connectionReleased(call: Call, connection: Connection) {
                            //                            Log.d(TAG, "connectionReleased: $connection")
                        }
                        override fun callFailed(call: Call, ioe: IOException) {
                            Log.e(TAG, "callFailed ${ioe.message}")
                        }
                        override fun dnsEnd(call: Call, domainName: String, inetAddressList: List<InetAddress>) {
//                            Log.d(TAG, "dns $domainName -> $inetAddressList")
                        }
                        override fun connectStart(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy) {
//                            Log.d(TAG, "connect start $addr")
                        }
                        override fun connectFailed(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy, protocol: Protocol?, ioe: IOException) {
//                            Log.d(TAG, "connect failed $addr: $ioe")
                        }
                    })
                    proxyConfig?.let { proxy ->
                        if (proxy.type != Proxy.Type.DIRECT && !proxy.host.isNullOrEmpty()) {
                            proxy(Proxy(proxy.type, InetSocketAddress.createUnresolved(proxy.host, if (proxy.port > 0) proxy.port else ProxyConfig.DEFAULT_PORT)))
                            if (!proxy.username.isNullOrEmpty() && proxy.password != null)
                                proxyAuthenticator { _, response -> response.request.newBuilder().header("Proxy-Authorization", basic(proxy.username, proxy.password)).build() }
                        }
                    }
                }
            }
        }
        return baseClient
    }

    fun configProxy(proxyConfig: ProxyConfig?) {
        PodciniHttpClient.proxyConfig = proxyConfig
    }
}
