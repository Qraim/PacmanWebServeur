package API;

import DAO.DAOFactory;
import DAO.UserDao;
import Forms.LoginForm;
import Modele.User;
import org.json.JSONObject;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {

    public static final String CONF_DAO_FACTORY = "DAOFactory";

    UserDao utilisateurDao;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JSONObject requestJson = new JSONObject(requestBody);
        this.utilisateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();

        LoginForm form = new LoginForm(utilisateurDao);
        User utilisateur = form.connecterUtilisateur(requestJson);

        JSONObject responseJson = new JSONObject();
        if (form.getErreurs().isEmpty()) {
            responseJson.put("status", "success");
            responseJson.put("message", "Connexion réussie");
        } else {
            responseJson.put("status", "failure");
            responseJson.put("message", form.getErreurs().values().toString()); // Améliorer pour une meilleure représentation des erreurs
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(responseJson.toString());
        }
    }

}
