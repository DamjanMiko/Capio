package mihajlo.asc.hr.capio.Models;

/**
 * Created by marko.raguz on 22.3.2017..
 */

public class User {
    public String id;
    public String firstName;
    public String lastName;
    public String imgUrl;
    public String dateOfBirth; // Treba bit date klase
    public String phone;
    public String address;
    public String email;
    public String gender;

    public User(String id,String firstName,String lastName, String imgUrl, String dateOfBirth, String phone,String address,String email,String gender){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imgUrl = imgUrl;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.gender = gender;
    }
}