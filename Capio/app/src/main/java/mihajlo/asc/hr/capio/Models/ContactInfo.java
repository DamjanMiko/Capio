package mihajlo.asc.hr.capio.Models;

/**
 * Created by Damjan on 3/29/2017.
 */

public class ContactInfo {

    private Long id;
    private String email;
    private String phoneNumber;

    public ContactInfo(Long id, String email, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
