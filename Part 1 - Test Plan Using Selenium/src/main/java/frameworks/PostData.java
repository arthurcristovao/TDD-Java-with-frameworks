package frameworks;

public class PostData {
    private String title;
    private String description;
    private String category;
    private String postURL;

    public PostData() {
    }

    public PostData(String title) {
        this.title = title;
    }

    public PostData(String title, String description) {
        this.title = title;
        this.description = description;

    }

    

    public PostData(String title, String description, String category, String postURL) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.postURL = postURL;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getPostURL() {
        return postURL;
    }

    @Override
    public String toString() {
        return "PostData{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", postURL='" + postURL + '\'' +
                '}';
    }
}
