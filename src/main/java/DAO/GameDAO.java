package DAO;

import Modele.Game;
import Modele.ModelException;

import java.sql.*;

public class GameDAO {
    private Connection connection;

    public GameDAO(Connection connection) {
        this.connection = connection;
    }

    public int addGame(Game game) throws SQLException {
        String query = "INSERT INTO Games (map, score, status) VALUES (?, ?, ?)";
        // Utilisation de Statement.RETURN_GENERATED_KEYS pour récupérer l'ID du jeu inséré
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            System.out.println(game.getMap());
            statement.setString(1, game.getMap());
            statement.setInt(2, game.getScore());
            statement.setString(3, game.getStatus());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating game failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Retourne l'ID du jeu inséré
                } else {
                    throw new SQLException("Creating game failed, no ID obtained.");
                }
            }
        }
    }

    private static Game map(ResultSet resultSet) throws SQLException, ModelException {
        Game game = new Game();
        game.setId(resultSet.getInt("id"));
        game.setMap(resultSet.getString("map"));
        game.setScore(resultSet.getInt("score"));
        game.setStatus(resultSet.getString("status"));
        return game;
    }

    // Cette méthode pourrait ne plus être pertinente selon votre nouvelle conception
    public Game getGame(int gameId) throws SQLException, ModelException {
        String query = "SELECT id, map, score, status FROM Games WHERE id = ?";
        Game game = null;

        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, gameId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    game = map(resultSet);
                }
            }
        }

        return game;
    }

    // Méthode pour mettre à jour un jeu existant
    public boolean updateGame(int id, int score, String status) throws SQLException {
        String query = "UPDATE Games SET score = ?, status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, score);
            statement.setString(2, status);
            statement.setInt(3, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }


}
