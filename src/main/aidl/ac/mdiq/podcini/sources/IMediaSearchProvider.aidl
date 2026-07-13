package ac.mdiq.podcini.sources;

import java.util.List;
import ac.mdiq.podcini.shared.EpisodeIPC;

interface IMediaSearchProvider {
    String getName();
    List<EpisodeIPC> searchQuick(String query);
    List<EpisodeIPC> search(String query, int limit);
    List<EpisodeIPC> getMoreItems();
}