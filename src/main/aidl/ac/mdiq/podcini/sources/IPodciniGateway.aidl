package ac.mdiq.podcini.sources;

import ac.mdiq.podcini.sources.IFeedSearchProvider;
import ac.mdiq.podcini.sources.Provider;

interface IPodciniGateway {
    // Search is required, so no annotation needed
    @nullable IFeedSearchProvider getSearchProvider();

    // Playlist management is optional!
    @nullable Provider getProvider();
}