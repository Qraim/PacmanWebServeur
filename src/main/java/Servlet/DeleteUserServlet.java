package Servlet;

import DAO.DAOFactory;
import DAO.UserDao;
import Forms.RemoveForm;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteUserServlet", urlPatterns = {"/deleteUser"})
public class DeleteUserServlet extends HttpServlet {

    private UserDao utilisateurDao;

    public static final String CONF_DAO_FACTORY = "DAOFactory";
    public static final String ATT_USER         = "User";
    public static final String ATT_FORM         = "Forms";
    public static final String VUE              = "/WEB-INF/inscription.jsp";

    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.utilisateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
        RemoveForm removeForm = null;
        removeForm = new RemoveForm(utilisateurDao);

        /* Traitement de la requête et récupération du bean en résultant */
        removeForm.supprimerUtilisateur(request);

        /* Stockage du résultat et des messages d'erreur dans l'objet request */
        request.setAttribute("form", removeForm);

        /* Transmission à la page JSP en charge de l'affichage des résultats */
        if (removeForm.getErreurs().isEmpty()) {
            request.setAttribute("successMessage", "Utilisateur supprimé avec succès.");
            request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Erreur lors de la suppression de l'utilisateur.");
            request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response); // Affiche la page d'erreur
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirection vers le formulaire de suppression (si vous en avez un) ou simplement rediriger vers doPost
        doPost(request, response);
    }
}
