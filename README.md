# PodciniLib

Contains API's and common routines for developing extensions for Podcini.A

## How to use:

Add it in your root settings.gradle at the end of repositories:

	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
	
In build.gradle:
	
	dependencies {
		        implementation 'com.github.XilinJia:PodciniLib:Tag'
	}
	
### To develop a source server app:

implement API:

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
		boolean isFeedAutoDownloadable(in String urlString);
		boolean canHandleSharedMedia(in String urlString);
		@nullable String getShareLogType();
		List<String> feedDomains();
		List<AudioSpec> getAudioSpecs(in EpisodeIPC media);
		List<VideoSpec> getVideoOnlySpecs(in EpisodeIPC media);
		List<VideoSpec> getVideoSpecs(in EpisodeIPC media);
		List<String> feedsAtUrl(in String url_);
		@nullable FeedIPC buildFeed(in String url, String feedSource, int index);
		List<EpisodeIPC> getEpisodes(in int total);
		@nullable FeedIPC downloadFeed(in String url, long lastUpdateTime, boolean fullUpdate, int limitEpisodesCount);
	}
	
	class XXXProvider: Provider.Stub() { ... }
	
And optionally:

	interface IFeedSearchProvider {
		String getName();
		boolean urlNeedsLookup(String url);
		List<FeedSearchResult> search(String query);
		String lookupUrl(String url);
	}
	
	class VistaGuideSearcher : IFeedSearchProvider.Stub() { ... }
	
When ready to connect with Podcini.A, register at https://github.com/XilinJia/Podcini.A with a feedType string and a brief description of the app.
