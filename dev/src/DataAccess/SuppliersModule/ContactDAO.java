package DataAccess.SuppliersModule;

import SuppliersModule.Business.Contact;

import java.sql.*;
import java.util.*;

/**
 * Data access object class of Contact.
 */
public class ContactDAO {

    //------------------------------------------ Attributes ---------------------------------------
    private Connection conn;
    static ContactDAO contactDAO;
    private Map<String, Contact> IdentifyMapContact;

    // -----------------------------------------------------------------------------------------------------

    /**
     * Singleton constructor
     */
    private ContactDAO(Connection conn) {
        this.conn = conn;
        IdentifyMapContact = new HashMap<>();
    }

    /**
     * Get instance
     * @param conn - Object connection to DB
     * @return - ContactDAO
     */
    public static ContactDAO getInstance(Connection conn) {
        if (contactDAO == null)
            contactDAO = new ContactDAO(conn);
        return contactDAO;
    }

    /**
     * Get all contacts that belongs to the supplier
     * @param supplierNum - number of the supplier.
     * @return desired contacts.
     */
    public Map<String, Contact> getAllBySupplierNum(String supplierNum)
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
                IdentifyMapContact.put(rs.getString("PhoneNumber"), currContact);
            }
        }
        catch (SQLException e) {throw new RuntimeException(e);}

        return contactMap;
    }

    /**
     * Write contacts from cache to DB
     * @param supplierNum - number of the supplier.
     * @param contactMap - contacts that belongs to the supplier
     */
    public void WriteFromCacheToDB(String supplierNum, Map<String,Contact> contactMap) {
        PreparedStatement stmt;
        for (Map.Entry<String, Contact> pair : contactMap.entrySet()) {
            try {
                stmt = conn.prepareStatement("Insert into Contact VALUES (?,?,?)");
                stmt.setString(1, supplierNum);
                stmt.setString(2, pair.getValue().getName());
                stmt.setString(3, pair.getValue().getPhoneNumber());
                stmt.executeUpdate();
            }
            catch (SQLException e) {throw new RuntimeException(e);}
        }
    }

    /**
     * Delete all the records in the table
     */
    public void deleteAllTable()
    {
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement("DELETE FROM Contact");
            stmt.executeUpdate();
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    /**
     * Delete All contacts from DB that belongs to the supplier.
     * @param supplierNum - supplierNum.
     */
    public void deleteBySupplier(String supplierNum)
    {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Contact WHERE SupplierNum = ?");
            stmt.setString(1, supplierNum);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
                IdentifyMapContact.remove(rs.getString("SupplierNum"));
        }
        catch (SQLException e) {throw new RuntimeException(e);}
    }

    /**
     * Insert contact to DB
     * @param contact - desired contact.
     */
    public void insert(Contact contact)
    {
        IdentifyMapContact.put(contact.getPhoneNumber(), contact);
    }

    /**
     * Get contact by phoneNumber
     * @param phoneNumber - number of contact.
     * @return desired contact.
     */
    public Contact getByPhoneNumber(String phoneNumber)
    {
        return IdentifyMapContact.get(phoneNumber);
    }

    /**
     * Delete contact from DB
     * @param phoneNumber - number of contact.
     */
    public void delete(String phoneNumber)
    {
        IdentifyMapContact.remove(phoneNumber);
    }
}