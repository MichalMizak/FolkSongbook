package sk.upjs.ics.folkjukebox.GUI;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

import junit.framework.Assert;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.upjs.ics.folkjukebox.R;
import sk.upjs.ics.folkjukebox.logic.provider.Provider;
import sk.upjs.ics.folkjukebox.utilities.Defaults;

public class SongDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private int id;

    private LinearLayoutManager linearLayoutManager;

    @BindView(R.id.titleTextView)
    TextView titleTextView;
    @BindView(R.id.lyricsTextView)
    TextView lyricsTextView;

    private static final String ID_CONSTANT = "id";
    private int loaderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);

        Stetho.initializeWithDefaults(this);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            id = getIntent().getIntExtra(Provider.Song._ID, 0);
        } else {
            Log.d(SongDetailActivity.class.getName(), "savedInstanceState is not null");
            id = (int) savedInstanceState.getSerializable(ID_CONSTANT);

            Log.d("SongDetailActivity", Integer.toString(id));
        }

        //getIntent().getBundleExtra("lyrics");

        linearLayoutManager = new LinearLayoutManager(this);
        loaderId += 1;
        getLoaderManager().initLoader(loaderId, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = Uri.withAppendedPath(Provider.SONG_CONTENT_URI, "" + this.id);

        CursorLoader loader = new CursorLoader(
                this, uri, Defaults.ALL_COLUMNS, Defaults.NO_SELECTION,
                Defaults.NO_SELECTION_ARGS, Defaults.NO_SORT_ORDER);

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data != null && data.moveToNext()) {
            Log.d("noLoadFinished", "I got here");
            final int titleColumnId = data.getColumnIndex(Provider.Song.COLUMN_NAMES.title.name());

            CharSequence title = data.getString(titleColumnId);

            titleTextView.setText(title);

            final int lyricsColumnId = data.getColumnIndex(Provider.Song.COLUMN_NAMES.lyrics.name());
            CharSequence lyrics = data.getString(lyricsColumnId);

            lyricsTextView.setText(lyrics);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        onSaveInstanceState(new Bundle());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d("onSaveInstance", id + "");

        outState.putSerializable(ID_CONSTANT, id);
    }
}
