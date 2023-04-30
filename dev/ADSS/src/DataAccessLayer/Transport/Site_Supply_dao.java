package DataAccessLayer.Transport;

import BussinessLayer.TransportationModule.objects.Site_Supply;
import BussinessLayer.TransportationModule.objects.Store;
import BussinessLayer.TransportationModule.objects.Transport;
import BussinessLayer.TransportationModule.objects.cold_level;
import DataAccessLayer.DAO;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class Site_Supply_dao extends DAO {

    HashMap<Integer, Site_Supply> site_supply_documents;
    public Site_Supply_dao(String tableName) {
        super(tableName);
        site_supply_documents = new HashMap<>();
        get_site_supply_documents_from_db();
    }

    @Override
    public boolean Insert(Object obj) {
        Site_Supply site_supply = (Site_Supply) obj;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
            String query = "INSERT INTO " + _tableName + " (ID, Store_Name, Origin, Total_Weight) VALUES (?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, site_supply.getId());
            statement.setString(2, site_supply.getStore().getSite_name());
            statement.setString(3, site_supply.getOrigin());
            statement.setDouble(4, site_supply.getProducts_total_weight());
            statement.execute();

            site_supply_documents.put(site_supply.getId(), site_supply);
            return true;
        } catch (SQLException e) {
            System.out.println("Exception thrown");
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println("Exception thrown");
                System.out.println(ex.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean Delete(Object obj) {
        Site_Supply site_supply = (Site_Supply) obj;
        String query = "DELETE FROM " + _tableName + " WHERE Site_Supply_ID = ?";
        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, site_supply.getId());
            statement.executeUpdate();

            site_supply_documents.remove(site_supply.getId());
            return true;

        } catch (SQLException e) {
            System.out.println("Exception thrown");
        }
        return false;
    }

    @Override
    public Object convertReaderToObject(ResultSet res) throws SQLException, ParseException {
        if(site_supply_documents.containsKey(res.getInt(1))){
            return site_supply_documents.get(res.getInt(1));
        }
        String store_name = res.getString(2);
        Store store = null;
        if (store != null) {
            Site_Supply site_supply = new Site_Supply(res.getInt(1), store , res.getString(3));
            site_supply_documents.put(site_supply.getId(), site_supply);
            return site_supply;
        }
        return null;
    }

    public boolean check_if_site_supply_exists(int id){
        if(site_supply_documents.containsKey(id)){
            return true;
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
            String query = "SELECT * FROM " + _tableName + " WHERE Site_Supply_ID = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            if(res.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public HashMap<Integer, Site_Supply> getSite_supply_documents_map() {
        return site_supply_documents;
    }

    public ArrayList<Site_Supply> get_site_supply_documents(){
        return new ArrayList<Site_Supply>(site_supply_documents.values());
    }

    private void get_site_supply_documents_from_db(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
            String query = "SELECT * FROM " + _tableName;

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                Site_Supply site_supply = (Site_Supply) convertReaderToObject(res);
                if(site_supply != null){
                    site_supply_documents.put(site_supply.getId(), site_supply);
                }
            }
        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
