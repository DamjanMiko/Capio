package mihajlo.asc.hr.capio.Models.ParcelableObjects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mihajlo.asc.hr.capio.Models.Image;
import mihajlo.asc.hr.capio.Models.Unit;

/**
 * Created by Damjan on 3/29/2017.
 */

public class ParcelableUnit implements Parcelable {

    private Long id;
    private String description;
    private float price;
    private float area;
    private boolean rent;
    private ParcelableLocation location;
    private List<ParcelableImage> images;

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<ParcelableUnit> CREATOR = new Parcelable.Creator<ParcelableUnit>() {
        public ParcelableUnit createFromParcel(Parcel in) {
            return new ParcelableUnit(in);
        }

        public ParcelableUnit[] newArray(int size) {
            return new ParcelableUnit[size];
        }
    };

    private ParcelableUnit(Parcel in) {
        id = in.readLong();
        description = in.readString();
        price = in.readFloat();
        area = in.readFloat();
        rent = in.readByte() != 0;
        location = in.readParcelable(ParcelableLocation.class.getClassLoader());
        images = new ArrayList<>();
        for (Object o : in.readParcelableArray(ParcelableImage.class.getClassLoader())) {
            images.add(((ParcelableImage) o));
        }
//        images = Arrays.asList((ParcelableImage[])
//                in.readParcelableArray(ParcelableImage.class.getClassLoader()));
    }

    public ParcelableUnit(Long id, String description, float price, float area, boolean rent, ParcelableLocation location, List<ParcelableImage> images) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.area = area;
        this.rent = rent;
        this.location = location;
        this.images = images;
    }

    public ParcelableUnit(Unit unit) {
        this.id = unit.getId();
        this.description = unit.getDescription();
        this.price = unit.getPrice();
        this.area = unit.getArea();
        this.rent = unit.isRent();
        this.location = new ParcelableLocation(unit.getLocation());
        images = new ArrayList<>();
        for (Image img : unit.getImages()) {
            images.add(new ParcelableImage(img));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(id);
        out.writeString(description);
        out.writeFloat(price);
        out.writeFloat(area);
        out.writeByte((byte) (rent ? 1 : 0));
        out.writeParcelable(location, flags);
        ParcelableImage[] arr = new ParcelableImage[images.size()];
        out.writeParcelableArray(images.toArray(arr), flags);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public boolean isRent() {
        return rent;
    }

    public void setRent(boolean rent) {
        this.rent = rent;
    }

    public ParcelableLocation getLocation() {
        return location;
    }

    public void setLocation(ParcelableLocation location) {
        this.location = location;
    }

    public List<ParcelableImage> getImages() {
        return images;
    }

    public void setImages(List<ParcelableImage> images) {
        this.images = images;
    }
}
