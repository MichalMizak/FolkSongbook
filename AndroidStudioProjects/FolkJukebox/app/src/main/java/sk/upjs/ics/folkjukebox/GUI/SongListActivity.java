package sk.upjs.ics.folkjukebox.GUI;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.facebook.stetho.Stetho;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.upjs.ics.folkjukebox.GUI.recycler.view.SimpleCursorRecyclerAdapter;
import sk.upjs.ics.folkjukebox.GUI.recycler.view.SongItemTouchHelper;
import sk.upjs.ics.folkjukebox.R;
import sk.upjs.ics.folkjukebox.logic.provider.Provider;
import sk.upjs.ics.folkjukebox.utilities.Defaults;

public class SongListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.songRecyclerView)
    RecyclerView songRecyclerView;

    private LinearLayoutManager linearLayoutManager;
    private SimpleCursorRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Stetho.initializeWithDefaults(this);
        ButterKnife.bind(this);

        linearLayoutManager = new LinearLayoutManager(this);
        songRecyclerView.setLayoutManager(linearLayoutManager);

        getLoaderManager().initLoader(0, null, this);

        songRecyclerView.setAdapter(initializeAdapter());

        new SongItemTouchHelper(songRecyclerView,
                new SongItemTouchHelper.SongCallback(adapter));
    }
    // idea from Marurban https://stackoverflow.com/questions/24471109/recyclerview-onclick/26196831#26196831
    private void setOnItemClickListener(SimpleCursorRecyclerAdapter adapter) {
        adapter.setOnItemClickListener(new SimpleCursorRecyclerAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v, Cursor cursor) {
                Log.d("SongListActivity", "onItemClick position: " + position);

                Intent intent = new Intent(v.getContext(), SongDetailActivity.class);

                // very, very, very slow, but works
                cursor.moveToPosition(position);

                final String id = Provider.Song._ID;
                intent.putExtra(id, cursor.getInt(cursor.getColumnIndex(id)));

                Log.d("SongListActivity", Integer.toString(cursor.getInt(cursor.getColumnIndex(id))));
                Log.d("SongListActivity", cursor.getString(cursor.getColumnIndex("title")));

                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position, View v, Cursor cursor) {
                Log.d("SongListActivity", "onItemLongClick pos = " + position);
            }
        });
    }


    private SimpleCursorRecyclerAdapter initializeAdapter() {
        String[] from = {Provider.Song.COLUMN_NAMES.title.name(),
                Provider.Song.COLUMN_NAMES.lyrics.name(),
                Provider.Song.COLUMN_NAMES.region.name(),
                Provider.Song.COLUMN_NAMES.source.name(),
                Provider.Song.COLUMN_NAMES.styleId.name()};

        int[] to = {android.R.id.text1};
// android.R.layout.simple_list_item_1, from, to
        adapter = new SimpleCursorRecyclerAdapter(this, Defaults.NO_CURSOR, 0);

        setOnItemClickListener(adapter);

        return adapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        CursorLoader loader = new CursorLoader(
                this, Provider.SONG_CONTENT_URI, Defaults.ALL_COLUMNS, Defaults.NO_SELECTION,
                Defaults.NO_SELECTION_ARGS, "lyrics ASC");

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(Defaults.NO_CURSOR);
    }

/*
    private int getLastVisibleItemPosition() {
        return linearLayoutManager.findLastVisibleItemPosition();
    }

    private void setRecyclerViewScrollListener() {
        songRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int totalItemCount = songRecyclerView.getLayoutManager().getItemCount();
                if (totalItemCount == getLastVisibleItemPosition() + 1) {
                    requestPhoto();
                }
            }
        });
    }*/
}
