package cs4370.hciinsights.models;

import java.time.LocalDateTime;

/**
 * Represents a session in the HCI Insights platform.
 */
public class Session {

    /**
     * Unique identifier of the session.
     */
    private int sessionId;

    /**
     * Id of the participant in the session.
     */
    private int participantId;

    /**
     * Id of the study that the session is for.
     */
    private int studyId;

    /**
     * Date when the session is scheduled.
     */
    private LocalDateTime scheduled;

    /**
     * Notes for the session.
     */
    private String notes;

    /**
     * Score of the session.
     * 0 - Failure
     * 100 - Success
     */
    private Integer score;

    /**
     * Creates a session with the specified details.
     * 
     * @param sessionId     unique identifier of the session
     * @param participantId participant id in the session
     * @param studyId       study id that the session is for
     * @param scheduled     date when the session is scheduled for
     * @param notes         notes for the session
     * @param score         score of the session
     */
    public Session(int sessionId, int participantId, int studyId,
            LocalDateTime scheduled, String notes, Integer score) {
        this.sessionId = sessionId;
        this.participantId = participantId;
        this.studyId = studyId;
        this.scheduled = scheduled;
        this.notes = notes;
        this.score = score;
    }

    /**
     * Returns the session id.
     * 
     * @return session id
     */
    public int getSessionId() {
        return sessionId;
    }

    /**
     * Returns the participant id.
     * 
     * @return participant id
     */
    public int getParticipantId() {
        return participantId;
    }

    /**
     * Returns the study id.
     * 
     * @return study id
     */
    public int getStudyId() {
        return studyId;
    }

    /**
     * Returns the scheduled date of the session.
     * 
     * @return scheduled date of the session
     */
    public LocalDateTime getScheduled() {
        return scheduled;
    }

    /**
     * Returns the notes from the session.
     * 
     * @return session notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Returns the score of the session.
     * 
     * @return session score
     */
    public Integer getScore() {
        return score;
    }
}
