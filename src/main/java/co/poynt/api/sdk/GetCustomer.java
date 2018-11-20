package co.poynt.api.sdk;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import co.poynt.api.model.Customer;

/**
 * Utility to get customer for given app
 *
 */
public class GetCustomer {
    public static void main(String[] args) throws FileNotFoundException {

        final String businessId = "11616893-4a93-4f1a-b81a-8762abf1d8c6"; //cafe cafe
        final String appId = "urn:aid:f3bb639a-2073-4928-9d2d-6ebf34a80f04"; //spotindev

        PoyntSdk sdk = PoyntSdk.builder().configure("config.properties").build();

        PoyntSdkBuilder builder2 = new PoyntSdkBuilder();
        Reader reader = new InputStreamReader(GetCustomer.class.getClassLoader().getResourceAsStream("urn_aid_f3bb639a-2073-4928-9d2d-6ebf34a80f04_publicprivatekey.pem"));

        builder2.configure(appId, reader);
        builder2.rebuild(sdk);

        ApiCustomer apiCustomer = new ApiCustomer(sdk);
        
        //getAllCustomers has parsing problems
//        List<Customer> customers = apiCustomer.getAllCustomers(businessId);
//        for (Customer customer : customers) {
//            System.out.println("Customer=" + customer.toString());
//        }
        Customer customer = apiCustomer.getFromBusiness(Customer.class, businessId, "" + 77416525);
        System.out.println("retrieve customer=" + customer.toString());
        Map<String, String> atts = customer.getAttributes();
        for(Map.Entry<String, String> entry: atts.entrySet()) {
            System.out.println("k=" + entry.getKey() + " val=" + entry.getValue());
        }
        System.out.println("=============Done!");
    }
}
