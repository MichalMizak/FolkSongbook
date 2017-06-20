package sk.upjs.ics.folkjukebox.logic.factory;


import sk.upjs.ics.folkjukebox.logic.dao.implementations.TestSongDao;
import sk.upjs.ics.folkjukebox.logic.dao.interfaces.SongDao;

public enum DaoFactory{
    INSTANCE;

    SongDao songDao;

    public SongDao getSongDao() {
        if (songDao == null) {
            return new TestSongDao();
        }
        return songDao;
    }
}
