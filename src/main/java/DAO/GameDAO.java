package DAO;

import Modele.Game;
import Modele.ModelException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface GameDAO {
    int addGame(Game game) throws SQLException, ModelException;
    Game getGame(int gameId) throws SQLException, ModelException;
    boolean updateGame(int id, String status, String map) throws SQLException;
    ArrayList<Game> InGoingGames() throws SQLException, ModelException;
}
