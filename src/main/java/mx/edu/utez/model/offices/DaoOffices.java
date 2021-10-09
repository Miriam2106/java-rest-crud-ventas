package mx.edu.utez.model.offices;

import mx.edu.utez.model.customer.Customer;
import mx.edu.utez.util.ConnectionMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoOffices {
    Connection con;
    PreparedStatement pstm;
    Statement statement;
    ResultSet rs;

    public List<Offices> findAllOffices(){ //Consultar todas las oficinas
        List<Offices> offices = new ArrayList<>();
        try{
            con = ConnectionMySQL.getConnection();
            String query = "SELECT officeCode,city,phone, addressLine1, addressLine2, state, country, postalCode,territory FROM offices;";
            statement = con.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()){
                Offices office = new Offices();
                office.setOfficeCode(rs.getString("officeCode"));
                office.setCity(rs.getString("city"));
                office.setPhone(rs.getString("phone"));
                office.setAddressLine1(rs.getString("addressLine1"));
                office.setAddressLine2(rs.getString("addressLine2"));
                office.setState(rs.getString("statE"));
                office.setPostalCode(rs.getString("postalCode"));
                office.setCountry(rs.getString("country"));
                office.setTerritory(rs.getString("territory"));
                offices.add(office);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return offices;
    }

    public Offices findOfficeById(String officeCode){
        Offices office = new Offices();
        try{
            con = ConnectionMySQL.getConnection();
            String query = "SELECT officeCode,city,phone, addressLine1, addressLine2, state, country, postalCode,territory FROM offices WHERE officeCode = ?;";
            pstm = con.prepareStatement(query);
            pstm.setString(1,officeCode);
            rs = pstm.executeQuery();
            if (rs.next()){
                office.setOfficeCode(rs.getString("officeCode"));
                office.setCity(rs.getString("city"));
                office.setPhone(rs.getString("phone"));
                office.setAddressLine1(rs.getString("addressLine1"));
                office.setAddressLine2(rs.getString("addressLine2"));
                office.setState(rs.getString("statE"));
                office.setPostalCode(rs.getString("postalCode"));
                office.setCountry(rs.getString("country"));
                office.setTerritory(rs.getString("territory"));
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return office;
    }

    public boolean insertOffice(Offices office, boolean insert){
        boolean flag = false;
        try{
            con = ConnectionMySQL.getConnection();
            if(insert){
                String query = "INSERT INTO offices(officeCode,city,phone, addressLine1, addressLine2, state, country, postalCode,territory) values(?,?,?,?,?,?,?,?,?);";
                pstm = con.prepareStatement(query);
                pstm.setString(1, office.getOfficeCode());
                pstm.setString(2, office.getCity());
                pstm.setString(3, office.getPhone());
                pstm.setString(4, office.getAddressLine1());
                pstm.setString(5, office.getAddressLine2());
                pstm.setString(6, office.getState());
                pstm.setString(7, office.getCountry());
                pstm.setString(8, office.getPostalCode());
                pstm.setString(9, office.getTerritory());
            }else{
                String query = "UPDATE offices SET city = ?,phone = ?,addressLine1 = ?,addressLine2 = ?, state = ?,country = ?,postalCode = ?,territory = ? WHERE officeCode = ?;";
                pstm = con.prepareStatement(query);
                pstm.setString(1, office.getCity());
                pstm.setString(2, office.getPhone());
                pstm.setString(3, office.getAddressLine1());
                pstm.setString(4, office.getAddressLine2());
                pstm.setString(5, office.getState());
                pstm.setString(6, office.getCountry());
                pstm.setString(7, office.getPostalCode());
                pstm.setString(8, office.getTerritory());
                pstm.setString(9, office.getOfficeCode());
            }
            flag = pstm.executeUpdate() == 1;
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            closeConnection();
        }
        return flag;
    }

    public boolean deleteOffice(String officeCode){
        boolean state = false;
        try{
            con = ConnectionMySQL.getConnection();
            String query = "delete from offices where officeCode = ?;";
            pstm = con.prepareStatement(query);
            pstm.setString(1, officeCode);
            state = pstm.executeUpdate() == 1;
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            closeConnection();
        }
        return state;
    }

    public void closeConnection(){
        try{
            if(con != null){
                con.close();
            }
            if(pstm != null){
                pstm.close();
            }
            if(rs != null){
                rs.close();
            }
            if(statement != null){
                statement.close();
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
