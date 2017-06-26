package sk.upjs.ics.folkjukebox.GUI;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.facebook.stetho.Stetho;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.upjs.ics.folkjukebox.GUI.recycler.view.SimpleCursorRecyclerAdapter;
import sk.upjs.ics.folkjukebox.R;
import sk.upjs.ics.folkjukebox.logic.provider.Provider;
import sk.upjs.ics.folkjukebox.logic.provider.SongSearchRecentSuggestionsProvider;
import sk.upjs.ics.folkjukebox.utilities.Defaults;

// AppCompatActivity,
public class SongSearchActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter adapter;
    private String query;
    private int loaderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_search);

        Stetho.initializeWithDefaults(this);
        ButterKnife.bind(this);

        // linearLayoutManager = new LinearLayoutManager(this);
        // songRecyclerView.setLayoutManager(linearLayoutManager);

        loaderId = 0;
        getLoaderManager().initLoader(loaderId, null, this);

        adapter = initializeAdapter();

        setListAdapter(adapter);

        // Get the intent, verify the action and get the query
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            this.query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch();
        } else {
            Log.d("SongSearchActivity", "intent incorrect");
            query = null;
        }
    }

    private void doMySearch() {
        Log.d("doMySearch", getLoaderManager().toString());

        // TODO: improve this solution by properly resetting loader
        getLoaderManager().destroyLoader(loaderId);
        loaderId++;
        getLoaderManager().initLoader(loaderId, null, this);

        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                SongSearchRecentSuggestionsProvider.AUTHORITY, SongSearchRecentSuggestionsProvider.MODE);
        suggestions.saveRecentQuery(query, null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == loaderId) {
            if (query == null) return null;

            Log.d("onCreateLoader", query);
            // TODO: LIKE or fulltext search
            CursorLoader loader = new CursorLoader(
                    this, Provider.SONG_CONTENT_URI, Defaults.ALL_COLUMNS, "title LIKE '" + query + "%'",
                    Defaults.NO_SELECTION_ARGS, "title ASC");

            return loader;
        } else {
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(Defaults.NO_CURSOR);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(v.getContext(), SongDetailBrowserActivity.class);

        // very, very, very slow, (I think) but works
        final Cursor cursor = adapter.getCursor();
        cursor.moveToPosition(position);

        final String songId = Provider.Song._ID;

        intent.putExtra(songId, cursor.getInt(cursor.getColumnIndex(songId)));

        startActivity(intent);
    }


    public void onSearchFabClick(View view) {
        onSearchRequested();
    }

    private SimpleCursorAdapter initializeAdapter() {

        String[] from = {Provider.Song.COLUMN_NAMES.title.name(),
                Provider.Song.COLUMN_NAMES.lyrics.name(),
                Provider.Song.COLUMN_NAMES.region.name(),
                Provider.Song.COLUMN_NAMES.source.name(),
                Provider.Song.COLUMN_NAMES.styleId.name()};

        int[] to = {android.R.id.text1};

        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                Defaults.NO_CURSOR, from, to, Defaults.NO_FLAGS);

        // setOnItemClickListener(adapter);

        return adapter;
    }
}
