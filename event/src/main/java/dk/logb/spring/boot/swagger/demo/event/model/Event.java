package dk.logb.spring.boot.swagger.demo.event.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Objects;

public class Event {
    //event properties e.g. venue and artist
    private Integer id;
    @NotBlank
    @Size(min = 2, max = 80)
    private String venue;
    @NotBlank
    @Size(min = 2, max = 70)
    private String artist;
    private LocalDate date;

    public Event() {
    }

    public Event(Integer id, String venue, String artist, LocalDate date) {
        this.id = id;
        this.venue = venue;
        this.artist = artist;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(venue, event.venue) && Objects.equals(artist, event.artist) && Objects.equals(date, event.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, venue, artist, date);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", venue='" + venue + '\'' +
                ", artist='" + artist + '\'' +
                ", date=" + date +
                '}';
    }


}
