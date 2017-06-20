package sk.upjs.ics.folkjukebox.logic.dao.implementations;

import android.app.LoaderManager;
import android.content.Context;
import android.os.Bundle;

import java.util.List;

import sk.upjs.ics.folkjukebox.logic.dao.interfaces.SongDao;
import sk.upjs.ics.folkjukebox.logic.entity.Song;


public class TestSongDao implements SongDao {

    public TestSongDao() {
          /*    LoaderManager loaderManager, Context context
    this.loaderManager = loaderManager;
        loaderManager.initLoader(0, Bundle.EMPTY, context);
*/
    }

    @Override
    public List<Song> getAll() { return null; }

    @Override
    public Song getById(Long id) {
        return null;
    }

    @Override
    public List<Song> getByCategory(String style) {
        return null;
    }
}
