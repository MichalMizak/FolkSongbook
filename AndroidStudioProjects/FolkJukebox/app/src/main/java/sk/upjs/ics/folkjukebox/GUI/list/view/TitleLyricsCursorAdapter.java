package sk.upjs.ics.folkjukebox.GUI.list.view;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sk.upjs.ics.folkjukebox.R;
import sk.upjs.ics.folkjukebox.logic.provider.Provider;


public class TitleLyricsCursorAdapter extends CursorAdapter {

    private LayoutInflater cursorInflater;

    public TitleLyricsCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        // R.layout.list_row is your xml layout for each row
        return cursorInflater.inflate(R.layout.title_lyrics_list_item, parent, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextViewList);
        String title = cursor.getString(cursor.getColumnIndex(Provider.Song.COLUMN_NAMES.title.name()));
        titleTextView.setText(title);

        TextView lyricsPreviewTextView = (TextView) view.findViewById(R.id.lyricsPreviewTextViewList);
        String lyrics = cursor.getString(cursor.getColumnIndex(Provider.Song.COLUMN_NAMES.lyrics.name()));
        lyricsPreviewTextView.setText(lyrics);
    }
}
