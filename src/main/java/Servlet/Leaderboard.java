package Servlet;

import DAO.DAOFactory;
import DAO.UserGameDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "Leaderboard", value = "/leader")
public class Leaderboard extends HttpServlet {

    private DAOFactory daoFactory;

    public void init() {
        this.daoFactory = (DAOFactory) getServletContext().getAttribute("DAOFactory");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connection = daoFactory.getConnection()) {
            UserGameDAO userGameDAO = new UserGameDAO(connection);
            List<String> topUsers = userGameDAO.getTopUsersByScore();
            request.setAttribute("topUsers", topUsers);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération du leaderboard", e);
        }
        request.getRequestDispatcher("/WEB-INF/leaderboard.jsp").forward(request, response);
    }
}