package sk.upjs.ics.folkjukebox.logic.entity;


import java.net.PasswordAuthentication;
import java.util.List;

public class Room {

    private Long id;

    private Long hostId;

    private String title;

    private RoomSong roomSong;

    private List<Integer> users;

    // private RoomType TODO: think this through

    private String password;
}
