package co.poynt.api.sdk;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import co.poynt.api.model.Customer;
import co.poynt.api.model.CustomerList;

/**
 * Utility to get customers for givne app
 * @author sanjay
 *
 */
public class GetCustomers {
    public static void main(String[] args) throws FileNotFoundException {

        final String businessId = "11616893-4a93-4f1a-b81a-8762abf1d8c6"; //cafe cafe
        final String appId = "urn:aid:f3bb639a-2073-4928-9d2d-6ebf34a80f04"; //spotindev

        PoyntSdk sdk = PoyntSdk.builder().configure("config.properties").build();

        PoyntSdkBuilder builder2 = new PoyntSdkBuilder();
        Reader reader = new InputStreamReader(GetCustomers.class.getClassLoader().getResourceAsStream("urn_aid_f3bb639a-2073-4928-9d2d-6ebf34a80f04_publicprivatekey.pem"));

        builder2.configure(appId, reader);
        builder2.rebuild(sdk);

        ApiCustomer apiCustomer = new ApiCustomer(sdk);
        List<Customer> customers = apiCustomer.getAllCustomers(businessId);
        for (Customer customer : customers) {
            System.out.println("Customer=" + customer.toString());
        }
        System.out.println("=============Done!");
    }
}
