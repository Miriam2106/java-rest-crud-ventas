package mx.edu.utez.controller;

import mx.edu.utez.model.customer.DaoCustomer;
import mx.edu.utez.model.offices.DaoOffices;
import mx.edu.utez.model.offices.Offices;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

@Path("/offices")
public class ServiceOffices {
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Offices> getOffices(){
        return new DaoOffices().findAllOffices();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Offices getOffices(@PathParam("id") String officeCode){ //CONSULTA POR ID
        return new DaoOffices().findOfficeById(officeCode);
    }

    @POST
    @Path("/save") //Registra
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Offices save(MultivaluedMap<String, String> formParams){
        String officeCode = formParams.get("officeCode").get(0);
        if(new DaoOffices().insertOffice(getParams(officeCode, formParams), true))
            return new DaoOffices().findOfficeById(officeCode);
        return null;
    }

    @POST
    @Path("/save/{id}") //Modifica
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Offices save(@PathParam("id") String officeCode, MultivaluedMap<String, String> formParams){
        if(new DaoOffices().insertOffice(getParams(officeCode, formParams), false))
            return new DaoOffices().findOfficeById(officeCode);
        return null;
    }

    @POST
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean delete(@PathParam("id") String officeCode){
        return new DaoOffices().deleteOffice(officeCode);
    }

    private Offices getParams(String officeCode, MultivaluedMap<String, String> formParams) {
         // officeCode,city,phone, addressLine1, addressLine2, state, country, postalCode,territory
        String city = formParams.get("city").get(0);
        String phone = formParams.get("phone").get(0);
        String address1 = formParams.get("addressLine1").get(0);
        String address2 = formParams.get("addressLine2").get(0);
        String state = formParams.get("state").get(0);
        String postalCode = formParams.get("postalCode").get(0);
        String country = formParams.get("country").get(0);
        String territory = formParams.get("territory").get(0);

        Offices office = new Offices(officeCode,city,phone, address1, address2, state, country, postalCode,territory);
        System.out.println(office);
        return office;
    }
}
