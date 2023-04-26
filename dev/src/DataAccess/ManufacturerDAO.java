package DataAccess;


import SuppliersModule.Business.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManufacturerDAO {

    private Connection conn;
    static ManufacturerDAO manufacturerDAO;
    private Map<String, Manufacturer> IdentifyMapManufacturer;

    private ManufacturerDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapManufacturer = new HashMap<>();
    }

    public static ManufacturerDAO getInstance(Connection conn) {
        if (manufacturerDAO == null)
            manufacturerDAO = new ManufacturerDAO(conn);
        return manufacturerDAO;
    }

    public void WriteManufacturersToCache() {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Manufacturer");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String manufacturerName = rs.getString("Name");
                IdentifyMapManufacturer.put(manufacturerName, new Manufacturer(manufacturerName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Manufacturer> getAll(String supplierNum) {
        List<Manufacturer> manufacturerList = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Supplier_Manufacturer WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            ResultSet rs = stmt.executeQuery();

            //-----------------------------------------Create array-----------------------------------------
            while (rs.next())
                manufacturerList.add(new Manufacturer(rs.getString("ManufacturerName")));
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        return manufacturerList;
    }
}
