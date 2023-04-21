package SuppliersModule.DataAccess;

import SuppliersModule.Business.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ContactDAO {

    private Connection conn;
    private Map<String, Contact> IdentifyMapContact;

    public ContactDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapContact = new HashMap<>();
    }

    public void saveContact(Contact contact, String supplierNum) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Contact (SupplierNum, Name, PhoneNumber) VALUES (?,?,?)");
            stmt.setString(1, supplierNum);
            stmt.setString(2, contact.getName());
            stmt.setString(3, contact.getPhoneNumber());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Contact getContactByPhoneNumber(String PhoneNumber) {
        Contact contact = null;
        contact = IdentifyMapContact.get(PhoneNumber);
        if(contact != null)
            return contact;

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Contact WHERE PhoneNumber = ?");
            stmt.setString(1, PhoneNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                contact = new Contact(rs.getString("Name"), rs.getString("PhoneNumber"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        IdentifyMapContact.put(PhoneNumber, contact);
        return contact;
    }

    public void updateContactPhoneNumber(String OldPhone, String NewPhone) {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE Contact SET PhoneNumber = ? WHERE PhoneNumber = ?");
            stmt.setString(1, NewPhone);
            stmt.setString(2, OldPhone);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePhoneNumber(String PhoneNumber) {
        IdentifyMapContact.remove(PhoneNumber);

        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Contact WHERE PhoneNumber = ?");
            stmt.setString(1, PhoneNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
