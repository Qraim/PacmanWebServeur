package API;

import DAO.DAOFactory;
import DAO.GameDAO;
import DAO.UserGameDAO;
import Modele.Game;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet("/api/game")
public class GameServlet extends HttpServlet {

    private DAOFactory daoFactory;

    @Override
    public void init() {
        this.daoFactory = (DAOFactory) getServletContext().getAttribute("DAOFactory");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JSONObject requestJson = new JSONObject(requestBody);
        JSONObject responseJson = new JSONObject();

        String action = requestJson.optString("action", "add"); // "add" par défaut

        try (Connection connection = daoFactory.getConnection()) {
            GameDAO gameDAO = new GameDAO(connection);

            if ("add".equals(action)) {
                Game g = new Game();
                g.setMap(requestJson.getString("map"));
                g.setScore(requestJson.getInt("score"));
                g.setStatus(requestJson.getString("status"));

                int gameId = gameDAO.addGame(g);

                if (requestJson.has("userId")) {
                    UserGameDAO userGameDAO = new UserGameDAO(connection);
                    int userId = requestJson.getInt("userId");
                    userGameDAO.addUserGame(userId, gameId);
                }

                responseJson.put("success", true);
                responseJson.put("message", "Game added successfully.");
                responseJson.put("gameId", gameId);
            } else if ("update".equals(action)) {
                int gameId = requestJson.getInt("gameId");
                String status = requestJson.getString("status");
                int score = requestJson.getInt("score");

                boolean updateResult = gameDAO.updateGame(gameId,score, status);

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
