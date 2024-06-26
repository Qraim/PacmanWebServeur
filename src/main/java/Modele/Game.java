package Modele;

import java.util.ArrayList;
import java.util.Date;

public class Game implements Comparable<Game>{
    private int id;
    private User joueur;
    private String map;
    private int score;
    private String status;
    private ArrayList<User> joueurs;
    private Date date;

    public Game(String map, int score, String status) {
        this.map = map;
        this.score = score;
        this.status = status;
    }

    public Game() {
        super();
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

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public User getJoueur() {
        return joueur;
    }

    public void setJoueur(User joueur) {
        this.joueur = joueur;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public ArrayList<User> getJoueurs() {
        return joueurs;
    }

    public void addJoueurs(User joueur) {
        this.joueurs.add(joueur);
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", joueur=" + joueur +
                ", map='" + map + '\'' +
                ", score=" + score +
                ", status='" + status + '\'' +
                ", joueurs=" + joueurs +
                '}';
    }

    @Override
    public int compareTo(Game game) {
        return ((Integer)(getScore()*(-1))).compareTo((Integer)(game.getScore()*(-1)));
    }
}