package DataAccessLayer.Transport;

import BussinessLayer.TransportationModule.objects.Site_Supply;
import BussinessLayer.TransportationModule.objects.Supplier;
import DataAccessLayer.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;

public class Suppliers_dao extends DAO {
    private HashMap<String, Supplier> Suppliers_map;
    public Suppliers_dao(){
        super("Suppliers");
        Suppliers_map = new HashMap<>();
    }
    @Override
    public boolean Insert(Object obj) {
        Supplier supplier = (Supplier) obj;
        try {
            String query = "INSERT INTO Suppliers (ID, Store_Name, Origin, Total_Weight) VALUES (?,?,?,?)";

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

    @Override
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
    public Object convertReaderToObject(ResultSet res) throws SQLException, ParseException {
        if(Suppliers_map.containsKey(res.getString(3))){
            return Suppliers_map.get(res.getString(3));
        }
        Supplier supplier = new Supplier(res.getString(1), res.getString(2), res.getString(3), res.getString(4) );
        return res;
    }

    public boolean is_any_supplier_exist(){
        if (Suppliers_map.size() > 0){
            return true;
        }
        else {
            String query = "SELECT * FROM " + this._tableName;
            try (PreparedStatement pstmt = connection.prepareStatement(query);) {
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()){
                    return true;
                }
                return false;
            } catch (SQLException ex) {
            }
            return false;
        }
    }
}
