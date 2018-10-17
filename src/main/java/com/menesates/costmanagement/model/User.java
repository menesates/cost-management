package com.menesates.costmanagement.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "Users")
@NamedQuery(query = "select u from User u", name = "find_all_users")
public class User {

    @Id
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private boolean enabled;
    @NotEmpty
    @Email
    @Column(unique = true)
    private String email;
    private String firstName;
    private String lastName;
    private Date birthDate;
    // todo profil image
    //private String profilImageUrl; Bunu nasıl yapacağıma karar vermeliyim.


    public User() {
    }

    public User(@NotEmpty String username,
                @NotEmpty String password,
                boolean enabled,
                @NotEmpty @Email String email,
                String firstName,
                String lastName,
                Date birthDate) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
