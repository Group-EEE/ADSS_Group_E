package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SuperLeeDBConnection {

    private static SuperLeeDBConnection superLeeDBConnection;
    private Connection conn;
    private SupplierDAO supplierDAO;
    private ManufacturerDAO manufacturerDAO;
    private GenericProductDAO genericProductDAO;

    private SuperLeeDBConnection() {
        try {conn = DriverManager.getConnection("jdbc:sqlite:dev/res/SuperLeeDB");}
        catch (SQLException e) {e.printStackTrace();}

        supplierDAO = SupplierDAO.getInstance(conn);
        manufacturerDAO = ManufacturerDAO.getInstance(conn);
        genericProductDAO = GenericProductDAO.getInstance(conn);
    }

    public static SuperLeeDBConnection getInstance() {
        if (superLeeDBConnection == null)
            superLeeDBConnection = new SuperLeeDBConnection();

        return superLeeDBConnection;
    }

    public Connection getConnection() {
        return conn;
    }

    public void ReadAllToCache()
    {
        manufacturerDAO.WriteManufacturersToCache();
        genericProductDAO.WriteGenericProductsToCache();
        supplierDAO.WriteSuppliersToCache();
    }

    public void WriteAllToDB()
    {
        manufacturerDAO.WriteFromCacheToDB();
        genericProductDAO.WriteFromCacheToDB();
        supplierDAO.WriteFromCacheToDB();
    }
}
