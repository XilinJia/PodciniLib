package ac.mdiq.podcini.sources;

import ac.mdiq.podcini.shared.HTTPAttribs;
import ac.mdiq.podcini.shared.EpisodeIPC;
import ac.mdiq.podcini.shared.AudioSpec;
import ac.mdiq.podcini.shared.CaptionSpec;
import ac.mdiq.podcini.shared.VideoSpec;
import ac.mdiq.podcini.shared.MediaSpecs;
import ac.mdiq.podcini.shared.FeedIPC;

interface Provider {
    HTTPAttribs httpAtribsFor(in String url);
    int canHandleUrl(in String url);
    @nullable EpisodeIPC buildEpisode(in String url);
    @nullable String getEpisodeDescription(in String url);
    List<CaptionSpec> getCaptionSpecs(in EpisodeIPC media);
    MediaSpecs getMediaSpecs(in EpisodeIPC media, in List<String> preferredLocales, in boolean force);
    List<String> feedsTitlesAtUrl(in String url_);
    @nullable FeedIPC buildFeed(in String url, int index);
    @nullable FeedIPC feedToUpdate(in String url);
    List<EpisodeIPC> getEpisodes(in int total, in long since);
}