package android.sinabro.sing_for_you.model;

/**
 * Created by parktaeim on 2017. 8. 25..
 */

public class PlayerItem {
    int Image;
    String title;
    String singer;

    public PlayerItem(int image, String title, String singer) {
        Image = image;
        this.title = title;
        this.singer = singer;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }




}
