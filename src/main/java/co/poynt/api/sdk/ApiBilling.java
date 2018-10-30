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

/**
 * ApiBilling provides methods to manage Poynt Billing
 * @author sanjay
 *
 */
public class ApiBilling extends Api {
    
    static final Logger logger = LoggerFactory.getLogger(ApiBilling.class);
    
    public ApiBilling(PoyntSdk sdk, String endPoint) {
        super(sdk, endPoint);
        // TODO Auto-generated constructor stub
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

    /**
     * getAll
     * @param resourceType
     * @return
     */
    public <T> ResourceList<T> getAll() {
            ResourceList<T> result = null;
            String accessToken = sdk.getAccessToken();
    
            String baseUrl = this.endPoint;
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
    
                logger.error("Failed to get resource", e);
    
                throw new PoyntSdkException("Failed to get resource " + e.getMessage());
            }
            finally {
                get.releaseConnection();
            }

            return result;
        }

}
