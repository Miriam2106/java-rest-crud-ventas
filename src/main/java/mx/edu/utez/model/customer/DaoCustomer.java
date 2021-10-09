package mx.edu.utez.model.customer;

import mx.edu.utez.util.ConnectionMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoCustomer {
    Connection con;
    PreparedStatement pstm;
    Statement statement;
    ResultSet rs;

    public List<Customer> findAll(){ //Consultar todos
        List<Customer> customers = new ArrayList<>();
        try{
            con = ConnectionMySQL.getConnection();
            String query = "SELECT customerNumber, customerName, contactLastname, contactFirstname, phone,addressLine1, addressLine2, city, state, postalCode, country, salesRepEmployeeNumber, creditLimit FROM customers;";
            statement = con.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()){
                Customer customer = new Customer();
                customer.setCustomerNumber(rs.getInt("customerNumber"));
                customer.setCustomerName(rs.getString("customerName"));
                customer.setContactLastname(rs.getString("contactLastname"));
                customer.setContactFirstname(rs.getString("contactFirstname"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddressLine1(rs.getString("addressLine1"));
                customer.setAddressLine2(rs.getString("addressLine2"));
                customer.setCity(rs.getString("city"));
                customer.setState(rs.getString("state"));
                customer.setPostalCode(rs.getString("postalCode"));
                customer.setCountry(rs.getString("country"));
                customer.setSalesRepEmployeeNumber(rs.getInt("salesRepEmployeeNumber"));
                customer.setCreditLimit(rs.getDouble("creditLimit"));
                customers.add(customer);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return customers;
    }

    public Customer findById(int customerNum){
        Customer customer = new Customer();
        try{
            con = ConnectionMySQL.getConnection();
            String query = "SELECT customerNumber, customerName, contactLastname, contactFirstname, phone,addressLine1, addressLine2, city, state, postalCode, country, salesRepEmployeeNumber, creditLimit FROM customers WHERE customerNumber = ?";
            pstm = con.prepareStatement(query);
            pstm.setInt(1,customerNum);
            rs = pstm.executeQuery();
            if (rs.next()){
                customer.setCustomerNumber(rs.getInt("customerNumber"));
                customer.setCustomerName(rs.getString("customerName"));
                customer.setContactLastname(rs.getString("contactLastname"));
                customer.setContactFirstname(rs.getString("contactFirstname"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddressLine1(rs.getString("addressLine1"));
                customer.setAddressLine2(rs.getString("addressLine2"));
                customer.setCity(rs.getString("city"));
                customer.setState(rs.getString("state"));
                customer.setPostalCode(rs.getString("postalCode"));
                customer.setCountry(rs.getString("country"));
                customer.setSalesRepEmployeeNumber(rs.getInt("salesRepEmployeeNumber"));
                customer.setCreditLimit(rs.getDouble("creditLimit"));
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return customer;
    }

    public boolean insertCustomer(Customer customer, boolean insert){
        boolean flag = false;
        try{
            con = ConnectionMySQL.getConnection();
            if(insert){
                String query = "INSERT INTO customers(customerNumber,customerName,contactLastname,contactFirstname,phone,addressLine1,addressLine2,city,state,postalCode,country,salesRepEmployeeNumber,creditLimit) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
                pstm = con.prepareStatement(query);
                pstm.setInt(1, customer.getCustomerNumber());
                pstm.setString(2, customer.getCustomerName());
                pstm.setString(3, customer.getContactLastname());
                pstm.setString(4, customer.getContactFirstname());
                pstm.setString(5, customer.getPhone());
                pstm.setString(6, customer.getAddressLine1());
                pstm.setString(7, customer.getAddressLine2());
                pstm.setString(8, customer.getCity());
                pstm.setString(9, customer.getState());
                pstm.setString(10, customer.getPostalCode());
                pstm.setString(11, customer.getCountry());
                pstm.setInt(12, customer.getSalesRepEmployeeNumber());
                pstm.setDouble(13, customer.getCreditLimit());
            }else{
                String query = "UPDATE customers SET customerName =?,contactLastname =?,contactFirstname =?,phone =?,addressLine1 =?,addressLine2 =?,city =?,state =?,postalCode =?,country =?,salesRepEmployeeNumber =?,creditLimit =? WHERE customerNumber = ?;";
                pstm = con.prepareStatement(query);
                pstm.setString(1, customer.getCustomerName());
                pstm.setString(2, customer.getContactLastname());
                pstm.setString(3, customer.getContactFirstname());
                pstm.setString(4, customer.getPhone());
                pstm.setString(5, customer.getAddressLine1());
                pstm.setString(6, customer.getAddressLine2());
                pstm.setString(7, customer.getCity());
                pstm.setString(8, customer.getState());
                pstm.setString(9, customer.getPostalCode());
                pstm.setString(10, customer.getCountry());
                pstm.setInt(11, customer.getSalesRepEmployeeNumber());
                pstm.setDouble(12, customer.getCreditLimit());
                pstm.setInt(13, customer.getCustomerNumber());
            }
            flag = pstm.executeUpdate() == 1;
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            closeConnection();
        }
        return flag;
    }

    public boolean deleteCustomer(int customerNumber){
        boolean state = false;
        try{
            con = ConnectionMySQL.getConnection();
            String query = "delete from customers where customerNumber = ?;";
            pstm = con.prepareStatement(query);
            pstm.setInt(1, customerNumber);
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
