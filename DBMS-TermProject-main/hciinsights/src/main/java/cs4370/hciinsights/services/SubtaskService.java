package cs4370.hciinsights.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs4370.hciinsights.models.Subtask;

@Service
public class SubtaskService {

    private final DataSource dataSource;

    @Autowired
    public SubtaskService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private int getNextSubtaskId(Connection conn, int taskId) throws SQLException {
        final String sql = """
                SELECT COALESCE(MAX(subtask_id), 0) + 1 AS next_id
                FROM Subtask
                WHERE task_id = ?
                """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next())
                    return rs.getInt("next_id");
            }
        }
        return 1;
    }

    public boolean createSubtask(int taskId, String description, Integer expectedCompTime) throws SQLException {
        final String sql = """
                INSERT INTO Subtask (task_id, subtask_id, description, expected_comp_time)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection conn = dataSource.getConnection()) {
            int subtaskId = getNextSubtaskId(conn, taskId);
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, taskId);
                pstmt.setInt(2, subtaskId);
                pstmt.setString(3, description);
                if (expectedCompTime == null)
                    pstmt.setNull(4, Types.INTEGER);
                else
                    pstmt.setInt(4, expectedCompTime);
                return pstmt.executeUpdate() > 0;
            }
        }
    }

    public List<Subtask> getSubtasksByTaskId(int taskId) throws SQLException {
        final String sql = """
                SELECT subtask_id, task_id, description, expected_comp_time
                FROM Subtask
                WHERE task_id = ?
                ORDER BY subtask_id ASC
                """;
        List<Subtask> subtasks = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    subtasks.add(new Subtask(
                            rs.getInt("subtask_id"),
                            rs.getInt("task_id"),
                            rs.getString("description"),
                            (Integer) rs.getObject("expected_comp_time")));
                }
            }
        }
        return subtasks;
    }

    public Subtask getSubtaskById(int taskId, int subtaskId) throws SQLException {
        final String sql = """
                SELECT subtask_id, task_id, description, expected_comp_time
                FROM Subtask
                WHERE task_id = ? AND subtask_id = ?
                """;
        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            pstmt.setInt(2, subtaskId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Subtask(
                            rs.getInt("subtask_id"),
                            rs.getInt("task_id"),
                            rs.getString("description"),
                            (Integer) rs.getObject("expected_comp_time"));
                }
            }
        }
        return null;
    }

    public boolean deleteSubtask(int taskId, int subtaskId) throws SQLException {
        final String sql = """
                DELETE FROM Subtask
                WHERE task_id = ? AND subtask_id = ?
                """;
        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            pstmt.setInt(2, subtaskId);
            return pstmt.executeUpdate() > 0;
        }
    }
}
