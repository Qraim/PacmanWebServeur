package API;

import DAO.DAOFactory;
import DAO.DAOFactoryPosgres;
import DAO.UserGameDAO;
import Traitement.GameUsersTraitement;
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

    private DAOFactory daoFactory;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void init() {
        this.daoFactory = (DAOFactory) getServletContext().getAttribute("DAOFactory");
    }

    // Dans la méthode doPost de GameServlet
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JSONObject requestJson = new JSONObject(requestBody);
        JSONObject responseJson = new JSONObject();

        try (Connection connection = daoFactory.getConnection()) {
            GameUsersTraitement traitement = new GameUsersTraitement(connection);
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