package Servlet;

import DAO.DAOFactory;
import DAO.UserDao;
import Forms.LoginForm;
import Modele.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Connexion", value = "/login")
public class Connexion extends HttpServlet {
    public static final String CONF_DAO_FACTORY = "DAOFactory";
    public static final String ATT_USER_SESSION = "sessionUser";
    public static final String ATT_FORM         = "loginForm";
    public static final String VUE_FORM         = "/WEB-INF/login.jsp";
    public static final String VUE_SUCCESS      = "/WEB-INF/userArea.jsp";

    private UserDao utilisateurDao;

    public void init() throws ServletException {
        this.utilisateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Affichage de la page de connexion */
        this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
        LoginForm form = new LoginForm(utilisateurDao);

        /* Traitement de la requête et récupération du bean en résultant */
        User utilisateur = form.connecterUtilisateur(request);

        /* Stockage du formulaire dans l'objet request */
        request.setAttribute(ATT_FORM, form);

        if (form.getErreurs().isEmpty()) {
            /* Si aucune erreur, stocker les infos utilisateur dans la session */
            HttpSession session = request.getSession();
            session.setAttribute(ATT_USER_SESSION, utilisateur);
            this.getServletContext().getRequestDispatcher(VUE_SUCCESS).forward(request, response);
        } else {
            /* En cas d'erreur, rediriger vers le formulaire de connexion */
            this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
        }
    }
}