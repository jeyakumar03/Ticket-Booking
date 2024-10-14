import java.sql.Timestamp;

public class Show {
    private int showId;
    private String movieName;
    private Timestamp showtime;
    private int availableSeats;
    private int totalSeats;
    public Show(int showId, String movieName, Timestamp showtime, int availableSeats, int totalSeats) {
        this.showId = showId;
        this.movieName = movieName;
        this.showtime = showtime;
        this.availableSeats = availableSeats;
        this.totalSeats = totalSeats;
    }
    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Timestamp getShowtime() {
        return showtime;
    }

    public void setShowtime(Timestamp showtime) {
        this.showtime = showtime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }
}

