package mihajlo.asc.hr.capio.Models.ParcelableObjects;

import android.os.Parcel;
import android.os.Parcelable;

import mihajlo.asc.hr.capio.Models.Location;

/**
 * Created by Damjan on 4/1/2017.
 */

public class ParcelableLocation implements Parcelable {

    private Long id;
    private String streetName;
    private int houseNumber;
    private int postalCode;
    private String country;
    private String city;

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<ParcelableLocation> CREATOR = new Parcelable.Creator<ParcelableLocation>() {
        public ParcelableLocation createFromParcel(Parcel in) {
            return new ParcelableLocation(in);
        }

        public ParcelableLocation[] newArray(int size) {
            return new ParcelableLocation[size];
        }
    };

    private ParcelableLocation(Parcel in) {
        id = in.readLong();
        this.streetName = in.readString();
        this.houseNumber = in.readInt();
        this.postalCode = in.readInt();
        this.country = in.readString();
        this.city = in.readString();
    }

    public ParcelableLocation(Long id, String streetName, int houseNumber, int postalCode, String country, String city) {
        this.id = id;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.country = country;
        this.city = city;
    }

    public ParcelableLocation(Location location) {
        id = location.getId();
        streetName = location.getStreetName();
        houseNumber = location.getHouseNumber();
        postalCode = location.getPostalCode();
        country = location.getCountry();
        city = location.getCity();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(id);
        out.writeString(streetName);
        out.writeInt(houseNumber);
        out.writeInt(postalCode);
        out.writeString(country);
        out.writeString(city);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
