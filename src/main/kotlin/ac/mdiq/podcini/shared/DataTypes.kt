package ac.mdiq.podcini.shared

import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpisodeIPC(
    var id: Long = 0L,
    var feedId: Long? = null,
    var title: String? = null,
    var fileUrl: String? = null,
    var link: String? = null,
    var downloadUrl: String? = null,
    var description: String? = null,
    var imageUrl: String? = null,
    var pubDate: Long = 0,
    var size: Long = 0L,
    var viewCount: Int = 0,
    var likeCount: Int = 0,
    var mimeType: String? = "",
    var duration: Int = 0
): Parcelable

@Parcelize
data class FeedIPC(
    var id: Long = 0,
    var title: String? = null,
    var downloadUrl: String? = null,
    var hasVideoMedia: Boolean = false,
    var prefStreamOverDownload: Boolean = true,
    var autoDownload: Boolean = false,
    var description: String? = null,
    var author: String? = null,
    var imageUrl: String? = null,
    var type: String? = null,
    var lastUpdateTime: Long = 0,
    var limitEpisodesCount: Int = 0,
    var episodes: MutableList<EpisodeIPC> = mutableListOf()
): Parcelable

@Parcelize
class AudioSpec(
    var averageBitrate: Int = 0,
    var bitrate: Int = 0,
    var quality: String? = null,
    var codec: String? = null,
    var format: String? = null,
    var audioTrackId: String? = null,
    var audioTrackName: String? = null,
    var audioLocale: String? = null,
    var deliveryMethod: String? = null,
    var url: String? = null
): Parcelable

@Parcelize
class VideoSpec(
    var isVideoOnly: Boolean = false,
    var bitrate: Int = 0,
    var fps: Int = 0,
    var width: Int = 0,
    var height: Int = 0,
    var quality: String? = null,
    var codec: String? = null,
    var deliveryMethod: String? = null,
    var url: String? = null,
    var resolution: String? = null
): Parcelable

@Parcelize
class FeedSearchResult(
    val title: String,
    val imageUrl: String?,
    val feedUrl: String?,
    val author: String?,
    val count: Int?,
    val update: String?,
    val subscriberCount: Int,
    val source: String): Parcelable {

    // feedId will be positive if already subscribed
    @IgnoredOnParcel
    var feedId by mutableLongStateOf(0L)
}
