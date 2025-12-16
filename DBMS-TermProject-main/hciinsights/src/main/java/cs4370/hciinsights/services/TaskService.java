package cs4370.hciinsights.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs4370.hciinsights.models.Task;

@Service
public class TaskService {

    private final DataSource dataSource;

    @Autowired
    public TaskService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean createTask(int studyId, int taskOrder, String description,
            String successCriteria, int expectedCompTime) throws SQLException {

        final String sql = """
                INSERT INTO Task (study_id, task_order, description, success_criteria, expected_comp_time)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studyId);
            pstmt.setInt(2, taskOrder);
            pstmt.setString(3, description);
            pstmt.setString(4, successCriteria);
            pstmt.setInt(5, expectedCompTime);

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Task> getTasksByStudy(int studyId) throws SQLException {

        final String sql = """
                SELECT task_id, study_id, task_order, description, success_criteria, expected_comp_time
                FROM Task
                WHERE study_id = ?
                ORDER BY task_order ASC
                """;

        List<Task> tasks = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studyId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tasks.add(new Task(
                            rs.getInt("task_id"),
                            rs.getInt("study_id"),
                            rs.getInt("task_order"),
                            rs.getString("description"),
                            rs.getString("success_criteria"),
                            rs.getInt("expected_comp_time")));
                }
            }
        }
        return tasks;
    }

    public Task getTaskById(int taskId) throws SQLException {

        final String sql = """
                SELECT task_id, study_id, task_order, description, success_criteria, expected_comp_time
                FROM Task
                WHERE task_id = ?
                """;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, taskId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Task(
                            rs.getInt("task_id"),
                            rs.getInt("study_id"),
                            rs.getInt("task_order"),
                            rs.getString("description"),
                            rs.getString("success_criteria"),
                            rs.getInt("expected_comp_time"));
                }
            }
        }
        return null;
    }

    public boolean deleteTask(int taskId) throws SQLException {

        final String sql = """
                DELETE FROM Task WHERE task_id = ?
                """;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, taskId);
            return pstmt.executeUpdate() > 0;
        }
    }
}
