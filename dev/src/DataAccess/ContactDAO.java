package DataAccess;

import SuppliersModule.Business.Contact;

import java.sql.*;
import java.util.*;

public class ContactDAO {

    private Connection conn;
    static ContactDAO contactDAO;

    private ContactDAO(Connection conn) {
        this.conn = conn;
    }

    public static ContactDAO getInstance(Connection conn) {
        if (contactDAO == null)
            contactDAO = new ContactDAO(conn);
        return contactDAO;
    }

    public void saveContact(Contact contact, String supplierNum) {
        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Contact (SupplierNum, Name, PhoneNumber) VALUES (?,?,?)");
            stmt.setString(1, supplierNum);
            stmt.setString(2, contact.getName());
            stmt.setString(3, contact.getPhoneNumber());
            stmt.executeUpdate();
        }
        catch (SQLException e) {e.printStackTrace();}
    }

    public Contact getContact(String PhoneNumber) {
        Contact contact = null;
        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Contact WHERE PhoneNumber = ?");
            stmt.setString(1, PhoneNumber);
            ResultSet rs = stmt.executeQuery();

            //-------------------------------------Create OrderDiscount---------------------------------
            if (rs.next()) {
                contact = new Contact(rs.getString("Name"), rs.getString("PhoneNumber"));
            }
        }
        catch (SQLException e) {e.printStackTrace();}

        return contact;
    }

    public void updateContact(String OldPhone, String NewPhone) {
        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE Contact SET PhoneNumber = ? WHERE PhoneNumber = ?");
            stmt.setString(1, NewPhone);
            stmt.setString(2, OldPhone);
            stmt.executeUpdate();
        }
        catch (SQLException e) {e.printStackTrace();}
    }

    public void deletePhoneNumber(String PhoneNumber) {
        //-----------------------------------------Create a query-----------------------------------------
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Contact WHERE PhoneNumber = ?");
            stmt.setString(1, PhoneNumber);
            stmt.executeUpdate();
        }
        catch (SQLException e) {e.printStackTrace();}
    }

    public Map<String, Contact> getAll(String supplierNum)
    {
        Map<String, Contact> contactMap = new HashMap<>();
        //-----------------------------------------Create a query-----------------------------------------
        ResultSet rs;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Contact WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            rs = stmt.executeQuery();

            //-----------------------------------------Create array-----------------------------------------
            while (rs.next())
                contactMap.put(rs.getString("PhoneNumber"), new Contact(rs.getString("Name"), rs.getString("PhoneNumber")));
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        return contactMap;
    }
}