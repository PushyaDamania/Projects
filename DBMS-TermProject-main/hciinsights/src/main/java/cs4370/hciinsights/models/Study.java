package cs4370.hciinsights.models;

/**
 * Represents a study of the HCI Insights platform
 */
public class Study {

    /**
     * Unique identifier for the study.
     */
    private final int studyId;

    /**
     * Unique identifier for user who created the study.
     */
    private final int userId;

    /**
     * Title of the study.
     */
    private final String title;

    /**
     * Description of the study.
     */
    private final String description;

    /**
     * Platform of which the study occured on.
     */
    private final String platform;

    /**
     * Status of the study.
     */
    private final Status status;

    /**
     * Date when the study was created.
     */
    private final String created_at;

    /**
     * Constructs a study with the specified details.
     * 
     * @param studyId
     * @param userId
     * @param title
     * @param description
     * @param platform
     * @param status
     * @param created_at
     */
    public Study(int studyId, int userId, String title, String description, String platform, Status status,
            String created_at) {
        this.studyId = studyId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.platform = platform;
        this.status = status;
        this.created_at = created_at;
    }

    /**
     * Returns the id of the study.
     * 
     * @return the id of the study
     */
    public int getStudyId() {
        return studyId;
    }

    /**
     * Returns the user id who created the study.
     * 
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Returns the title of the study.
     * 
     * @return the study title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the description of the study.
     * 
     * @return the study description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the platform type on which the study took place.
     * 
     * @return the study platform type
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * Returns the current status of the study.
     * 
     * @return the study status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Returns the date at which the study was created.
     * 
     * @return the study creation date
     */
    public String getCreatedAt() {
        return created_at;
    }
}