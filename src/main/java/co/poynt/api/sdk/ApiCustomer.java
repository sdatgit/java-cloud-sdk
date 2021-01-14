package co.poynt.api.sdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.poynt.api.model.Customer;
import co.poynt.api.model.CustomerList;

//by Wavelety, Inc.
public class ApiCustomer extends Api {
    public static final String API_CUSTOMERS = "/businesses/{businessId}/customers?limit=100&startAt={startAt}&endAt={endAt}";
    private static final Logger logger = LoggerFactory.getLogger(ApiCustomer.class);

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
        CustomerList list = (CustomerList) getAllFromBusiness(CustomerList.class, businessId);
        return list.getCustomers();
    }

    public List<Customer> getAll(String businessId, String startAt, String endAt, int offset) {
        List<CustomerList> result = new ArrayList<CustomerList>();
        String accessToken = sdk.getAccessToken();

        String baseUrl = this.endPoint.replace("{businessId}", businessId)
                .replace("startAt", startAt)
                .replace("endAt", endAt)
                .replace("startOffset", "" + offset);
        HttpGet get = this.createGetRequest(baseUrl);

        get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        try {
            HttpResponse response = this.sdk.getHttpClient().execute(get);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = this.readListResponse(response, CustomerList.class);
            }
            else {
                handleError(response);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            logger.error("Failed to get customers from business {}", businessId, e);
            throw new PoyntSdkException("Failed to get customers from business=" + businessId);
        }
        finally {
            get.releaseConnection();
        }
        if(result.size() > 0) {
            return result.get(0).getCustomers();
        } 
        return new ArrayList<Customer>();

    }
}
