package cs4370.hciinsights.models;

/**
 * Represents a user of the HCI Insights platform.
 */
public class User {

    /**
     * Unique identifier for the user.
     */
    private final int userId;

    /**
     * First name of the user.
     */
    private final String fname;

    /**
     * Last name of the user.
     */
    private final String lname;

    /**
     * Username of the user.
     */
    private final String username;

    /**
     * Email address of the user.
     */
    private final String email;

    /**
     * Date when the user registered their account.
     */
    private final String registered;

    /**
     * Constructs a User with specified details.
     * 
     * @param userId     the unique identifier of the user
     * @param fname      the first name of the user
     * @param lname      the last name of the user
     * @param username   the username of the user
     * @param email      the email of the user
     * @param registered the date when the user registered their account
     */
    public User(int userId, String fname, String lname, String username, String email, String registered) {
        this.userId = userId;
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.email = email;
        this.registered = registered;
    }

    /**
     * Returns the user ID.
     * 
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Returns the first name of the user.
     * 
     * @return the first name of the user
     */
    public String getFirstName() {
        return fname;
    }

    /**
     * Returns the last name of the user.
     * 
     * @return the last name of the user
     */
    public String getLastName() {
        return lname;
    }

    /**
     * Returns the username of the user.
     * 
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the email of the user.
     * 
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the date when the user registered their account.
     * 
     * @return the date when the user registered their account
     */
    public String getRegistered() {
        return registered;
    }
}