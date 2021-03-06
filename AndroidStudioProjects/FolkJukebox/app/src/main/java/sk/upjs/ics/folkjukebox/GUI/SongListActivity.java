package sk.upjs.ics.folkjukebox.GUI;

import android.animation.Animator;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.stetho.Stetho;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.upjs.ics.folkjukebox.GUI.recycler.view.FastScroll.FastScrollRecyclerView;
import sk.upjs.ics.folkjukebox.GUI.recycler.view.FastScroll.FastScrollRecyclerViewItemDecoration;
import sk.upjs.ics.folkjukebox.GUI.recycler.view.SimpleCursorRecyclerAdapter;
import sk.upjs.ics.folkjukebox.GUI.recycler.view.SongItemTouchHelper;
import sk.upjs.ics.folkjukebox.R;
import sk.upjs.ics.folkjukebox.logic.provider.Provider;
import sk.upjs.ics.folkjukebox.utilities.Defaults;

public class SongListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    // source https://github.com/ajaydewari/FloatingActionButtonMenu
    FloatingActionButton fab, regionFab, lyricsFab, titleFab;
    LinearLayout fabLayout1, fabLayout2, fabLayout3;
    View fabBGLayout;
    boolean isFABOpen = false;

    Cursor cursor;

    @BindView(R.id.songRecyclerView)
    FastScrollRecyclerView songRecyclerView;

    private LinearLayoutManager linearLayoutManager;
    private SimpleCursorRecyclerAdapter adapter;

    private int searchMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);
        ButterKnife.bind(this);

        getLoaderManager().initLoader(0, null, this);

        linearLayoutManager = new LinearLayoutManager(this);
        initRecyclerView();

        initFabs();

        handleOnClickListeners();

        new SongItemTouchHelper(songRecyclerView,
                new SongItemTouchHelper.SongCallback(adapter));
    }

    private void initRecyclerView() {
        songRecyclerView.setLayoutManager(linearLayoutManager);
        songRecyclerView.setAdapter(initializeAdapter());

        FastScrollRecyclerViewItemDecoration decoration = new FastScrollRecyclerViewItemDecoration(this);
        songRecyclerView.addItemDecoration(decoration);
        songRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initFabs() {
        fabLayout1 = (LinearLayout) findViewById(R.id.fabLayout1);
        fabLayout2 = (LinearLayout) findViewById(R.id.fabLayout2);
        fabLayout3 = (LinearLayout) findViewById(R.id.fabLayout3);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        regionFab = (FloatingActionButton) findViewById(R.id.regionFab);
        lyricsFab = (FloatingActionButton) findViewById(R.id.lyricsFab);
        titleFab = (FloatingActionButton) findViewById(R.id.titleFab);
        fabBGLayout = findViewById(R.id.fabBGLayout);
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


    // idea from Marurban https://stackoverflow.com/questions/24471109/recyclerview-onclick/26196831#26196831
    private void setOnItemClickListener(SimpleCursorRecyclerAdapter adapter) {
        adapter.setOnItemClickListener(new SimpleCursorRecyclerAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v, Cursor cursor) {
                Log.d("SongListActivity", "onItemClick position: " + position);

                // Intent intent = new Intent(v.getContext(), SongDetailActivity.class);
                Intent intent = new Intent(v.getContext(), SongDetailBrowserActivity.class);
                // very, very, very slow, but works
                cursor.moveToPosition(position);

                final String id = Provider.Song._ID;
                intent.putExtra(id, cursor.getInt(cursor.getColumnIndex(id)));

                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position, View v, Cursor cursor) {
                Log.d("SongListActivity", "onItemLongClick pos = " + position);
            }
        });
    }


    private SimpleCursorRecyclerAdapter initializeAdapter() {
        cursor = getContentResolver().query(Provider.SONG_CONTENT_URI, Defaults.ALL_COLUMNS, Defaults.NO_SELECTION,
                Defaults.NO_SELECTION_ARGS, "title ASC");

        adapter = new SimpleCursorRecyclerAdapter(this, cursor, 0);
        adapter.setMapIndex(buildMapIndex(cursor));

        setOnItemClickListener(adapter);

        return adapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        CursorLoader loader = new CursorLoader(
                this, Provider.SONG_CONTENT_URI, Defaults.ALL_COLUMNS, Defaults.NO_SELECTION,
                Defaults.NO_SELECTION_ARGS, "title ASC");

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // adapter.setMapIndex(buildMapIndex(data));
        cursor = data;
        //songRecyclerView.setupThings();
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(Defaults.NO_CURSOR);
    }


    // TODO: work with database rather than iterate whole cursor
    private HashMap<String, Integer> buildMapIndex(Cursor cursor) {
        HashMap<String, Integer> mapIndex = new HashMap<>();

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(Provider.Song.COLUMN_NAMES.title.name()));
            String index = name.substring(0, 1);
            index = index.toUpperCase();

            if (!mapIndex.containsKey(index)) {
                mapIndex.put(index, cursor.getPosition());
            }
        }

        return mapIndex;
    }

}
