package cs4370.hciinsights.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import cs4370.hciinsights.models.User;

@Service
@SessionScope
public class UserService {

    private final DataSource dataSource;

    private final BCryptPasswordEncoder passwordEncoder;

    private User loggedInUser = null;

    @Autowired
    public UserService(DataSource dataSource) {
        this.dataSource = dataSource;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean authenticate(String username, String password) throws SQLException {
        final String sql = """
                SELECT user_id, fname, lname, email, password, registered
                FROM Users
                WHERE username = ?
                """;
        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {

                    String storedPasswordHash = rs.getString("password");
                    boolean isPassMatch = passwordEncoder.matches(password, storedPasswordHash);

                    if (isPassMatch) {
                        int user_id = rs.getInt("user_id");
                        String fname = rs.getString("fname");
                        String lname = rs.getString("lname");
                        String email = rs.getString("email");
                        String registered = rs.getTimestamp("registered").toString();
                        loggedInUser = new User(user_id, fname, lname, username, email, registered);
                    }
                    return isPassMatch;
                }
            }
        }
        return false;
    }

    public void unAuthenticate() {
        loggedInUser = null;
    }

    public boolean isAuthenticated() {
        return loggedInUser != null;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public boolean registerUser(String username, String fname, String lname, String password, String email)
            throws SQLException {

        final String sql = """
                INSERT INTO Users (fname, lname, username, password, email) values (?, ?, ?, ?, ?)
                """;

        try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fname);
            pstmt.setString(2, lname);
            pstmt.setString(3, username);
            pstmt.setString(4, passwordEncoder.encode(password));
            pstmt.setString(5, email);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

}