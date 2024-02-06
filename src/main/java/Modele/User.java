package Modele;

import java.util.ArrayList;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private ArrayList<Game> games;

    public User(String name, String email, String password) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(int id, String name, String email, String password) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) throws ModelException {
        if(id <= 0) {
            throw new ModelException("L'id ne peut pas être négatif ou nul");
        }else {
            this.id = id;
        }
    }

    public String getName() {
        return name;
    }
    public void setName(String name) throws ModelException {
        //if(((UserDAO)DAOFactory.getInstance().getUserDAO()).getPseudos().contains(name)) {
        //throw new ModelException("Le nom d'utilisateur est déjà utilisé, veuillez trouver un autre nom");
        //} else {
        this.name = name;
        //}
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws ModelException {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws ModelException {
        if(password.length() < 8) {
            throw new ModelException("Le mot de passe doit faire 8 caractères au minimum");
        } else {
            this.password = password;
        }
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) throws ModelException {
        this.games = games;
    }


    @Override
    public String toString() {
        return "User [name=" + name + ", email=" + email + ", password=" + password + ", games=" + games
                + "]";
    }

}