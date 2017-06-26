package sk.upjs.ics.folkjukebox.GUI;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.upjs.ics.folkjukebox.R;
import sk.upjs.ics.folkjukebox.logic.provider.Provider;
import sk.upjs.ics.folkjukebox.utilities.Defaults;

public class SongDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Integer id;

    @BindView(R.id.titleTextViewValue)
    TextView titleTextViewValue;

    @BindView(R.id.styleTextViewValue)
    TextView styleTextViewValue;

    @BindView(R.id.regionTextViewValue)
    TextView regionTextViewValue;

    private int loaderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);

        Stetho.initializeWithDefaults(this);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            id = getIntent().getIntExtra(Provider.Song._ID, 0);

            Log.d("SongDetailActivity", Integer.toString(id));
        } else {
            Log.d(SongDetailActivity.class.getName(), "savedInstanceState is not null");
            id = (int) savedInstanceState.getSerializable(Provider.Song._ID);
        }

        setBorders();

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
            String title = data.getString(titleColumnId);
            titleTextViewValue.setText(title);

            final int styleColumnId = data.getColumnIndex(Provider.Style.COLUMN_NAMES.name.name());
            String style = data.getString(styleColumnId);
            styleTextViewValue.setText(style);

            final int regionColumnId = data.getColumnIndex(Provider.Song.COLUMN_NAMES.region.name());
            String region = data.getString(regionColumnId);
            regionTextViewValue.setText(region);

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

        outState.putSerializable(Provider.Song._ID, id);
    }

    private void setBorders() {
        ShapeDrawable rectShapeDrawable = new ShapeDrawable(); // pre defined class

        // get paint
        Paint paint = rectShapeDrawable.getPaint();

        // set border color, stroke and stroke width
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5); // you can change the value of 5

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.titleLayout);
        layout.setBackgroundDrawable(rectShapeDrawable);

        layout = (RelativeLayout) findViewById(R.id.regionLayout);
        layout.setBackgroundDrawable(rectShapeDrawable);

    }

}
