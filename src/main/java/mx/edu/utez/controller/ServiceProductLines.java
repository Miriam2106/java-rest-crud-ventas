package mx.edu.utez.controller;

import mx.edu.utez.model.productLines.DaoProductLines;
import mx.edu.utez.model.productLines.ProductLines;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

@Path("/productLines")
public class ServiceProductLines {
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductLines> getProductLines(){
        return new DaoProductLines().findAllProductLines();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductLines getProductLines(@PathParam("id") String productLine){ //CONSULTA POR ID
        return new DaoProductLines().findProductLineById(productLine);
    }

    @POST
    @Path("/save") //Registra
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public ProductLines save(MultivaluedMap<String, String> formParams){
        String productLine = formParams.get("productLine").get(0);
        if(new DaoProductLines().insertProductLines(getParams(productLine, formParams), true))
            return new DaoProductLines().findProductLineById(productLine);
        return null;
    }

    @POST
    @Path("/save/{id}") //Modifica
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public ProductLines save(@PathParam("id") String productLine, MultivaluedMap<String, String> formParams){
        if(new DaoProductLines().insertProductLines(getParams(productLine, formParams), false))
            return new DaoProductLines().findProductLineById(productLine);
        return null;
    }

    @POST
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean delete(@PathParam("id") String productLine){
        return new DaoProductLines().deleteProductLines(productLine);
    }

    private ProductLines getParams(String productLine, MultivaluedMap<String, String> formParams) {
        // productLine, textDescription, htmlDescription, image
        String textDescription = formParams.get("textDescription").get(0);
        String htmlDescription = formParams.get("htmlDescription").get(0);
        String image = formParams.get("image").get(0);

        ProductLines productL = new ProductLines(productLine, textDescription, htmlDescription, image);
        System.out.println(productL);
        return productL;
    }
}
