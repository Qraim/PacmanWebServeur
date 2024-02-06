package DAO;

import Modele.User;

public interface UserDao {

    void creer( User utilisateur ) throws DAOException;

    User trouver( String email ) throws DAOException;

}