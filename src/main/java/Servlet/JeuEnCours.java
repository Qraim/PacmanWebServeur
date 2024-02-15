package Servlet;

import DAO.DAOFactory;
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

@WebServlet(name = "JeuEnCours", value = "/ingoing")
public class JeuEnCours extends HttpServlet {

    private DAOFactory daoFactory;

    public void init() {
        this.daoFactory = (DAOFactory) getServletContext().getAttribute("DAOFactory");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connection = daoFactory.getConnection()) {
            GameDAO gameDAO = new GameDAO(connection);

            List<Game> games = gameDAO.InGoingGames();
            request.setAttribute("games", games);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération des jeux", e);
        }

        request.getRequestDispatcher("/WEB-INF/jeuencours.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        try (Connection connection = daoFactory.getConnection()) {
            GameDAO gameDAO = new GameDAO(connection);

            List<Game> games = gameDAO.InGoingGames();
            request.setAttribute("games", games);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération des jeux", e);
        }

        request.getRequestDispatcher("/WEB-INF/jeuencours.jsp").forward(request, response);
    }
}