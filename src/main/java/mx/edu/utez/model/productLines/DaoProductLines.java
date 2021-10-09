package mx.edu.utez.model.productLines;

import mx.edu.utez.util.ConnectionMySQL;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class DaoProductLines {
    Connection con;
    PreparedStatement pstm;
    Statement statement;
    ResultSet rs;

    public List<ProductLines> findAllProductLines(){ //Consultar todos los productos
        List<ProductLines> productLines = new ArrayList<>();
        try{
            con = ConnectionMySQL.getConnection();
            String query = "SELECT productLine, textDescription, htmlDescription, image FROM productLines;";
            statement = con.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()){
                ProductLines productL = new ProductLines();
                productL.setProductLine(rs.getString("productLine"));
                productL.setTextDescription(rs.getString("textDescription"));
                productL.setHtmlDescription(rs.getString("htmlDescription"));
                Blob blob = rs.getBlob("image");
                if(blob != null){
                    InputStream inputStream = blob.getBinaryStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] imageBytes = outputStream.toByteArray();
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    inputStream.close();
                    outputStream.close();
                    productL.setImage(base64Image);
                }
                productLines.add(productL);
            }
        }catch (SQLException | IOException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return productLines;
    }

    public ProductLines findProductLineById(String productLine){
        ProductLines productLines = new ProductLines();
        try{
            con = ConnectionMySQL.getConnection();
            String query = "SELECT productLine, textDescription, htmlDescription, image FROM productLines WHERE productLine = ?";
            pstm = con.prepareStatement(query);
            pstm.setString(1,productLine);
            rs = pstm.executeQuery();
            if (rs.next()){
                productLines.setProductLine(rs.getString("productLine"));
                productLines.setTextDescription(rs.getString("textDescription"));
                productLines.setHtmlDescription(rs.getString("htmlDescription"));
                productLines.setImage(rs.getString("image"));
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            closeConnection();
        }
        return productLines;
    }

    public boolean insertProductLines(ProductLines productLines, boolean insert){
        boolean flag = false;
        try{
            con = ConnectionMySQL.getConnection();
            if(insert){
                String query = "INSERT INTO productlines(productLine, textDescription, htmlDescription, image) values(?,?,?,?);";
                pstm = con.prepareStatement(query);
                pstm.setString(1, productLines.getProductLine());
                pstm.setString(2, productLines.getTextDescription());
                pstm.setString(3, productLines.getHtmlDescription());
                pstm.setString(4, productLines.getImage());
            }else{
                String query = "UPDATE productlines SET textDescription =?, htmlDescription =?, image =? WHERE productLine = ?;";
                pstm = con.prepareStatement(query);
                pstm.setString(1, productLines.getTextDescription());
                pstm.setString(2, productLines.getHtmlDescription());
                pstm.setString(3, productLines.getImage());
                pstm.setString(4, productLines.getProductLine());
            }
            flag = pstm.executeUpdate() == 1;
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally{
            closeConnection();
        }
        return flag;
    }

    public boolean deleteProductLines(String productLines){
        boolean state = false;
        try{
            con = ConnectionMySQL.getConnection();
            String query = "delete from productlines where productLine = ?;";
            pstm = con.prepareStatement(query);
            pstm.setString(1, productLines);
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
