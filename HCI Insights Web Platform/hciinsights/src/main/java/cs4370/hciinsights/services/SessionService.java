package cs4370.hciinsights.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import cs4370.hciinsights.models.Session;

@Service
public class SessionService {

    private final DataSource dataSource;

    public SessionService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean createSession(int participantId, int studyId, Timestamp scheduled, String notes, Integer score)
            throws SQLException {
        final String sql = """
                INSERT INTO Session (participant_id, study_id, scheduled, notes, score)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, participantId);
            pstmt.setInt(2, studyId);
            pstmt.setTimestamp(3, scheduled);
            pstmt.setString(4, notes);
            if (score == null) {
                pstmt.setNull(5, Types.INTEGER);
            } else {
                pstmt.setInt(5, score);
            }
            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Session> getSessionsByStudy(int studyId) throws SQLException {
        final String sql = """
                SELECT session_id, participant_id, study_id, scheduled, notes, score
                FROM Session
                WHERE study_id = ?
                ORDER BY scheduled ASC
                """;
        List<Session> sessions = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studyId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    sessions.add(new Session(
                            rs.getInt("session_id"),
                            rs.getInt("participant_id"),
                            rs.getInt("study_id"),
                            rs.getTimestamp("scheduled").toLocalDateTime(),
                            rs.getString("notes"),
                            (Integer) rs.getObject("score")));
                }
            }
        }
        return sessions;
    }

    public Session getSessionById(int sessionId) throws SQLException {
        final String sql = """
                SELECT session_id, participant_id, study_id, scheduled, notes, score
                FROM Session
                WHERE session_id = ?
                """;
        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sessionId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Session(
                            rs.getInt("session_id"),
                            rs.getInt("participant_id"),
                            rs.getInt("study_id"),
                            rs.getTimestamp("scheduled").toLocalDateTime(),
                            rs.getString("notes"),
                            (Integer) rs.getObject("score"));
                }
            }
        }
        return null;
    }

    public boolean deleteSession(int sessionId) throws SQLException {
        final String sql = """
                DELETE FROM Session
                WHERE session_id = ?
                """;
        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sessionId);
            return pstmt.executeUpdate() > 0;
        }
    }
}
