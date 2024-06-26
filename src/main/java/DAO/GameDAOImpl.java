package DAO;

import Modele.Game;
import Modele.ModelException;

import java.sql.*;
import java.util.ArrayList;

public class GameDAOImpl implements GameDAO {
    private Connection connection;

    public GameDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public int addGame(Game game) throws SQLException {
        String query = "INSERT INTO Games (map, status) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, game.getMap());
            statement.setString(2, game.getStatus());

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
        game.setStatus(resultSet.getString("status"));
        game.setDate(resultSet.getDate("date"));

        return game;
    }

    // Cette méthode pourrait ne plus être pertinente selon votre nouvelle conception
    public Game getGame(int gameId) throws SQLException, ModelException {
        String query = "SELECT id, map, u.score, status, g.date FROM Games g JOIN user_games u ON g.id=u.game_id WHERE id = ? ORDER BY id";
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
    public boolean updateGame(int id, String status, String map) throws SQLException {
        String query = "UPDATE Games SET status = ?, map = ? WHERE id = ?";
        System.out.println("Update game " + "id" + " to " + status);
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, status);

            statement.setString(2, map);

            statement.setInt(3, id);
            return statement.executeUpdate() > 0;
        }
    }

    public ArrayList<Game> InGoingGames() throws SQLException, ModelException {
        String query = "SELECT id, map, status, date FROM games WHERE status = 'In Progress' Order by id";
        ArrayList<Game> games = new ArrayList<>();
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    games.add(map(resultSet));
                }
            }
        }
        return games;
    }


}
