package sk.upjs.ics.folkjukebox.GUI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import sk.upjs.ics.folkjukebox.R;
import sk.upjs.ics.folkjukebox.logic.provider.Provider;

public class SongDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);

        final int id = getIntent().getIntExtra(Provider.Song._ID, 0);

        Log.d("SongDetailActivity", Integer.toString(id));

        getIntent().getBundleExtra("lyrics");
    }
}
