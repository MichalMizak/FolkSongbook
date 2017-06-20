package sk.upjs.ics.folkjukebox.logic.provider;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public interface Provider {

    String AUTHORITY = "sk.upjs.ics.folkjukebox";

    Uri SONG_CONTENT_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .appendPath(Song.TABLE_NAME)
            .build();

    Uri STYLE_CONTENT_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .appendPath(Style.TABLE_NAME)
            .build();

    Uri ATTRIBUTE_CONTENT_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .appendPath(Attribute.TABLE_NAME)
            .build();

    Uri ATTRIBUTE_SONG_CONTENT_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .appendPath(AttributeSong.TABLE_NAME)
            .build();


    public interface Song extends BaseColumns {

        String TABLE_NAME = "Song";

        enum COLUMN_NAMES {title, lyrics, region, styleId, source}
    }

    public interface Style extends BaseColumns {

        String TABLE_NAME = "Style";

        enum COLUMN_NAMES {name}

    }

    public interface Attribute extends BaseColumns {

        String TABLE_NAME = "Attribute";

        enum COLUMN_NAMES {styleId, name}

    }

    public interface AttributeSong extends BaseColumns {

        String TABLE_NAME = "AttributeSong";

        enum COLUMN_NAMES {attributeId, styleId}

    }

}

