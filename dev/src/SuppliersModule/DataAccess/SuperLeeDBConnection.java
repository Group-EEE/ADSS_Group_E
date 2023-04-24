package SuppliersModule.DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SuperLeeDBConnection {

    private static SuperLeeDBConnection superLeeDBConnection;
    private Connection conn;

    private SupplierDAO supplierDAO;

    private SuperLeeDBConnection() {
        try {conn = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDB");}
        catch (SQLException e) {e.printStackTrace();}

        supplierDAO = SupplierDAO.getInstance(conn);
    }

    public static SuperLeeDBConnection getInstance() {
        if (superLeeDBConnection == null)
            superLeeDBConnection = new SuperLeeDBConnection();

        return superLeeDBConnection;
    }

    public Connection getConnection() {
        return conn;
    }

    public void WriteAllToCache()
    {
        supplierDAO.WriteSuppliersToCache();
    }
}
