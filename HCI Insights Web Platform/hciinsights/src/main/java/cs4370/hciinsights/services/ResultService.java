package cs4370.hciinsights.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cs4370.hciinsights.models.Result;

@Service
public class ResultService {

    private final DataSource dataSource;

    @Autowired
    public ResultService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean createResult(int sessionId, int taskId, boolean isComplete,
            Integer completionTime, Integer errors, String notes) throws SQLException {

        final String sql = """
                INSERT INTO Result (session_id, task_id, is_complete, completion_time, errors, notes)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, sessionId);
            pstmt.setInt(2, taskId);
            pstmt.setBoolean(3, isComplete);

            if (completionTime == null)
                pstmt.setNull(4, Types.INTEGER);
            else
                pstmt.setInt(4, completionTime);

            if (errors == null)
                pstmt.setNull(5, Types.INTEGER);
            else
                pstmt.setInt(5, errors);

            pstmt.setString(6, notes);

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Result> getResultsBySession(int sessionId) throws SQLException {

        final String sql = """
                SELECT result_id, session_id, task_id, is_complete, completion_time, errors, notes
                FROM Result
                WHERE session_id = ?
                """;

        List<Result> results = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, sessionId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(new Result(
                            rs.getInt("result_id"),
                            rs.getInt("session_id"),
                            rs.getInt("task_id"),
                            rs.getBoolean("is_complete"),
                            (Integer) rs.getObject("completion_time"),
                            (Integer) rs.getObject("errors"),
                            rs.getString("notes")));
                }
            }
        }

        return results;
    }

    public Result getResultById(int resultId) throws SQLException {
        final String sql = """
                SELECT result_id, session_id, task_id, is_complete,
                        completion_time, errors, notes
                FROM Result
                WHERE result_id = ?
                """;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, resultId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Result(
                            rs.getInt("result_id"),
                            rs.getInt("session_id"),
                            rs.getInt("task_id"),
                            rs.getBoolean("is_complete"),
                            (Integer) rs.getObject("completion_time"),
                            (Integer) rs.getObject("errors"),
                            rs.getString("notes"));
                }
            }
        }
        return null;
    }

    public boolean deleteResult(int resultId) throws SQLException {

        final String sql = """
                DELETE FROM Result
                WHERE result_id = ?
                """;
        ;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, resultId);
            return pstmt.executeUpdate() > 0;
        }
    }
}
