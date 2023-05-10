package DataAccessLayer.Transport;

import BussinessLayer.HRModule.Objects.Store;
import BussinessLayer.TransportationModule.objects.Site_Supply;
import BussinessLayer.TransportationModule.objects.Transport;
import BussinessLayer.TransportationModule.objects.cold_level;
import DataAccessLayer.DAO;
import DataAccessLayer.HRMoudle.StoresDAO;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Site_Supply_dao extends DAO {

    HashMap<Integer, Site_Supply> site_supply_documents;
    private static Site_Supply_dao site_supply_dao = null;
    public static Site_Supply_dao getInstance(){
        if (site_supply_dao == null)
            site_supply_dao = new Site_Supply_dao("Sites_Documents");
        return site_supply_dao;
    }

    private Site_Supply_dao(String tableName) {
        super(tableName);
        site_supply_documents = new HashMap<>();
        get_site_supply_documents_from_db();
    }


    public boolean Insert(Object obj) {
        Site_Supply site_supply = (Site_Supply) obj;
        try {
            String query = "INSERT INTO " + _tableName + " (ID, Store_Name, Origin, Total_Weight) VALUES (?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, site_supply.getId());
            statement.setString(2, site_supply.getStore().getSite_name());
            statement.setString(3, site_supply.getOrigin());
            statement.setDouble(4, site_supply.getProducts_total_weight());
            statement.execute();
            insert_products_to_table(site_supply);


            site_supply_documents.put(site_supply.getId(), site_supply);
            return true;
        } catch (SQLException e) {
            System.out.println("Exception thrown");
            System.out.println(e.getMessage());
        }
        return false;
    }


    public boolean Delete(Object obj) {
        Site_Supply site_supply = (Site_Supply) obj;
        String query = "DELETE FROM " + _tableName + " WHERE ID = ?";
        try {
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
        Store store;
        try {
            store = StoresDAO.getInstance().getStore(store_name);
        } catch (Exception e) {
            return null;
        }
        Site_Supply site_supply = new Site_Supply(res.getInt(1), store , res.getString(3));
        //insert_products_to_site_supply(site_supply);
        site_supply_documents.put(site_supply.getId(), site_supply);
        return site_supply;
    }

    public boolean check_if_site_supply_exists(int id){
        if(site_supply_documents.containsKey(id)){
            return true;
        }
        try {
            String query = "SELECT * FROM " + _tableName + " WHERE ID = ?";

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
        try {
            String query = "SELECT * FROM " + _tableName;

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                Site_Supply site_supply = (Site_Supply) convertReaderToObject(res);
                insert_products_to_site_supply(site_supply);
                if(site_supply != null){
                    site_supply_documents.put(site_supply.getId(), site_supply);
                }
            }
        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void insert_products_to_table(Site_Supply siteSupply){
        for (Map.Entry<String, Integer> entry: siteSupply.getItems().entrySet()){
            String query = "INSERT INTO Products_by_site_supply (Site_supply_ID, Name, Amount) VALUES (?,?,?)";
            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, siteSupply.getId());
                statement.setString(2, entry.getKey());
                statement.setInt(3, entry.getValue());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void insert_products_to_site_supply(Site_Supply siteSupply){
        String query = "SELECT * FROM Products_by_site_supply WHERE Site_supply_ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, siteSupply.getId());
            ResultSet res = statement.executeQuery();
            while (res.next()){
                siteSupply.insert_item(res.getString(2), res.getInt(3));
            }
        } catch (SQLException e) {
        }
    }
}
