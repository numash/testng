package entities;

/**
 * Created by numash on 02.12.2016.
 */

//poker player entity
public class PokerPlayer {

    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String city;
    private String country;
    private String address;
    private String phone;
    private String gender;
    private String birthday;

    //constructor
    public PokerPlayer(
            String username,
            String email,
            String firstname,
            String lastname,
            String city,
            String country,
            String address,
            String phone,
            String gender,
            String birthday) {

        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.country = country;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.birthday = birthday;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        return "entities.PokerPlayer{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PokerPlayer)) return false;

        PokerPlayer that = (PokerPlayer) o;

        if (!username.equals(that.username)) return false;
        if (!email.equals(that.email)) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        return birthday != null ? birthday.equals(that.birthday) : that.birthday == null;
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        return result;
    }
}

