package co.poynt.api.sdk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// by Wavelety, Inc.
public class PoyntSdkBuilder extends PoyntSdk.Builder {
    private static final Logger logger = LoggerFactory.getLogger(PoyntSdkBuilder.class);
    public static final String POYNT_MERCHANT_ID = "poynt.biz";
    public static final String POYNT_ACCESS_TOKEN = "accessToken";
    public static final String POYNT_REFRESH_TOKEN = "refreshToken";
    public static final String POYNT_TOKEN_EXPIRY = "expiresIn";

    private PoyntSdk sdk;
    private JsonWebTokenService jwt2;

    /**
     * getJwt2 gives access to JSONToken util providing sign and verify functions
     * @return
     */
    public JsonWebTokenService getJwt2() {
        return jwt2;
    }

    public PoyntSdkBuilder configure(String appId, Reader keyPairReader) throws PoyntSdkException {
        try {
            PoyntSdkConfig config = new PoyntSdkConfig();
            config.load("config.properties");
            config.setAppId(appId);
            config.setKeyPair(keyPairReader);

            this.cfg = config;

            //create jwt util
            jwt2 = new JsonWebTokenService(null);
            jwt2.init(cfg.getAppId(), config.getKeyPair());
            //set in parent so it could be used to set in sdk
            this.jwt = jwt2;

        }
        catch (IOException e) {
            logger.error("Failed to configure ", e);
            throw new PoyntSdkException("Failed to initialize JWT2.");
        }

        //set httpclient if needed

        return this;
    }

    public void rebuild(PoyntSdk sdk) {
        sdk.jsonWebToken(this.jwt);
        sdk.setConfig(this.cfg);
        this.sdk = sdk;
    }

    /**
     * getAccessToken gets access token after merchant authorizes our app
     * @param code
     * @param redirectUri
     * @return
     */
    public Map<String, Object> getAccessToken(String code, String redirectUri) {

        if (code == null) {
            throw new IllegalArgumentException("code is required!");
        }
        if (redirectUri == null) {
            throw new IllegalArgumentException("redirectUri is required!");
        }
        /**
         * From your server make a HTTP POST request to https://services.poynt.net/token and include the following headers and arguments:
        Header: Accept: application/json
        Header: Authorization: Bearer {self-signed-JWT}
        HTTP param: grant_type=authorization_code
        HTTP param: redirect_uri={redirect_uri}
        HTTP param: client_id={appId}
        HTTP param: code={code} 
         */
        HttpPost post = new HttpPost(Constants.POYNT_API_HOST + Constants.API_TOKEN);

        post.setHeader("Accept", "application/json");
        post.setHeader("Authorization", "Bearer " + jwt.selfIssue());

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

        urlParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        urlParameters.add(new BasicNameValuePair("redirect_uri", redirectUri));
        urlParameters.add(new BasicNameValuePair("client_id", sdk.getConfig().getAppId()));
        urlParameters.add(new BasicNameValuePair("code", code));

        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters));

            HttpResponse response = sdk.getHttpClient().execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                @SuppressWarnings("unchecked")
                Map<String, Object> tokenProperties = sdk.getOm().readValue(result.toString(), HashMap.class);
                return tokenProperties;
            }
            else {
                String emsg = String.format("Failed to get access token for authz code=%s response status=%d message=%s", code, response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
                logger.error(emsg);
                throw new PoyntSdkException(emsg);
            }
        }
        catch (Exception e) {
            if(e instanceof PoyntSdkException) {
                throw (PoyntSdkException)e;
            }
            logger.error("Failed to get access token for authz code={}", code, e);
            throw new PoyntSdkException("Failed to obtain access token.");
        }
        finally {
            post.releaseConnection();
        }
    }
}
