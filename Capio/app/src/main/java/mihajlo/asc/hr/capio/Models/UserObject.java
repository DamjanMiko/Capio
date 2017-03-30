package mihajlo.asc.hr.capio.Models;

import java.util.List;

/**
 * Created by Damjan on 3/29/2017.
 */

public class UserObject {

    private Long id;
    private String firstName;
    private String lastName;
    private ContactInfo contactInfo;
    private Location location;
    private List<Unit> ownedUnits;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Unit> getOwnedUnits() {
        return ownedUnits;
    }

    public void setOwnedUnits(List<Unit> ownedUnits) {
        this.ownedUnits = ownedUnits;
    }
}
