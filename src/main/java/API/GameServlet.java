package API;

import DAO.DAOFactoryPosgres;
import DAO.GameDAO;
import DAO.UserGameDAO;
import Modele.Game;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.stream.Collectors;

@WebServlet("/api/game")
public class GameServlet extends HttpServlet {

    private DAOFactoryPosgres daoFactory;

    @Override
    public void init() {
        this.daoFactory = (DAOFactoryPosgres) getServletContext().getAttribute("DAOFactory");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JSONObject requestJson = new JSONObject(requestBody);
        JSONObject responseJson = new JSONObject();

        String action = requestJson.optString("action", "add");

        try (Connection connection = daoFactory.getConnection()) {
            GameDAO gameDAO = new GameDAO(connection);

            if ("add".equals(action)) {
                Game g = new Game();
                g.setMap(requestJson.getString("map"));
                g.setStatus(requestJson.getString("status"));
                g.setDate(Date.from(new Date().toInstant()));

                int gameId = gameDAO.addGame(g);
                UserGameDAO userGameDAO = new UserGameDAO(connection);

                if (requestJson.has("userId")) {
                    int userId = requestJson.getInt("userId");
                    userGameDAO.addUserGame(userId, gameId);
                }
                responseJson.put("success", true);
                responseJson.put("message", "Game added successfully.");
                responseJson.put("gameId", gameId);
            } else if ("update".equals(action)) {
                int gameId = requestJson.getInt("gameId");
                String status = requestJson.getString("status");

                JSONArray playersScores = requestJson.getJSONArray("playersScores");
                UserGameDAO userGameDAO = new UserGameDAO(connection);
                boolean updateResult = gameDAO.updateGame(gameId, status);

                for (int i = 0; i < playersScores.length(); i++) {
                    JSONObject playerScore = playersScores.getJSONObject(i);
                    int playerId = playerScore.getInt("id");
                    int playerScoreValue = playerScore.getInt("score");
                    userGameDAO.AddScoreForUser(playerId, playerScoreValue, gameId);
                }

                if (updateResult) {
                    responseJson.put("success", true);
                    responseJson.put("message", "Game status updated successfully.");
                } else {
                    responseJson.put("success", false);
                    responseJson.put("error", "Failed to update game status.");
                }
            } else {
                responseJson.put("success", false);
                responseJson.put("error", "Invalid action.");
            }
        } catch (SQLException e) {
            responseJson.put("success", false);
            responseJson.put("error", e.getMessage());
            e.printStackTrace();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseJson.toString());
    }


}
