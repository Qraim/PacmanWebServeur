package DAO;

import Modele.Game;
import Modele.ModelException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameDAO {
    private Connection connection;

    public GameDAO(Connection connection) {
        this.connection = connection;
    }

    public void addGame(Game game, int userId) throws SQLException {
        String query = "INSERT INTO Games (map, score, status, joueur_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, game.getMap());
            statement.setInt(2, game.getScore());
            statement.setString(3, game.getStatus());
            statement.setInt(4, userId);
            statement.executeUpdate();
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


    public Game getGame(int gameId) throws SQLException, ModelException {
        String query = "SELECT id, map, score, status, joueur_id FROM Games WHERE id = ?";
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

    public List<Game> getGamesByUserId(int userId) throws SQLException {
        List<Game> games = new ArrayList<>();
        String query = "SELECT id, map, score, status FROM Games WHERE joueur_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    games.add(map(resultSet));
                }
            } catch (ModelException e) {
                throw new RuntimeException(e);
            }
        }

        return games;
    }

    public void updateGame(int id, int score) {
    }


    // Ajouter d'autres méthodes CRUD ici...
}
