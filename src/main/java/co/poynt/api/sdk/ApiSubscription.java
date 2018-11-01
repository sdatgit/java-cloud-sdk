package co.poynt.api.sdk;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.poynt.api.model.ResourceList;
import co.poynt.api.model.Subscription;

public class ApiSubscription extends ApiBilling {

    private static final Logger logger = LoggerFactory.getLogger(ApiSubscription.class);

    public static final String API_SUBSCRIPTIONS = "/apps/{appId}/subscriptions";

    public ApiSubscription(PoyntSdk sdk, String appId) {

        super(sdk, Constants.POYNT_BILLING_API_HOST + API_SUBSCRIPTIONS.replace("{appId}", appId));

    }

    public ResourceList<Subscription> getAllFromBusiness(String businessId) {
        ResourceList<Subscription> result = null;
        String accessToken = sdk.getAccessToken();
        String baseUrl = this.endPoint + "?businessId={businessId}";

        baseUrl = baseUrl.replace("{businessId}", businessId);
        HttpGet get = this.createGetRequest(baseUrl);

        get.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        try {
            HttpResponse response = this.sdk.getHttpClient().execute(get);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = this.readResourceListResponse(response);
            }
            else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                logger.error("No subscription found for businessId={} error={}", businessId, response.toString());
            }
            else {
                handleError(response);
            }
        }
        catch (IOException e) {

            logger.error("Failed to get subscriptions", e);

            throw new PoyntSdkException("Failed to get subscriptions");
        }
        finally {
            get.releaseConnection();
        }

        return result;
    }

}
