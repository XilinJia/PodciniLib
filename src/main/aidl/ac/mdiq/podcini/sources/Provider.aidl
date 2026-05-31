package ac.mdiq.podcini.sources;

import ac.mdiq.podcini.shared.EpisodeIPC;
import ac.mdiq.podcini.shared.AudioSpec;
import ac.mdiq.podcini.shared.VideoSpec;
import ac.mdiq.podcini.shared.FeedIPC;

interface Provider {
    String feedType();
    boolean haveMultiQualities();
    boolean canHandleFeed(in String url);
    boolean canHandleUrl(in String url);
    @nullable EpisodeIPC buildEpisode(in String url);
    boolean haveViewCount();
    boolean haveLikeCount();
    @nullable String getEpisodeDescription(in String url);
    String searcherTAG();
    boolean canHandleSharedMedia(in String urlString);
    @nullable String getShareLogType();
    List<String> feedDomains();
    List<AudioSpec> getAudioSpecs(in EpisodeIPC media);
    List<VideoSpec> getVideoOnlySpecs(in EpisodeIPC media);
    List<VideoSpec> getVideoSpecs(in EpisodeIPC media);
    List<String> feedsTitlesAtUrl(in String url_);
    @nullable FeedIPC buildFeed(in String url, String feedSource, int index);
    List<EpisodeIPC> getEpisodes(in int total);
    @nullable FeedIPC downloadFeed(in String url, long lastUpdateTime, boolean fullUpdate, int limitEpisodesCount);
}