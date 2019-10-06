package android.gava.chatfortwo;

public class ChatMassage {

    String text;
    String name;
    String imageUrl;

    public ChatMassage () {

    }

    public ChatMassage (String text, String name, String imageUrl){
        this.text = text;
        this.name = name;
        this.imageUrl = imageUrl;

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
