package sk.upjs.ics.folkjukebox.logic.provider;

import android.content.SearchRecentSuggestionsProvider;

public class SongSearchRecentSuggestionsProvider extends SearchRecentSuggestionsProvider {

    //
    public final static String AUTHORITY = Provider.AUTHORITY+ ".logic.provider.SongSearchRecentSuggestionsProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SongSearchRecentSuggestionsProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
