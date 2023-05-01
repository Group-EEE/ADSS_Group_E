package DataAccess.SuppliersModule;


import SuppliersModule.Business.*;

import java.sql.*;
import java.util.*;

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
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    public List<Manufacturer> getAll(String supplierNum) {
        List<Manufacturer> manufacturerList = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Supplier_Manufacturer WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            ResultSet rs = stmt.executeQuery();

            //-----------------------------------------Create array-----------------------------------------
            Manufacturer currManufacturer;
            while (rs.next()) {
                currManufacturer = IdentifyMapManufacturer.get(rs.getString("ManufacturerName"));
                manufacturerList.add(currManufacturer);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        return manufacturerList;
    }

    public void WriteFromCacheToDB()
    {
        PreparedStatement stmt;

        try{
            stmt = conn.prepareStatement("DELETE FROM Manufacturer");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        for (Map.Entry<String, Manufacturer> pair : IdentifyMapManufacturer.entrySet()) {
            try{
                stmt = conn.prepareStatement("Insert into Manufacturer VALUES (?)");
                stmt.setString(1, pair.getKey());
                stmt.executeUpdate();
            }
            catch (SQLException e) {throw new RuntimeException(e);}
        }

        writeAllToSupplierManufacturer();
    }

    public void writeAllToSupplierManufacturer()
    {
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("DELETE FROM Supplier_Manufacturer");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        for (Map.Entry<String, Manufacturer> pair : IdentifyMapManufacturer.entrySet()) {
            for (Map.Entry<String, Supplier> pair2 : pair.getValue().getMySuppliers().entrySet()) {
                try {
                    stmt = conn.prepareStatement("Insert into Supplier_Manufacturer VALUES (?,?)");
                    stmt.setString(1, pair2.getKey());
                    stmt.setString(2, pair.getKey());
                    stmt.executeUpdate();
                }
                catch (SQLException e) {throw new RuntimeException(e);}
            }
        }
    }

    public boolean CheckIfManufacturerExist(String manufacturerName)
    {
        return IdentifyMapManufacturer.containsKey(manufacturerName);
    }

    public Manufacturer getManufacturer(String manufacturerName)
    {
        return IdentifyMapManufacturer.get(manufacturerName);
    }

    public void insert(Manufacturer manufacturer)
    {
        IdentifyMapManufacturer.put(manufacturer.getName(), manufacturer);
    }


    public void deleteBySupplier(String supplierNum)
    {
        try {
            PreparedStatement stmt = conn.prepareStatement("Delete FROM Supplier_Manufacturer WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    public void deleteWorkingWithManufacturer(String supplierNum, String manufacturerNum)
    {
        try {
            PreparedStatement stmt = conn.prepareStatement("Delete FROM Supplier_Manufacturer WHERE SupplierNum = ? AND manufacturerNum = ?");
            stmt.setString(1, supplierNum);
            stmt.setString(2, manufacturerNum);
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }
}
