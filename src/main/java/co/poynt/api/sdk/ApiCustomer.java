package co.poynt.api.sdk;

import java.util.List;

import co.poynt.api.model.Customer;


//by Wavelety, Inc.
public class ApiCustomer extends Api {
    public static final String API_CUSTOMERS = "/businesses/{businessId}/customers";

    public ApiCustomer(PoyntSdk sdk) {
        super(sdk, Constants.POYNT_API_HOST + API_CUSTOMERS);
    }

    public List<Customer> getAll(String businessId) {
        
        Customer customer = new Customer();
        createAtBusiness(customer, "businessId");
        return getAllFromBusiness(Customer.class, businessId);
    }
}
