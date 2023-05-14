package DataAccessLayer.Transport;

import BussinessLayer.HRModule.Objects.Store;
import BussinessLayer.TransportationModule.objects.Site_Supply;
import BussinessLayer.TransportationModule.objects.Supplier;
import BussinessLayer.TransportationModule.objects.Truck;
import DataAccessLayer.DAO;
import DataAccessLayer.HRMoudle.StoresDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public class Suppliers_dao extends DAO {
    private HashMap<String, Supplier> Suppliers_map;

    private static Suppliers_dao suppliersDao = null;

    public static Suppliers_dao getInstance() {
        if (suppliersDao == null)
            suppliersDao = new Suppliers_dao();
        return suppliersDao;
    }

    private Suppliers_dao() {
        super("Suppliers");
        Suppliers_map = new HashMap<>();
    }

    public boolean Insert(Object obj) {
        Supplier supplier = (Supplier) obj;
        try {
            String query = "INSERT INTO Suppliers (Address, Phone, Name, Contact_name) VALUES (?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, supplier.getAddress());
            statement.setString(2, supplier.getPhone());
            statement.setString(3, supplier.getSite_name());
            statement.setString(4, supplier.getSite_contact_name());
            statement.execute();
            Suppliers_map.put(supplier.getSupplier_name(), supplier);
            return true;
        } catch (SQLException e) {
            System.out.println("Exception thrown");
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean Delete(Object obj) {
        Supplier supplier = (Supplier) obj;
        String query = "DELETE FROM " + _tableName + " WHERE Name = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, supplier.getSite_name());
            statement.executeUpdate();

            Suppliers_map.remove(supplier.getSupplier_name());
            return true;

        } catch (SQLException e) {
            System.out.println("Exception thrown");
        }
        return false;
    }

    @Override
    public Supplier convertReaderToObject(ResultSet res) throws SQLException, ParseException {
        if (Suppliers_map.containsKey(res.getString(3))) {
            return Suppliers_map.get(res.getString(3));
        }
        Supplier supplier = new Supplier(res.getString(1), res.getString(2), res.getString(3), res.getString(4));
        return supplier;
    }

    public boolean is_any_supplier_exist() {
        if (Suppliers_map.size() > 0) {
            return true;
        } else {
            String query = "SELECT * FROM " + this._tableName;
            try (PreparedStatement pstmt = connection.prepareStatement(query);) {
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return true;
                }
                return false;
            } catch (SQLException ex) {
            }
            return false;
        }
    }

    public boolean is_supplier_exist(String supplier_name) {
        String query = "SELECT * FROM " + _tableName + " WHERE Name = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, supplier_name);
            ResultSet rs = statement.executeQuery();
            if (rs.getString(3) != null && rs.getString(3).equals(supplier_name)) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.out.println("Exception thrown");
        }
        return false;
    }

    public Supplier get_supplier_by_name(String supplier_name) {
        if (Suppliers_map.containsKey(supplier_name)) {
            return Suppliers_map.get(supplier_name);
        }
        String query = "SELECT * FROM " + this._tableName + " WHERE Name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query);) {
            pstmt.setString(1, supplier_name);
            ResultSet rs = pstmt.executeQuery();
            Supplier supplier = convertReaderToObject(rs);
            return supplier;
        } catch (SQLException ex) {
            System.out.println("We don't have these truck in the Database.");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Supplier> SelectAllSuppliers() {
        List<Supplier> suppliers = select();
        for (Supplier supplier : suppliers) {
            if (!Suppliers_map.containsKey(supplier.getSite_name()))
                Suppliers_map.put(supplier.getSite_name(), supplier);
        }
        return suppliers;
    }
}

