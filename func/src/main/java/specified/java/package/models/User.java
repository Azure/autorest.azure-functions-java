package specified.java.package.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The User model.
 */
@Fluent
public final class User {
    /*
     * The id property.
     */
    @JsonProperty(value = "id")
    private Long id;

    /*
     * The username property.
     */
    @JsonProperty(value = "username")
    private String username;

    /*
     * The firstName property.
     */
    @JsonProperty(value = "firstName")
    private String firstName;

    /*
     * The lastName property.
     */
    @JsonProperty(value = "lastName")
    private String lastName;

    /*
     * The email property.
     */
    @JsonProperty(value = "email")
    private String email;

    /*
     * The password property.
     */
    @JsonProperty(value = "password")
    private String password;

    /*
     * The phone property.
     */
    @JsonProperty(value = "phone")
    private String phone;

    /*
     * User Status
     */
    @JsonProperty(value = "userStatus")
    private Integer userStatus;

    /**
     * Get the id property: The id property.
     * 
     * @return the id value.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Set the id property: The id property.
     * 
     * @param id the id value to set.
     * @return the User object itself.
     */
    public User setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get the username property: The username property.
     * 
     * @return the username value.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Set the username property: The username property.
     * 
     * @param username the username value to set.
     * @return the User object itself.
     */
    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Get the firstName property: The firstName property.
     * 
     * @return the firstName value.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Set the firstName property: The firstName property.
     * 
     * @param firstName the firstName value to set.
     * @return the User object itself.
     */
    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Get the lastName property: The lastName property.
     * 
     * @return the lastName value.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Set the lastName property: The lastName property.
     * 
     * @param lastName the lastName value to set.
     * @return the User object itself.
     */
    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Get the email property: The email property.
     * 
     * @return the email value.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Set the email property: The email property.
     * 
     * @param email the email value to set.
     * @return the User object itself.
     */
    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Get the password property: The password property.
     * 
     * @return the password value.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Set the password property: The password property.
     * 
     * @param password the password value to set.
     * @return the User object itself.
     */
    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * Get the phone property: The phone property.
     * 
     * @return the phone value.
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Set the phone property: The phone property.
     * 
     * @param phone the phone value to set.
     * @return the User object itself.
     */
    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * Get the userStatus property: User Status.
     * 
     * @return the userStatus value.
     */
    public Integer getUserStatus() {
        return this.userStatus;
    }

    /**
     * Set the userStatus property: User Status.
     * 
     * @param userStatus the userStatus value to set.
     * @return the User object itself.
     */
    public User setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
        return this;
    }
}
