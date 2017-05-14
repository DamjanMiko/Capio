package mihajlo.asc.hr.capio.Models.ParcelableObjects;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Blob;

import mihajlo.asc.hr.capio.Models.Image;

/**
 * Created by Damjan on 4/1/2017.
 */

public class ParcelableImage implements Parcelable {
    private Long id;
    private Blob img;
    private String url;

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<ParcelableImage> CREATOR = new Parcelable.Creator<ParcelableImage>() {
        public ParcelableImage createFromParcel(Parcel in) {
            return new ParcelableImage(in);
        }

        public ParcelableImage[] newArray(int size) {
            return new ParcelableImage[size];
        }
    };

    private ParcelableImage(Parcel in) {
        id = in.readLong();
        url = in.readString();
    }

    public ParcelableImage(Long id, Blob img, String url) {
        this.id = id;
        this.img = img;
        this.url = url;
    }

    public ParcelableImage(Image image) {
        this.id = image.getId();
        this.img = image.getImg();
        this.url = image.getUrl();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(id);
        out.writeString(url);
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
