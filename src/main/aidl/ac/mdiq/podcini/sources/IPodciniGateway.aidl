package ac.mdiq.podcini.sources;

import ac.mdiq.podcini.sources.IFeedSearchProvider;
import ac.mdiq.podcini.sources.Provider;
import ac.mdiq.podcini.shared.ProviderAttrs;

interface IPodciniGateway {
    @nullable ProviderAttrs getAttributes();
    @nullable IFeedSearchProvider getSearchProvider();
    @nullable Provider getProvider();
}