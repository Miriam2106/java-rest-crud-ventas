package mx.edu.utez.controller;

import mx.edu.utez.model.customer.Customer;
import mx.edu.utez.model.customer.DaoCustomer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

@Path("/customer")
public class Service {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getCustomers(){
        return new DaoCustomer().findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer getCustomers(@PathParam("id") int customerNumber){ //CONSULTA POR ID
        return new DaoCustomer().findById(customerNumber);
    }

    @POST
    @Path("/save") //Registra
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Customer save(MultivaluedMap<String, String> formParams){
        int customerNumber = Integer.parseInt(formParams.get("customerNumber").get(0));
        if(new DaoCustomer().insertCustomer(getParams(customerNumber, formParams), true))
            return new DaoCustomer().findById(customerNumber);
        return null;
    }

    @POST
    @Path("/save/{id}") //Modifica
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Customer save(@PathParam("id") int customerNumber, MultivaluedMap<String, String> formParams){
        if(new DaoCustomer().insertCustomer(getParams(customerNumber, formParams), false))
            return new DaoCustomer().findById(customerNumber);
        return null;
    }

    @POST
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean deleteCustomer(@PathParam("id") int customerNumber){
        return new DaoCustomer().deleteCustomer(customerNumber);
    }

    private Customer getParams(int customerNumber, MultivaluedMap<String, String> formParams) {
        String customerName = formParams.get("customerName").get(0);
        String contactFirstname = formParams.get("contactFirstname").get(0);
        String contactLastname = formParams.get("contactLastname").get(0);
        String phone = formParams.get("phone").get(0);
        String address1 = formParams.get("addressLine1").get(0);
        String address2 = formParams.get("addressLine2").get(0);
        String city = formParams.get("city").get(0);
        String state = formParams.get("state").get(0);
        String postalCode = formParams.get("postalCode").get(0);
        String country = formParams.get("country").get(0);
        int salesRep = Integer.parseInt(formParams.get("salesRepEmployeeNumber").get(0));
        double credit = Double.parseDouble(formParams.get("creditLimit").get(0));

        Customer customer = new Customer(customerNumber, customerName, contactLastname, contactFirstname, phone, address1, address2, city, state, postalCode, country, salesRep, credit);
        System.out.println(customer);
        return customer;
    }
}
