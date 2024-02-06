package Forms;

import DAO.DAOException;
import DAO.UserDao;
import Modele.ModelException;
import Modele.User;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public final class LoginForm {
    private static final String CHAMP_EMAIL = "email";
    private static final String CHAMP_PASS = "motdepasse";

    private String resultat;
    private Map<String, String> erreurs = new HashMap<>();

    private UserDao utilisateurDao;

    public LoginForm(UserDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
    }

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public User connecterUtilisateur(HttpServletRequest request) {
        String email = getValeurChamp(request, CHAMP_EMAIL);
        String motDePasse = getValeurChamp(request, CHAMP_PASS);

        User utilisateur = new User();

        try {
            traiterEmail(email, utilisateur);
            traiterMotDePasse(motDePasse, utilisateur);

            if (erreurs.isEmpty()) {
                utilisateur = utilisateurDao.trouver(email);
                if (utilisateur == null) {
                    setErreur(CHAMP_EMAIL, "Utilisateur inconnu, merci de vérifier votre email.");
                    resultat = "Échec de la connexion.";
                } else {
                    ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
                    passwordEncryptor.setAlgorithm("SHA-256");
                    passwordEncryptor.setPlainDigest(false);

                    if (!passwordEncryptor.checkPassword(motDePasse, utilisateur.getPassword())) {
                        setErreur(CHAMP_PASS, "Mot de passe invalide.");
                        resultat = "Échec de la connexion.";
                    } else {
                        resultat = "Succès de la connexion.";
                    }
                }
            } else {
                resultat = "Échec de la connexion.";
            }
        } catch (DAOException | ModelException e) {
            resultat = "Échec de la connexion : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }

        return utilisateur;
    }

    private void traiterEmail(String email, User utilisateur) throws ModelException {
        try {
            validationEmail(email);
        } catch (FormValidationException e) {
            setErreur(CHAMP_EMAIL, e.getMessage());
        }
        utilisateur.setEmail(email);
    }

    private void traiterMotDePasse(String motDePasse, User utilisateur) throws DAOException {
        if (motDePasse == null) {
            setErreur(CHAMP_PASS, "Merci de saisir votre mot de passe.");
        }
        // Pas de stockage ou modification du mot de passe ici car on vérifie seulement
    }

    private void validationEmail(String email) throws FormValidationException {
        if (email != null) {
            if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
                throw new FormValidationException("Merci de saisir une adresse mail valide.");
            }
        } else {
            throw new FormValidationException("Merci de saisir une adresse mail.");
        }
    }

    private static String getValeurChamp(HttpServletRequest request, String nomChamp) {
        String valeur = request.getParameter(nomChamp);
        if (valeur == null || valeur.trim().length() == 0) {
            return null;
        } else {
            return valeur.trim();
        }
    }

    private void setErreur(String champ, String message) {
        erreurs.put(champ, message);
    }
}
