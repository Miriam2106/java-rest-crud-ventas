package mx.edu.utez.controller;

import mx.edu.utez.model.product.DaoProducts;
import mx.edu.utez.model.product.Products;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

@Path("/products")
public class ServiceProducts {
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Products> getProducts(){
        return new DaoProducts().findAllProducts();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Products getProducts(@PathParam("id") String productCode){ //CONSULTA POR ID
        return new DaoProducts().findProductById(productCode);
    }

    @POST
    @Path("/save") //Registra
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Products save(MultivaluedMap<String, String> formParams){
        String productCode = formParams.get("productCode").get(0);
        if(new DaoProducts().insertProduct(getParams(productCode, formParams), true))
            return new DaoProducts().findProductById(productCode);
        return null;
    }

    @POST
    @Path("/save/{id}") //Modifica
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Products save(@PathParam("id") String productCode, MultivaluedMap<String, String> formParams){
        if(new DaoProducts().insertProduct(getParams(productCode, formParams), false))
            return new DaoProducts().findProductById(productCode);
        return null;
    }

    @POST
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean delete(@PathParam("id") String productCode){
        return new DaoProducts().deleteProducts(productCode);
    }

    private Products getParams(String productCode, MultivaluedMap<String, String> formParams) {
//productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP
        String productName = formParams.get("productName").get(0);
        String productLine = formParams.get("productLine").get(0);
        String productScale = formParams.get("productScale").get(0);
        String productVendor = formParams.get("productVendor").get(0);
        String productDescription = formParams.get("productDescription").get(0);
        int quantityInStock = Integer.parseInt(formParams.get("quantityInStock").get(0));
        double buyPrice = Double.parseDouble(formParams.get("buyPrice").get(0));
        double MSRP = Double.parseDouble(formParams.get("MSRP").get(0));

        Products product = new Products(productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP);
        System.out.println(product);
        return product;
    }
}
