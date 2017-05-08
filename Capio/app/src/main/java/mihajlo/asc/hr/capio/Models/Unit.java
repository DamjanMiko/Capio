package mihajlo.asc.hr.capio.Models;

import java.util.List;

/**
 * Created by Damjan on 3/29/2017.
 */

public class Unit {

    private Long id;
    private String description;
    private float price;
    private float area;
    private boolean rent;
    private Location location;
    private List<Image> images;

    public Unit() {}

    public Unit(Long id, String description, float price, float area, boolean rent, Location location, List<Image> images) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.area = area;
        this.rent = rent;
        this.location = location;
        this.images = images;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
