package cs4370.hciinsights.models;

/**
 * Represents a participant within the HCI Insights platform.
 */
public class Participant {

    /**
     * Unique identifier of a participant.
     */
    private final int participantId;

    /**
     * Participant first name.
     */
    private final String fname;

    /**
     * Participant last name.
     */
    private final String lname;

    /**
     * Participant age.
     */
    private final Integer age;

    /**
     * Participant occupation.
     */
    private String occupation;

    /**
     * Participant occupational experience.
     * (i.e. senior, intermediate, entry, etc.)
     */
    private String occupationExp;

    /**
     * Participant email.
     */
    private String email;

    /**
     * Creates a participant with the specified details.
     * 
     * @param participantId unique identifier of the participant
     * @param fname         participant first name
     * @param lname         participant last name
     * @param age           participant age
     * @param occupation    participant occupation
     * @param occupationExp participant occupational experience
     * @param email         participant email
     */
    public Participant(int participantId, String fname, String lname, Integer age, String occupation,
            String occupationExp, String email) {
        this.participantId = participantId;
        this.fname = fname;
        this.lname = lname;
        this.age = age;
        this.occupation = occupation;
        this.occupationExp = occupationExp;
        this.email = email;
    }

    /**
     * Returns the id of the participant.
     * 
     * @return participant id
     */
    public int getParticipantId() {
        return participantId;
    }

    /**
     * Returns the participant first name.
     * 
     * @return participant first name
     */
    public String getFname() {
        return fname;
    }

    /**
     * Returns the participant last name.
     * 
     * @return participant last name
     */
    public String getLname() {
        return lname;
    }

    /**
     * Returns the participant age.
     * 
     * @return participant age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Returns the participant occupation.
     * 
     * @return participant occupation
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * Returns the participant occupational experience.
     * 
     * @return participant occupational experience
     */
    public String getOccupationExp() {
        return occupationExp;
    }

    /**
     * Returns the participant email.
     * 
     * @return participant email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the participant full name.
     * 
     * @return participant first name and participant last name
     */
    public String getFullName() {
        return fname + " " + lname;
    }

}