import java.util.Objects;

public class Item {

    private final String name;
    private int code;
    private String description;
    private String picture;

    public Item(String name, int code, String description, String picture) {
        this.name = name;
        this.code = code;
        this.description = Objects.requireNonNullElse(description, "No description available");
        this.picture = Objects.requireNonNullElse(picture, "src/ItemPics/Unpiced.png");
    }

    public String toString() {
        return "["+code+"]: " + name + "____" + description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public String getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }
}
