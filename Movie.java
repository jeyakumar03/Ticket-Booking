public class Movie {
    private String name;
    private String language;  
    private long duration;
    public Movie(String name, String language, long duration) {
        this.name = name;
        this.language = language;  
        this.duration = duration;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLanguage() {  
        return language;
    }
    public void setLanguage(String language) {  
        this.language = language;
    }
    public long getDuration() {
        return duration;
    }
    public void setDuration(long duration) {  
        this.duration = duration;
    }
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
   
}

