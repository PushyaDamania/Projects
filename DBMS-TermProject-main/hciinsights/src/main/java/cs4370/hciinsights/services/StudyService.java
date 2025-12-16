package cs4370.hciinsights.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs4370.hciinsights.models.Study;
import cs4370.hciinsights.models.Status;

@Service
public class StudyService {

    private final DataSource dataSource;

    @Autowired
    public StudyService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean createStudy(int userId, String title, String description, String platform, Status status)
            throws SQLException {

        final String sql = """
                INSERT INTO Study (user_id, title, description, platform, status)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, title);
            pstmt.setString(3, description);
            pstmt.setString(4, platform);
            pstmt.setString(5, status.name());

            return pstmt.executeUpdate() > 0;
        }

    }

    public Study getStudyById(int studyId) throws SQLException {

        final String sql = """
                SELECT study_id, user_id, title, description, platform, status, created_at
                FROM Study
                WHERE study_id = ?
                """;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studyId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    return new Study(
                            rs.getInt("study_id"),
                            rs.getInt("user_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("platform"),
                            Status.valueOf(rs.getString("status")),
                            rs.getTimestamp("created_at").toString());
                }
            }
        }
        return null;
    }

    public List<Study> getStudiesByUser(int userId) throws SQLException {

        final String sql = """
                SELECT study_id, user_id, title, description, platform, status, created_at
                FROM Study
                WHERE user_id = ?
                ORDER BY created_at DESC
                """;

        List<Study> studies = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    studies.add(new Study(
                            rs.getInt("study_id"),
                            rs.getInt("user_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("platform"),
                            Status.valueOf(rs.getString("status")),
                            rs.getTimestamp("created_at").toString()));
                }
            }
        }
        return studies;
    }

    public boolean updateStudy(int studyId, String title, String description, String platform, Status status)
            throws SQLException {

        final String sql = """
                UPDATE Study
                SET title = ?, description = ?, platform = ?, status = ?
                WHERE study_id = ?
                """;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, platform);
            pstmt.setString(4, status.name());

            return pstmt.executeUpdate() > 0;

        }

    }

    public boolean deleteStudy(int studyId) throws SQLException {

        final String sql = """
                DELETE FROM Study
                WHERE study_id = ?
                """;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studyId);

            return pstmt.executeUpdate() > 0;

        }

    }

}
