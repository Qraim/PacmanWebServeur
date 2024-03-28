package API;

import DAO.DAOFactoryPosgres;
import DAO.UserGameDAO;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet("/api/gameusers")
public class GameUsersServlet extends HttpServlet {

    private DAOFactoryPosgres daoFactory;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void init() {
        this.daoFactory = (DAOFactoryPosgres) getServletContext().getAttribute("DAOFactory");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JSONObject requestJson = new JSONObject(requestBody);
        JSONObject responseJson = new JSONObject();

        try (Connection connection = daoFactory.getConnection()) {
            UserGameDAO userGameDAO = new UserGameDAO(connection);

            int userId;
            int gameId;

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
            responseJson.put("message", "Users and Games are now linked.");

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