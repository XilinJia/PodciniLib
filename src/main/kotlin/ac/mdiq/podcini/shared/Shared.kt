package ac.mdiq.podcini.shared

import ac.mdiq.podcini.sources.IFeedSearchProvider
import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.net.Proxy
import kotlin.concurrent.atomics.AtomicLong
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.time.Clock

const val PROVIDER_API_VERSION = 1

fun nowInMillis(): Long = Clock.System.now().toEpochMilliseconds()

@OptIn(ExperimentalAtomicApi::class)
private val lastId = AtomicLong(nowInMillis())
@OptIn(ExperimentalAtomicApi::class)
fun getEntityId(now: Long = nowInMillis()): Long {
    while (true) {
        val last = lastId.load()
        val next = if (now > last) now else last + 1
        if (lastId.compareAndSet(last, next)) return next
    }
}

enum class FeedType(name: String) {
    RSS("rss"),
    ATOM1("atom"),
    YOUTUBE("YouTube")
}

enum class ShareType {
    Text,
    YTMedia,
    Podcast
}

private const val AP_SUBSCRIBE = "podcini-subscribe://"     // TODO: appears not used

/**
 * Checks if URL is valid and modifies it if necessary.
 * @param url_ The url which is going to be prepared
 * @return The prepared url
 */
fun prepareUrl(url_: String): String {
    var url = url_
    url = url.trim { it <= ' ' }
    val lowerCaseUrl = url.lowercase() // protocol names are case insensitive
    //        Logd(TAG, "prepareUrl lowerCaseUrl: $lowerCaseUrl")
    return when {
        lowerCaseUrl.startsWith("feed://") ->  prepareUrl(url.substring("feed://".length))
        lowerCaseUrl.startsWith("pcast://") ->  prepareUrl(url.substring("pcast://".length))
        lowerCaseUrl.startsWith("pcast:") ->  prepareUrl(url.substring("pcast:".length))
        lowerCaseUrl.startsWith("itpc") ->  prepareUrl(url.substring("itpc://".length))
        lowerCaseUrl.startsWith(AP_SUBSCRIBE) ->  prepareUrl(url.substring(AP_SUBSCRIBE.length))
        //            lowerCaseUrl.contains(AP_SUBSCRIBE_DEEPLINK) -> {
        //                Logd(TAG, "Removing $AP_SUBSCRIBE_DEEPLINK")
        //                val removedWebsite = url.substring(url.indexOf("?url=") + "?url=".length)
        //                return try {
        //                    prepareUrl(URLDecoder.decode(removedWebsite, "UTF-8"))
        //                } catch (e: UnsupportedEncodingException) {
        //                    prepareUrl(removedWebsite)
        //                }
        //            }
        !(lowerCaseUrl.startsWith("http://") || lowerCaseUrl.startsWith("https://")) ->  "https://$url"
        else ->  url
    }
}

interface FeedSearcher {
    fun urlNeedsLookup(url: String): Boolean = false
    suspend fun search(query: String): List<FeedSearchResult>
    suspend fun lookupUrl(url: String): String = url
    val name: String?
}

abstract class BaseSearchProviderService : Service(), FeedSearcher {
    private val binder = object : IFeedSearchProvider.Stub() {
        override fun getName(): String? = this@BaseSearchProviderService.name

        override fun urlNeedsLookup(url: String): Boolean =
            this@BaseSearchProviderService.urlNeedsLookup(url)

        override fun search(query: String): List<FeedSearchResult> {
            return kotlinx.coroutines.runBlocking {
                this@BaseSearchProviderService.search(query)
            }
        }

        override fun lookupUrl(url: String): String = kotlinx.coroutines.runBlocking {
            this@BaseSearchProviderService.lookupUrl(url)
        }
    }

    override fun onBind(intent: Intent): IBinder = binder
}

class ProxyConfig( val type: Proxy.Type,
                   val host: String?,
                   val port: Int,
                   val username: String?,
                   val password: String?) {

    companion object {
        const val DEFAULT_PORT: Int = 8080
    }
}
