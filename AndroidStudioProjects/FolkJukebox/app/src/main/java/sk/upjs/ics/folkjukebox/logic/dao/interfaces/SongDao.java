package sk.upjs.ics.folkjukebox.logic.dao.interfaces;


import java.util.List;

import sk.upjs.ics.folkjukebox.logic.entity.Song;

public interface SongDao {

    List<Song> getAll();

    Song getById(Long id);

    List<Song> getByCategory(String style);

}
