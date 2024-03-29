package Servlet;

import DAO.DAOFactory;
import DAO.DAOFactoryPosgres;
import DAO.UserDao;
import Forms.InscriptionForm;
import Modele.User;

import javax.servlet.annotation.*;
import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Inscription", value = "/register")
public class Inscription extends HttpServlet {

    public static final String CONF_DAO_FACTORY = "DAOFactory";
    public static final String ATT_USER         = "User";
    public static final String ATT_FORM         = "Forms";
    public static final String VUE              = "/WEB-INF/inscription.jsp";

    private UserDao utilisateurDao;

    public void init() throws ServletException {
        try {
            // Log or print a debug message
            System.out.println("Initializing Inscription servlet...");
            this.utilisateurDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getUtilisateurDao();
            // Additional debug message to confirm successful retrieval
            System.out.println("DAOFactory successfully retrieved.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error initializing Inscription servlet", e);
        }
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page d'inscription */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
        InscriptionForm form = new InscriptionForm( utilisateurDao );

        /* Traitement de la requête et récupération du bean en résultant */
        User utilisateur = null;
        utilisateur = form.inscrireUtilisateur( request );

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_USER, utilisateur );

        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}