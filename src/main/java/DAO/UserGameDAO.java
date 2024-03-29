package DAO;

import java.sql.SQLException;
import java.util.List;

public interface UserGameDAO{
    void addUserGame(int userId, int gameId) throws SQLException;
    List<Integer> getGamesByUserId(int userId) throws SQLException;
    List<String> getTopUsersByScore() throws SQLException;
    List<Integer> getUsersByGameId(int gameId) throws SQLException;
    void AddScoreForUser(int userId, int score, int gameId) throws SQLException;
    void deleteUserGame(int userId, int gameId) throws SQLException;
}
