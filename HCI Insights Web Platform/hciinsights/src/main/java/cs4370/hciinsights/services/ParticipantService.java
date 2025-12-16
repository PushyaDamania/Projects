package cs4370.hciinsights.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs4370.hciinsights.models.Participant;

@Service
public class ParticipantService {

    private final DataSource dataSource;

    @Autowired
    public ParticipantService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean createParticipant(String fname, String lname, Integer age, String occupation, String occupationExp,
            String email) throws SQLException {

        final String sql = """
                INSERT INTO Participant (fname, lname, age, occupation, occupation_exp, email)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fname);
            pstmt.setString(2, lname);

            if (age == null)
                pstmt.setNull(3, Types.INTEGER);
            else
                pstmt.setInt(3, age);

            pstmt.setString(4, occupation);
            pstmt.setString(5, occupationExp);
            pstmt.setString(6, email);

            return pstmt.executeUpdate() > 0;

        }
    }

    public List<Participant> getAllParticipants() throws SQLException {

        final String sql = """
                SELECT participant_id, fname, lname, age, occupation, occupation_exp, email
                FROM Participant
                ORDER BY lname, fname
                """;

        List<Participant> participants = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    participants.add(new Participant(
                            rs.getInt("participant_id"),
                            rs.getString("fname"),
                            rs.getString("lname"),
                            (Integer) rs.getObject("age"),
                            rs.getString("occupation"),
                            rs.getString("occupation_exp"),
                            rs.getString("email")));
                }
            }
        }
        return participants;
    }

    public Participant getParticipantById(int participantId) throws SQLException {

        final String sql = """
                SELECT participant_id, fname, lname, age, occupation, occupation_exp, email
                FROM Participant
                WHERE participant_id = ?
                """;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, participantId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Participant(
                            rs.getInt("participant_id"),
                            rs.getString("fname"),
                            rs.getString("lname"),
                            (Integer) rs.getObject("age"),
                            rs.getString("occupation"),
                            rs.getString("occupation_exp"),
                            rs.getString("email"));
                }
            }
        }
        return null;
    }

    public boolean deleteParticipant(int participantId) throws SQLException {
        final String sql = """
                DELETE FROM Participant
                WHERE participant_id = ?
                """;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, participantId);
            return pstmt.executeUpdate() > 0;
        }
    }

}