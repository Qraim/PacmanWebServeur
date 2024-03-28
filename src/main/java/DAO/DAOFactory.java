package DAO;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAOFactory {

    public static final int CLOUDSCAPE = 1;
    public static final int ORACLE = 2;
    public static final int SYBASE = 3;
    public static final int POSTGRESQL = 4;

    // Ajoutez d'autres types de base de données si nécessaire.

    // Méthode pour obtenir une connexion à la base de données
    public abstract Connection getConnection() throws SQLException;

    // Factory method pour UserDao
    public abstract UserDao getUtilisateurDao();

    // Méthode pour récupérer l'instance de la fabrique spécifique
    public static DAOFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case CLOUDSCAPE:
                // Retourner l'instance de CloudscapeDAOFactory
                break;
            case ORACLE:
                // Retourner l'instance de OracleDAOFactory
                break;
            case SYBASE:
                // Retourner l'instance de SybaseDAOFactory
                break;
            case POSTGRESQL:
                // Retourner l'instance de DAOFactoryPostGres
                return DAOFactoryPosgres.getInstance();
            default:
                return null;
        }
        return null;
    }
}
