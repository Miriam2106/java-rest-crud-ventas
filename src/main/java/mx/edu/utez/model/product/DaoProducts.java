package mx.edu.utez.model.product;

import mx.edu.utez.util.ConnectionMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoProducts {
    Connection con;
    PreparedStatement pstm;
    Statement statement;
    ResultSet rs;

    public List<Products> findAllProducts(){ //Consultar todos los productos
        List<Products> products = new ArrayList<>();
        try{
            con = ConnectionMySQL.getConnection();
            String query = "SELECT productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP FROM products;";
            statement = con.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()){
                Products product = new Products();
                product.setProductCode(rs.getString("productCode"));
                product.setProductName(rs.getString("productName"));
                product.setProductLine(rs.getString("productLine"));
                product.setProductScale(rs.getString("productScale"));
                product.setProductVendor(rs.getString("productVendor"));
                product.setProductDescription(rs.getString("productDescription"));
                product.setQuantityInStock(rs.getInt("quantityInStock"));
                product.setBuyPrice(rs.getDouble("buyPrice"));
                product.setMSRP(rs.getDouble("MSRP"));
                products.add(product);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return products;
    }

    public Products findProductById(String productCode){
        Products product = new Products();
        try{
            con = ConnectionMySQL.getConnection();
            String query = "SELECT productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP FROM products WHERE productCode = ?";
            pstm = con.prepareStatement(query);
            pstm.setString(1,productCode);
            rs = pstm.executeQuery();
            if (rs.next()){
                product.setProductCode(rs.getString("productCode"));
                product.setProductName(rs.getString("productName"));
                product.setProductLine(rs.getString("productLine"));
                product.setProductScale(rs.getString("productScale"));
                product.setProductVendor(rs.getString("productVendor"));
                product.setProductDescription(rs.getString("productDescription"));
                product.setQuantityInStock(rs.getInt("quantityInStock"));
                product.setBuyPrice(rs.getInt("buyPrice"));
                product.setMSRP(rs.getInt("MSRP"));
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return product;
    }

    public boolean insertProduct(Products product, boolean insert){
        boolean flag = false;
        try{
            con = ConnectionMySQL.getConnection();
            if(insert){
                String query = "INSERT INTO products(productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP) values(?,?,?,?,?,?,?,?,?);";
                pstm = con.prepareStatement(query);
                pstm.setString(1, product.getProductCode());
                pstm.setString(2, product.getProductName());
                pstm.setString(3, product.getProductLine());
                pstm.setString(4, product.getProductScale());
                pstm.setString(5, product.getProductVendor());
                pstm.setString(6, product.getProductDescription());
                pstm.setInt(7, product.getQuantityInStock());
                pstm.setDouble(8, product.getBuyPrice());
                pstm.setDouble(9, product.getMSRP());
            }else{
                String query = "UPDATE products SET productName =?, productLine =?, productScale =?, productVendor =?, productDescription =?, quantityInStock =?, buyPrice =?, MSRP =? WHERE productCode = ?;";
                pstm = con.prepareStatement(query);
                pstm.setString(1, product.getProductName());
                pstm.setString(2, product.getProductLine());
                pstm.setString(3, product.getProductScale());
                pstm.setString(4, product.getProductVendor());
                pstm.setString(5, product.getProductDescription());
                pstm.setInt(6, product.getQuantityInStock());
                pstm.setDouble(7, product.getBuyPrice());
                pstm.setDouble(8, product.getMSRP());
                pstm.setString(9, product.getProductCode());
            }
            flag = pstm.executeUpdate() == 1;
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            closeConnection();
        }
        return flag;
    }

    public boolean deleteProducts(String productCode){
        boolean state = false;
        try{
            con = ConnectionMySQL.getConnection();
            String query = "delete from products where productCode = ?;";
            pstm = con.prepareStatement(query);
            pstm.setString(1, productCode);
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

