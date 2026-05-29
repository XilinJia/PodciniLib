package ac.mdiq.podcini.sources;

import java.util.List;
import ac.mdiq.podcini.shared.FeedSearchResult;

interface IFeedSearchProvider {
    // 1. Property converted to an explicit getter method
    String getName();

    // 2. Default logic removed; must be implemented by the service
    boolean urlNeedsLookup(String url);

    // 3. 'suspend' removed; runs as a synchronous block over the wire
    List<FeedSearchResult> search(String query);

    String lookupUrl(String url);
}