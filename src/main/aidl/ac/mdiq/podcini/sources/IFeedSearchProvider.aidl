package ac.mdiq.podcini.sources;

import java.util.List;
import ac.mdiq.podcini.shared.FeedSearchResult;

interface IFeedSearchProvider {
    String getName();
    boolean urlNeedsLookup(String url);
    List<FeedSearchResult> search(String query);
    String lookupUrl(String url);
}