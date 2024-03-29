package Traitement;

import DAO.DAOFactory;
import DAO.DAOFactoryPosgres;
import DAO.UserDaoImpl;
import Forms.LoginForm;
import Modele.User;
import org.json.JSONObject;

public class LoginTraitement {

    private final DAOFactory daoFactory;

    public LoginTraitement(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public JSONObject connecterUtilisateur(JSONObject requestJson) {

        JSONObject responseJson = new JSONObject();
        try {
            UserDaoImpl userDao = new UserDaoImpl(daoFactory);
            LoginForm form = new LoginForm(userDao);
            User user = form.connecterUtilisateur(requestJson);
            responseJson.put("success", true);
            responseJson.put("user", userToJson(user));
        } catch (Exception e) {
            responseJson.put("success", false);
            responseJson.put("error", e.getMessage());
            e.printStackTrace();
        }
        return responseJson;
    }

    private JSONObject userToJson(User user) {
        return new JSONObject()
                .put("id", user.getId())
                .put("name", user.getName());
    }
}
