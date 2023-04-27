package DataAccess;

import SuppliersModule.Business.Contact;
import SuppliersModule.Business.Supplier;

import java.sql.*;
import java.util.*;

public class ContactDAO {

    private Connection conn;
    static ContactDAO contactDAO;

    private Map<String, Contact> IdentifyMapContact;

    private ContactDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapContact = new HashMap<>();
    }

    public static ContactDAO getInstance(Connection conn) {
        if (contactDAO == null)
            contactDAO = new ContactDAO(conn);
        return contactDAO;
    }

    public Map<String, Contact> getAll(String supplierNum)
    {
        Map<String, Contact> contactMap = new HashMap<>();
        //-----------------------------------------Create a query-----------------------------------------
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Contact WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            ResultSet rs = stmt.executeQuery();

            //-----------------------------------------Create array-----------------------------------------
            while (rs.next()) {
                Contact currContact = new Contact(rs.getString("Name"), rs.getString("PhoneNumber"));

                contactMap.put(rs.getString("PhoneNumber"), currContact);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        return contactMap;
    }
}