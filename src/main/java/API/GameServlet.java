package API;

import DAO.DAOFactory;
import DAO.DAOFactoryPosgres;
import DAO.GameDAO;
import DAO.UserGameDAO;
import Modele.Game;
import Traitement.GameTraitement;
import Traitement.GameUsersTraitement;
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

        String action = requestJson.optString("action", "add");


        try (Connection connection = daoFactory.getConnection()) {
            GameTraitement traitement = new GameTraitement(connection);
            responseJson = traitement.traitement(requestJson);
        } catch (SQLException e) {
            responseJson = new JSONObject();
            responseJson.put("success", false);
            responseJson.put("error", e.getMessage());
            e.printStackTrace();
        }


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseJson.toString());
    }


}
