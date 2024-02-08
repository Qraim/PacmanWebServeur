package API;

import DAO.DAOFactory;
import DAO.GameDAO;
import DAO.UserDao;
import Forms.LoginForm;
import Modele.Game;
import Modele.User;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebServlet("/api/game")
public class GameServlet extends HttpServlet {

    private DAOFactory daoFactory;

    public void init() {
        this.daoFactory = (DAOFactory) getServletContext().getAttribute("DAOFactory");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JSONObject requestJson = new JSONObject(requestBody);

        try (Connection connection = daoFactory.getConnection()) {
            GameDAO gameDAO = new GameDAO(connection);
            switch(requestJson.getString("action")) {
                case "add":
                    Game g = new Game();
                    g.setMap(requestJson.getString("map"));
                    g.setScore(requestJson.getInt("score"));
                    g.setStatus(requestJson.getString("status"));
                    gameDAO.addGame(g, requestJson.getInt("userId"));
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        JSONObject responseJson = new JSONObject();


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}