package sk.upjs.ics.folkjukebox.logic.entity;

import java.util.List;
import java.util.Map;

public class Song {

    private Long id;

    private String title;

    private String lyrics;

    private List<String> songStyle;

    private Map<String, String> styleToAttribute;

    private String region;

    private String source;

    /*private String description;

    private int totalTimesPlayed;*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public List<String> getSongStyle() {
        return songStyle;
    }

    public void setSongStyle(List<String> songStyle) {
        this.songStyle = songStyle;
    }

    public Map<String, String> getStyleToAttribute() {
        return styleToAttribute;
    }

    public void setStyleToAttribute(Map<String, String> styleToAttribute) {
        this.styleToAttribute = styleToAttribute;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}

