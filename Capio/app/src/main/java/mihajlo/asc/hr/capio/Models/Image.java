package mihajlo.asc.hr.capio.Models;

import java.sql.Blob;

import mihajlo.asc.hr.capio.Models.ParcelableObjects.ParcelableImage;

/**
 * Created by Damjan on 3/29/2017.
 */

public class Image {

    private Long id;
    private Blob img;
    private String url;

    public Image() {}

    public Image(Long id, Blob img, String url) {
        this.id = id;
        this.img = img;
        this.url = url;
    }

    public Image(ParcelableImage parcelableImage) {
        this.id = parcelableImage.getId();
        this.img = parcelableImage.getImg();
        this.url = parcelableImage.getUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blob getImg() {
        return img;
    }

    public void setImg(Blob img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
