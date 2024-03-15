package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserGameDAO {
    private Connection connection;

    public UserGameDAO(Connection connection) {
        this.connection = connection;
    }

    public void addUserGame(int userId, int gameId) throws SQLException {
        String query = "INSERT INTO user_games (user_id, game_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, gameId);
            statement.executeUpdate();
        }
    }

    public List<Integer> getGamesByUserId(int userId) throws SQLException {
        List<Integer> gameIds = new ArrayList<>();
        String query = "SELECT game_id FROM user_games WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    gameIds.add(resultSet.getInt("game_id"));
                }
            }
        }
        return gameIds;
    }

    public List<Integer> getUsersByGameId(int gameId) throws SQLException {
        List<Integer> userIds = new ArrayList<>();
        String query = "SELECT user_id FROM user_games WHERE game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, gameId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    userIds.add(resultSet.getInt("user_id"));
                }
            }
        }
        return userIds;
    }

    public void AddScoreForUser(int id, int score, int gameid) throws SQLException {
        String query = "UPDATE user_games SET score = ? WHERE user_id = ? AND game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, score);
            statement.setInt(2, id);
            statement.setInt(3, gameid);
            statement.executeUpdate();
        }
    }

    // Supprime une association entre un utilisateur et un jeu
    public void deleteUserGame(int userId, int gameId) throws SQLException {
        String query = "DELETE FROM user_games WHERE user_id = ? AND game_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, gameId);
            statement.executeUpdate();
        }
    }

}
