package ac.mdiq.podcini.sources;

import ac.mdiq.podcini.shared.EpisodeIPC;
import ac.mdiq.podcini.shared.AudioSpec;
import ac.mdiq.podcini.shared.VideoSpec;
import ac.mdiq.podcini.shared.FeedIPC;

interface Provider {
    boolean canHandleFeed(in String url);
    boolean canHandleUrl(in String url);
    @nullable EpisodeIPC buildEpisode(in String url);
    @nullable String getEpisodeDescription(in String url);
    boolean canHandleSharedMedia(in String urlString);
    List<AudioSpec> getAudioSpecs(in EpisodeIPC media);
    List<VideoSpec> getVideoOnlySpecs(in EpisodeIPC media);
    List<VideoSpec> getVideoSpecs(in EpisodeIPC media);
    List<String> feedsTitlesAtUrl(in String url_);
    @nullable FeedIPC buildFeed(in String url, int index);
    @nullable FeedIPC feedToUpdate(in String url);
    List<EpisodeIPC> getEpisodes(in int total, in long since);
}