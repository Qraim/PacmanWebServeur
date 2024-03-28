package Servlet;

import DAO.DAOFactoryPosgres;
import DAO.GameDAO;
import DAO.UserGameDAO;
import Modele.Game;
import Modele.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Historique", value = "/games")
public class Historique extends HttpServlet {
    private DAOFactoryPosgres daoFactory;

    public void init() {
        this.daoFactory = (DAOFactoryPosgres) getServletContext().getAttribute("DAOFactory");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("sessionUser");

        // Vérifie si l'utilisateur est connecté
        if (user == null) {
            // Si non connecté, redirige vers la page de login
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Récupère les parties de jeu de l'utilisateur connecté
        try (Connection connection = daoFactory.getConnection()) {
            UserGameDAO UsergameDAO = new UserGameDAO(connection);
            GameDAO gameDAO = new GameDAO(connection);

            List<Integer> gamesId = UsergameDAO.getGamesByUserId(user.getId());

            List<Game> games = new ArrayList<>();
            for(int gameId : gamesId) {
                Game game = gameDAO.getGame(gameId);
                games.add(game);
            }
            request.setAttribute("games", games);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération des jeux", e);
        }

        // Transfère la requête vers la page JSP
        request.getRequestDispatcher("/WEB-INF/historique.jsp").forward(request, response);
    }
}