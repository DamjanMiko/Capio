package mihajlo.asc.hr.capio.Models;

import mihajlo.asc.hr.capio.Models.ParcelableObjects.ParcelableLocation;

/**
 * Created by Damjan on 3/29/2017.
 */

public class Location {

    private Long id;
    private String streetName;
    private int houseNumber;
    private int postalCode;
    private String country;
    private String city;

    public Location() {}

    public Location(Long id, String streetName, int houseNumber, int postalCode, String country, String city) {
        this.id = id;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.country = country;
        this.city = city;
    }

    public Location(ParcelableLocation parcelableLocation) {
        this.id = parcelableLocation.getId();
        this.streetName = parcelableLocation.getStreetName();
        this.houseNumber = parcelableLocation.getHouseNumber();
        this.postalCode = parcelableLocation.getPostalCode();
        this.country = parcelableLocation.getCountry();
        this.city = parcelableLocation.getCity();
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
