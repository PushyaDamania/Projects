package cs4370.hciinsights.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {

    private final DataSource dataSource;

    @Autowired
    public AnalyticsService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Map<String, Object>> getAverageTaskTimes(int userId, Integer studyId) throws SQLException {

        String sql = """
                SELECT t.task_id, t.description, AVG(r.completion_time) AS avg_time
                FROM Result r
                JOIN Task t        ON r.task_id = t.task_id
                JOIN Session s     ON r.session_id = s.session_id
                JOIN Study st      ON s.study_id = st.study_id
                JOIN (
                    SELECT DISTINCT t2.task_id
                    FROM Task t2
                    JOIN Study st2 ON t2.study_id = st2.study_id
                    WHERE st2.user_id = ?
                """
                + (studyId != null ? " AND st2.study_id = ?" : "")
                + "\n" + """
                    ORDER BY t2.task_id
                    LIMIT 15
                ) AS topTasks ON topTasks.task_id = t.task_id
                WHERE st.user_id = ?
                """
                + (studyId != null ? " AND st.study_id = ?" : "")
                + "\n" + """
                GROUP BY t.task_id, t.description
                ORDER BY t.task_id;
                """;

        List<Map<String, Object>> avgTaskTimes = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int idx = 1;
            pstmt.setInt(idx++, userId);
            if (studyId != null) {
                pstmt.setInt(idx++, studyId);
            }
            pstmt.setInt(idx++, userId);
            if (studyId != null) {
                pstmt.setInt(idx++, studyId);
            }

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Map<String, Object> avgTaskTime = new HashMap<>();
                    avgTaskTime.put("taskId", rs.getInt("task_id"));
                    avgTaskTime.put("description", rs.getString("description"));
                    avgTaskTime.put("avgTime", rs.getDouble("avg_time"));
                    avgTaskTimes.add(avgTaskTime);
                }
            }
        }
        return avgTaskTimes;
    }

    public List<Map<String, Object>> getTaskDifficultyRatios(int userId, Integer studyId) throws SQLException {

        String sql = """
                SELECT t.task_id, t.description, AVG(r.completion_time * 1.0 / t.expected_comp_time) AS difficulty_ratio
                FROM Result r
                JOIN Task t   ON r.task_id = t.task_id
                JOIN Session s ON r.session_id = s.session_id
                JOIN Study st ON s.study_id = st.study_id
                WHERE st.user_id = ?
                AND r.completion_time IS NOT NULL
                AND t.expected_comp_time IS NOT NULL
                """
                + (studyId != null ? " AND st.study_id = ?" : "")
                + "\n" + """
                GROUP BY t.task_id, t.description
                ORDER BY difficulty_ratio DESC
                LIMIT 15
                """;

        List<Map<String, Object>> difficultyRatios = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int idx = 1;
            pstmt.setInt(idx++, userId);
            if (studyId != null) {
                pstmt.setInt(idx++, studyId);
            }

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("taskId", rs.getInt("task_id"));
                    row.put("description", rs.getString("description"));
                    row.put("difficultyRatio", rs.getDouble("difficulty_ratio"));
                    difficultyRatios.add(row);
                }
            }
        }
        return difficultyRatios;
    }

    public List<Map<String, Object>> getAverageParticipantScores(int userId, Integer studyId) throws SQLException {

        String sql = """
                SELECT p.participant_id, CONCAT(p.fname, ' ', p.lname) AS name, AVG(s.score) AS avg_score
                FROM Participant p
                JOIN Session s ON p.participant_id = s.participant_id
                JOIN Study st  ON s.study_id = st.study_id
                WHERE st.user_id = ?
                """
                + (studyId != null ? " AND st.study_id = ?" : "")
                + "\n" + """
                GROUP BY p.participant_id, name
                ORDER BY avg_score DESC
                LIMIT 15;
                """;

        List<Map<String, Object>> avgParticipantScores = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int idx = 1;
            pstmt.setInt(idx++, userId);
            if (studyId != null) {
                pstmt.setInt(idx++, studyId);
            }

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Map<String, Object> avgParticipantScore = new HashMap<>();
                    avgParticipantScore.put("participantId", rs.getInt("participant_id"));
                    avgParticipantScore.put("name", rs.getString("name"));
                    avgParticipantScore.put("avgScore", rs.getDouble("avg_score"));
                    avgParticipantScores.add(avgParticipantScore);
                }
            }
        }
        return avgParticipantScores;
    }
}