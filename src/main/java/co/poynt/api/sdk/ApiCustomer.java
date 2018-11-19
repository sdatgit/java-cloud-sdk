package co.poynt.api.sdk;

import java.util.List;

import co.poynt.api.model.Customer;
import co.poynt.api.model.CustomerList;


//by Wavelety, Inc.
public class ApiCustomer extends Api {
    public static final String API_CUSTOMERS = "/businesses/{businessId}/customers";

    public ApiCustomer(PoyntSdk sdk) {
        super(sdk, Constants.POYNT_API_HOST + API_CUSTOMERS);
    }

    public List<Customer> getAll(String businessId) {

        return getAllFromBusiness(Customer.class, businessId);
    }
    
    public Customer get(String businessId, String customerId) {
        return this.getFromBusiness(Customer.class, businessId, customerId);
    }
    
    public List<Customer> getAllCustomers(String businessId) {
        CustomerList list = (CustomerList)getAllFromBusiness(CustomerList.class, businessId);
        return list.getCustomers();
    }
}
