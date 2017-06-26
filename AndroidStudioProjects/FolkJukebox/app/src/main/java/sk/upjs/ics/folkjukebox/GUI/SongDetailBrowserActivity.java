package sk.upjs.ics.folkjukebox.GUI;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.facebook.stetho.Stetho;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.upjs.ics.folkjukebox.GUI.pager.CursorFragmentStatePagerAdapter;
import sk.upjs.ics.folkjukebox.R;
import sk.upjs.ics.folkjukebox.logic.provider.Provider;
import sk.upjs.ics.folkjukebox.utilities.Defaults;

public class SongDetailBrowserActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Integer id;
    private LinearLayoutManager linearLayoutManager;

    CursorFragmentStatePagerAdapter cursorFragmentStatePagerAdapter;

    @BindView(R.id.songDetailViewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail_browser);

        Stetho.initializeWithDefaults(this);
        ButterKnife.bind(this);

        // Cursor c = (Cursor) getIntent().getSerializableExtra("cursor");
        // final String[] regionNames = getResources().getStringArray(R.array.regionNames);
        // this.viewPager = (ViewPager) findViewById(R.id.songDetailViewPager);


        id = getIntent().getIntExtra(Provider.Song._ID, 0);
        Log.d("SongDetailBrowser onCr", id + " = id");

        // CAN BE A PROBLEM
        FragmentManager fm = getSupportFragmentManager();

        cursorFragmentStatePagerAdapter = new CursorFragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position, Cursor cursor) {
                String lyrics = "";
                if (cursor != null) {
                    cursor.moveToPosition(position);
                    final int lyricsColumnId = cursor.getColumnIndex(Provider.Song.COLUMN_NAMES.lyrics.name());
                    lyrics = cursor.getString(lyricsColumnId);
                }
                return SongDetailFragment.newInstance(lyrics);
            }

            @Override
            public CharSequence getPageTitle(int position, Cursor cursor) {
                CharSequence title = null;
                if (cursor != null) {
                    cursor.moveToPosition(position);
                    final int titleColumnId = cursor.getColumnIndex(Provider.Song.COLUMN_NAMES.title.name());
                    title = cursor.getString(titleColumnId);
                }
                return title;
            }
        };

        this.viewPager.setAdapter(cursorFragmentStatePagerAdapter);
        getLoaderManager().initLoader(0, null, this);
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

        if (id != null) {
            // move cursor to id position
            data.moveToFirst();
            while (data.moveToNext()) {
                final int idColumnId = data.getColumnIndex(Provider.Song._ID);
                Integer id = data.getInt(idColumnId);

                if (this.id.equals(id)) {
                    this.id = null;

                    int position = data.getPosition();
                    cursorFragmentStatePagerAdapter.swapCursor(data);

                    // woodcutter style
                    cursorFragmentStatePagerAdapter.getCursor().moveToPosition(position);
                    viewPager.setCurrentItem(data.getPosition(), true);
                    break;
                }
            }
        }

        // not swapCursor because we want it closed
        cursorFragmentStatePagerAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // not swapCursor because we want it closed
        // TODO: review this comment out
//        cursorFragmentStatePagerAdapter.changeCursor(Defaults.NO_CURSOR);
    }

    // not sure about the efectiveness of this
    /*@Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SongListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // call this to finish the current activity
    }*/

    public void onFabClick(View view) {
        startActivity(new Intent(view.getContext(), SongListActivity.class));
    }
}
