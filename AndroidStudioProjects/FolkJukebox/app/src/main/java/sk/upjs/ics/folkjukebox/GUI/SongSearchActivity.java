package sk.upjs.ics.folkjukebox.GUI;

import android.animation.Animator;
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
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.facebook.stetho.Stetho;

import butterknife.ButterKnife;
import sk.upjs.ics.folkjukebox.GUI.list.view.TitleLyricsCursorAdapter;
import sk.upjs.ics.folkjukebox.R;
import sk.upjs.ics.folkjukebox.logic.provider.Provider;
import sk.upjs.ics.folkjukebox.logic.provider.SongSearchRecentSuggestionsProvider;
import sk.upjs.ics.folkjukebox.utilities.Defaults;

// AppCompatActivity,
public class SongSearchActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String SEARCH_MODE = "search_mode";

    public static final int SEARCH_MODE_REGION = 0;
    public static final int SEARCH_MODE_TITLE = 1;
    public static final int SEARCH_MODE_LYRICS = 2;

    private int searchMode;

    FloatingActionButton fab, regionFab, lyricsFab, titleFab;
    LinearLayout fabLayout1, fabLayout2, fabLayout3;
    View fabBGLayout;
    boolean isFABOpen = false;

    private TitleLyricsCursorAdapter adapter;
    private String query;
    private int loaderId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_search);

        Stetho.initializeWithDefaults(this);
        ButterKnife.bind(this);

        fabLayout1 = (LinearLayout) findViewById(R.id.fabLayout1);
        fabLayout2 = (LinearLayout) findViewById(R.id.fabLayout2);
        fabLayout3 = (LinearLayout) findViewById(R.id.fabLayout3);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        regionFab = (FloatingActionButton) findViewById(R.id.regionFab);
        lyricsFab = (FloatingActionButton) findViewById(R.id.lyricsFab);
        titleFab = (FloatingActionButton) findViewById(R.id.titleFab);
        fabBGLayout = findViewById(R.id.fabBGLayout);

        handleOnClickListeners();

        loaderId = 0;
        getLoaderManager().initLoader(loaderId, null, this);

        adapter = initializeAdapter();

        setListAdapter(adapter);

        // Get the intent, verify the action and get the query
        handleIntent(getIntent());
    }

    private void handleOnClickListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
            }
        });

        regionFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                searchMode = SongSearchActivity.SEARCH_MODE_REGION;
                onSearchRequested();
            }
        });

        titleFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMode = SongSearchActivity.SEARCH_MODE_TITLE;
                onSearchRequested();
            }
        });

        lyricsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMode = SongSearchActivity.SEARCH_MODE_LYRICS;
                onSearchRequested();
            }
        });
    }


    private void showFABMenu() {
        isFABOpen = true;
        fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.setVisibility(View.VISIBLE);
        fabLayout3.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);

        fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
        fabLayout3.animate().translationY(-getResources().getDimension(R.dimen.standard_145));
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabBGLayout.setVisibility(View.GONE);
        fab.animate().rotationBy(-180);
        fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0);
        fabLayout3.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fabLayout1.setVisibility(View.GONE);
                    fabLayout2.setVisibility(View.GONE);
                    fabLayout3.setVisibility(View.GONE);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isFABOpen) {
            closeFABMenu();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSearchRequested() {
        if (isFABOpen)
            closeFABMenu();

        Bundle appData = new Bundle();
        appData.putInt(SongSearchActivity.SEARCH_MODE, this.searchMode);
        startSearch(null, false, appData, false);
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        Bundle appData = getIntent().getBundleExtra(SearchManager.APP_DATA);
        if (appData != null) {
            searchMode = appData.getInt(SEARCH_MODE);
        } else {
            Log.d("SongSearchActivity", "Got a problem, Sherlock");
        }

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            this.query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch();
        } else {
            Log.d("SongSearchActivity", "intent incorrect");
            query = null;
        }
    }

    private void doMySearch() {
        // TODO: improve this solution by properly resetting loader
        getLoaderManager().destroyLoader(loaderId);
        loaderId++;
        getLoaderManager().initLoader(loaderId, null, this);


        saveQuery();
    }

    private void saveQuery() {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                SongSearchRecentSuggestionsProvider.AUTHORITY, SongSearchRecentSuggestionsProvider.MODE);
        suggestions.saveRecentQuery(query, null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == loaderId) {
            if (query == null) return null;

            Log.d("onCreateLoader", query);

            String column = "";

            switch(searchMode) {
                case SEARCH_MODE_REGION:
                    column = Provider.Song.COLUMN_NAMES.region.name();
                    break;
                case SEARCH_MODE_TITLE:
                    column = Provider.Song.COLUMN_NAMES.title.name();
                    break;
                case SEARCH_MODE_LYRICS:
                    column = Provider.Song.COLUMN_NAMES.lyrics.name();
                    break;

            }

            CursorLoader loader = new CursorLoader(
                    this, Provider.SONG_CONTENT_URI, Defaults.ALL_COLUMNS, column + " LIKE '%" + query + "%'",
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

    private TitleLyricsCursorAdapter initializeAdapter() {

        String[] from = {Provider.Song.COLUMN_NAMES.title.name(),
                Provider.Song.COLUMN_NAMES.lyrics.name()};

        // int[] to = {android.R.id.text1, android.R.id.text2};
        // android.R.layout.simple_list_item_1

        // int[] to = {R.id.titleTextViewList, R.id.lyricsPreviewTextViewList};

        //adapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
          //      Defaults.NO_CURSOR, from, to, Defaults.NO_FLAGS);
        adapter = new TitleLyricsCursorAdapter(this, Defaults.NO_CURSOR, Defaults.NO_FLAGS);
        // setOnItemClickListener(adapter);

        return adapter;
    }
}
