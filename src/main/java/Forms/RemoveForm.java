package Forms;

import DAO.DAOException;
import DAO.UserDao;
import Modele.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public final class RemoveForm {
    private static final String CHAMP_ID  = "id";
    private String              resultat;
    private Map<String, String> erreurs   = new HashMap<>();

    private UserDao utilisateurDao;

    public RemoveForm( UserDao utilisateurDao ) {
        this.utilisateurDao = utilisateurDao;
    }

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public void supprimerUtilisateur(HttpServletRequest request) {
        String idString = getValeurChamp(request, CHAMP_ID);
        int id = -1;
        try {
            // Validation de l'ID
            id = validationId(idString);

            // Suppression de l'utilisateur
            utilisateurDao.supprimer(id);
            resultat = "Succès de la suppression.";
        } catch (DAOException e) {
            setErreur(CHAMP_ID, "Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
            resultat = "Échec de la suppression de l'utilisateur.";
        } catch (Exception e) {
            setErreur(CHAMP_ID, e.getMessage());
            resultat = "Échec de la suppression de l'utilisateur.";
        }
    }

    private int validationId(String idString) throws Exception {
        if (idString != null) {
            try {
                return Integer.parseInt(idString);
            } catch (NumberFormatException e) {
                throw new Exception("L'identifiant utilisateur est invalide.");
            }
        } else {
            throw new Exception("Merci de fournir un identifiant utilisateur.");
        }
    }

    private void setErreur(String champ, String message) {
        erreurs.put(champ, message);
    }

    private static String getValeurChamp(HttpServletRequest request, String nomChamp) {
        String valeur = request.getParameter(nomChamp);
        if (valeur == null || valeur.trim().length() == 0) {
            return null;
        } else {
            return valeur.trim();
        }
    }
}
