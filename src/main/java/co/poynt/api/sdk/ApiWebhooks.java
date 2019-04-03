package co.poynt.api.sdk;


import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.poynt.api.model.ResourceList;
import co.poynt.api.model.Hook;

public class ApiWebhooks extends CustomApi {

    private static final Logger logger = LoggerFactory.getLogger(ApiWebhooks.class);
    public ApiWebhooks(PoyntSdk sdk) {

        super(sdk, Constants.POYNT_API_HOST + Constants.API_WEBHOOKS);

    }

    public ResourceList<Hook> getAllFromBusiness(String businessId) {
        ResourceList<Hook> result = null;
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
                logger.error("No Hook found for businessId={} error={}", businessId, response.toString());
            }
            else {
                handleError(response);
            }
        }
        catch (IOException e) {

            logger.error("Failed to get Hooks", e);

            throw new PoyntSdkException("Failed to get Hooks");
        }
        finally {
            get.releaseConnection();
        }

        return result;
    }

}
