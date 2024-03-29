package Traitement;

import DAO.GameDAO;
import DAO.GameDAOImpl;
import DAO.UserGameDAO;
import DAO.UserGameDAOImpl;
import Modele.Game;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.util.Date;

public class GameUsersTraitement {

    private final Connection connection;

    public GameUsersTraitement(Connection connection) {
        this.connection = connection;
    }

    public JSONObject traitement(JSONObject json) {
        JSONObject responseJson = new JSONObject();
        try {
            String action = json.optString("action", "add");
            responseJson = LinkClientGames(json);
        } catch (Exception e) {
            responseJson.put("success", false);
            responseJson.put("error", e.getMessage());
            e.printStackTrace();
        }
        return responseJson;
    }
    public JSONObject LinkClientGames(JSONObject requestJson) {
        JSONObject responseJson = new JSONObject();
        try {

            int userId;
            int gameId;

            UserGameDAO userGameDAO = new UserGameDAOImpl(connection);

            if (requestJson.has("userId")) {
                userId = requestJson.getInt("userId");
            } else {
                throw new IllegalArgumentException("userId is required.");
            }

            if (requestJson.has("gameId")) {
                gameId = requestJson.getInt("gameId");
            } else {
                throw new IllegalArgumentException("gameId is required.");
            }

            userGameDAO.addUserGame(userId, gameId);

            responseJson.put("success", true);
            responseJson.put("gameId", gameId);
        } catch (Exception e) {
            responseJson.put("success", false);
            responseJson.put("error", e.getMessage());
            e.printStackTrace();
        }
        return responseJson;
    }


}
