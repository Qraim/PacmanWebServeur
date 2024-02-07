package Servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Deconnexion", value = "/logout")
public class Deconnexion extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public static final String URL_REDIRECTION = "/index.jsp"; // ou une autre URL selon vos besoins

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();

        response.sendRedirect(request.getContextPath() + URL_REDIRECTION);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // La méthode POST peut simplement rediriger vers la méthode GET si nécessaire
        doGet(request, response);
    }
}