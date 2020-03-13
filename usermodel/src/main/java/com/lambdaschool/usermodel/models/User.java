package com.lambdaschool.usermodel.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

/**
 * The entity allowing interaction with the users table
 */
@Entity
@Table(name = "users")
public class User extends Auditable
{
    /**
     * The primary key (long) of the users table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;

    /**
     * The username (String). Cannot be null and must be unique
     */
    @Column(nullable = false,
        unique = true)
    private String username;

    /**
     * The password (String) for this user. Cannot be null. Never get displayed
     */
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * Primary email account of user. Could be used as the userid. Cannot be null and must be unique.
     */
    @Column(nullable = false,
        unique = true)
    @Email
    private String primaryemail;

    /**
     * List of emails associated with this user. Does not get saved in the database directly.
     * Forms a one to many relationship with useremail. One user to many useremails.
     */
    @OneToMany(mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @JsonIgnoreProperties(value = "user",
        allowSetters = true)
    private List<Useremail> useremails = new ArrayList<>();

    /**
     * Part of the join relationship between user and role
     * connects users to the user role combination
     */
    @OneToMany(mappedBy = "user",
        cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "user",
        allowSetters = true)
    private List<UserRoles> roles = new ArrayList<>();

    /**
     * Default constructor used primarily by the JPA.
     */
    public User()
    {
    }

    /**
     * Given the params, create a new user object
     * <p>
     * userid is autogenerated
     *
     * @param username     The username (String) of the user
     * @param password     The password (String) of the user
     * @param primaryemail The primary email (String) of the user
     * @param userRoles    The list of roles (userroles) assigned to this user
     */
    public User(
        String username,
        String password,
        String primaryemail,
        List<UserRoles> userRoles)
    {
        setUsername(username);
        setPassword(password);
        this.primaryemail = primaryemail;
        for (UserRoles ur : userRoles)
        {
            ur.setUser(this);
        }
        this.roles = userRoles;
    }

    /**
     * Getter for userid
     *
     * @return the userid (long) of the user
     */
    public long getUserid()
    {
        return userid;
    }

    /**
     * Setter for userid. Used primary for seeding data
     *
     * @param userid the new userid (long) of the user
     */
    public void setUserid(long userid)
    {
        this.userid = userid;
    }

    /**
     * Getter for username
     *
     * @return the username (String) lowercase
     */
    public String getUsername()
    {
        if (username == null) // this is possible when updating a user
        {
            return null;
        } else
        {
            return username.toLowerCase();
        }
    }

    /**
     * setter for username
     *
     * @param username the new username (String) converted to lowercase
     */
    public void setUsername(String username)
    {
        this.username = username.toLowerCase();
    }

    /**
     * getter for primary email
     *
     * @return the primary email (String) for the user converted to lowercase
     */
    public String getPrimaryemail()
    {
        if (primaryemail == null) // this is possible when updating a user
        {
            return null;
        } else
        {
            return primaryemail.toLowerCase();
        }
    }

    /**
     * setter for primary email
     *
     * @param primaryemail the new primary email (String) for the user converted to lowercase
     */
    public void setPrimaryemail(String primaryemail)
    {
        this.primaryemail = primaryemail.toLowerCase();
    }

    /**
     * Getter for the password
     *
     * @return the password (String) of the user
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Setter for password
     *
     * @param password the new password (String) for the user
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Getter for the list of useremails for this user
     *
     * @return the list of useremails (List(Useremail)) for this user
     */
    public List<Useremail> getUseremails()
    {
        return useremails;
    }

    /**
     * Setter for list of useremails for this user
     *
     * @param useremails the new list of useremails (List(Useremail)) for this user
     */
    public void setUseremails(List<Useremail> useremails)
    {
        this.useremails = useremails;
    }

    /**
     * Getter for user role combinations
     *
     * @return A list of user role combinations associated with this user
     */
    public List<UserRoles> getRoles()
    {
        return roles;
    }

    /**
     * Setter for user role combinations
     *
     * @param roles Change the list of user role combinations associated with this user to this one
     */
    public void setRoles(List<UserRoles> roles)
    {
        this.roles = roles;
    }
}
