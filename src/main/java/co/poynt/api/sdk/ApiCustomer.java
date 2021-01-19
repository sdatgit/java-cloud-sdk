package co.poynt.api.sdk;

import java.io.IOException;

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
    public static final String API_CUSTOMERS = "/businesses/{businessId}/customers";
    private static final Logger logger = LoggerFactory.getLogger(ApiCustomer.class);

    public ApiCustomer(PoyntSdk sdk) {
        super(sdk, Constants.POYNT_API_HOST + API_CUSTOMERS);
    }


    public Customer get(String businessId, String customerId) {
        return this.getFromBusiness(Customer.class, businessId, customerId);
    }


    public CustomerList getAll(String businessId, String next) {
        CustomerList result = null;
        String accessToken = sdk.getAccessToken();

        String baseUrl = this.endPoint.replace("{businessId}", businessId);
        baseUrl += "?limit=100";
        if(next != null) {
            baseUrl = Constants.POYNT_API_HOST + next;
        }
        
        logger.info("getAll: url={}", baseUrl);
        HttpGet get = this.createGetRequest(baseUrl);

        get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        try {
            HttpResponse response = this.sdk.getHttpClient().execute(get);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = this.readResponse(response, CustomerList.class);
            } else {
                handleError(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Failed to get customers from business {}", businessId, e);
            throw new PoyntSdkException("Failed to get customers from business=" + businessId);
        } finally {
            get.releaseConnection();
        }
        return result;

    }
}
