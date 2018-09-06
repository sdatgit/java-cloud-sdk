package co.poynt.api.sdk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;

import co.poynt.api.model.ResourceList;

public class ApiSubscription extends Api {

    private static final Logger logger = LoggerFactory.getLogger(ApiSubscription.class);

    public static final String API_SUBSCRIPTIONS = "/apps/{appId}/subscriptions?businessId={businessId}";

    public ApiSubscription(PoyntSdk sdk, String appId) {

        super(sdk, Constants.POYNT_BILLING_API_HOST + API_SUBSCRIPTIONS.replace("{appId}", appId));

    }

    public <T> ResourceList<T> getAllFromBusiness2(Class<T> resourceType, String businessId) {
        ResourceList<T> result = null;
        String accessToken = sdk.getAccessToken();

        String baseUrl = this.endPoint.replace("{businessId}", businessId);
        HttpGet get = this.createGetRequest(baseUrl);

        get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        try {
            HttpResponse response = this.sdk.getHttpClient().execute(get);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = this.readResourceListResponse(response);
            }
            else {
                handleError(response);
            }
        }
        catch (IOException e) {

            logger.error("Failed to get business", e);

            throw new PoyntSdkException("Failed to get business");
        }
        finally {
            get.releaseConnection();
        }

        return result;
    }

    public <T> ResourceList<T> readResourceListResponse(HttpResponse response) throws IOException {
        String responseContent = readResponse(response);

        ResourceList<T> responseList = this.sdk.getOm().readValue(responseContent, new TypeReference<ResourceList<T>>() {});
        return responseList;
    }
    
    private String readResponse(HttpResponse response) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer sb = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

}
