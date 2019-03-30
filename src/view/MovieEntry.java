package view;

public class MovieEntry {
    String imageUrl;
    String title;
    int ID;

    public MovieEntry(String imageUrl, String title, int ID) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.ID = ID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public int getID() {
        return ID;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
