package Traitement;

import DAO.*;
import Modele.Game;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class GameTraitement {


    private final Connection connection;

    public GameTraitement(Connection connection) {
        this.connection = connection;
    }

    public JSONObject traitement(JSONObject json){
        JSONObject responseJson = new JSONObject();

         try {
             String action = json.optString("action", "add");

            if ("add".equals(action)) {
                responseJson = handleAddGame(json);
            } else if ("update".equals(action)) {
                responseJson = handleUpdateGame(json);
            } else {
                responseJson.put("success", false).put("error", "Invalid action.");
            }
        } catch (SQLException e) {
            responseJson.put("success", false).put("error", e.getMessage());
            e.printStackTrace();
        }
         return responseJson;
    }

    public JSONObject handleAddGame(JSONObject requestJson) throws SQLException {
        JSONObject responseJson = new JSONObject();
        try {
            GameDAO gameDAO = new GameDAOImpl(connection);
            Game g = new Game();
            g.setMap(requestJson.getString("map"));
            g.setStatus(requestJson.getString("status"));
            g.setDate(new Date());

            int gameId = gameDAO.addGame(g);
            UserGameDAO userGameDAO = new UserGameDAOImpl(connection);

            if (requestJson.has("userId")) {
                int userId = requestJson.getInt("userId");
                userGameDAO.addUserGame(userId, gameId);
            }

            responseJson.put("success", true).put("message", "Game added successfully.").put("gameId", gameId);
        } catch (Exception e) {
            responseJson.put("success", false);
            responseJson.put("error", e.getMessage());
            e.printStackTrace();
        }
        return responseJson;
    }

    public JSONObject handleUpdateGame(JSONObject requestJson) throws SQLException {
        JSONObject responseJson = new JSONObject();
        try {
            GameDAO gameDAO = new GameDAOImpl(connection);
            UserGameDAO userGameDAO = new UserGameDAOImpl(connection);

            int gameId = requestJson.getInt("gameId");
            String status = requestJson.getString("status");
            String map = requestJson.getString("map");

            boolean updateResult = gameDAO.updateGame(gameId, status, map);

            JSONArray playersScores = requestJson.getJSONArray("playersScores");
            for (int i = 0; i < playersScores.length(); i++) {
                JSONObject playerScore = playersScores.getJSONObject(i);
                int playerId = playerScore.getInt("id");
                int playerScoreValue = playerScore.getInt("score");
                userGameDAO.AddScoreForUser(playerId, playerScoreValue, gameId);
            }

            if (updateResult) {
                responseJson.put("success", true).put("message", "Game status updated successfully.");
            } else {
                responseJson.put("success", false).put("error", "Failed to update game status.");
            }
        } catch (Exception e) {
            responseJson.put("success", false).put("error", e.getMessage());
            e.printStackTrace();
        }
        return responseJson;
    }


}
