package DAO;

import Modele.Game;

import java.sql.*;
import java.util.ArrayList;

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

    // Ajouter d'autres méthodes CRUD ici...
}
