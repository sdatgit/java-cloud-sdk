package co.poynt.api.sdk;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class JsonWebTokenService extends JsonWebToken {
    private String appId;
    private KeyPair keyPair;

    public JsonWebTokenService(Config config) {
        super(config);
    }

    /**
     * init initializes with given appId and key pair in string format
     * @param appId
     * @param keyPairString
     * @return
     * @throws IOException
     */
    public JsonWebTokenService init(String appId, Reader keyPairReader) throws IOException {
        if (appId == null || appId.isEmpty()) {
            throw new IllegalArgumentException("appId is required");
        }
        if (keyPairReader == null) {
            throw new IllegalArgumentException("keyPairReader is required");
        }
        this.appId = appId;

        try (PEMParser pp = new PEMParser(keyPairReader)) {
            PEMKeyPair pemKeyPair = (PEMKeyPair)pp.readObject();
            keyPair = new JcaPEMKeyConverter().getKeyPair(pemKeyPair);
        }
        return this;
    }

    @Override
    public String selfIssue() {
        JWSSigner signer = new RSASSASigner((RSAPrivateKey)keyPair.getPrivate());

        List<String> aud = new ArrayList<String>();
        aud.add(Constants.POYNT_API_HOST);

        JWTClaimsSet claimsSet = new JWTClaimsSet();
        claimsSet.setAudience(aud);
        claimsSet.setSubject(appId);
        claimsSet.setIssuer(appId);
        Calendar now = Calendar.getInstance();
        claimsSet.setIssueTime(now.getTime());
        now.add(Calendar.MINUTE, 15);
        claimsSet.setExpirationTime(now.getTime());
        claimsSet.setJWTID(UUID.randomUUID().toString());

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet);

        try {
            signedJWT.sign(signer);
        }
        catch (JOSEException e) {
            throw new PoyntSdkException("Failed to sign self issued JWT.");
        }
        return signedJWT.serialize();
    }

    /**
     * decode decodes given JWT and returns claims
     * @param jwtString
     * @return claims 
     * {
     * "sub": "urn:aid:08bc7784-a262-4b97-a84c-348ba0f21c3f",
     * "aud": "urn:aid:08bc7784-a262-4b97-a84c-348ba0f21c3f",
     * "poynt.sct": "J",
     * "poynt.org": "6dc5a48d-8f99-41e9-b716-b3c564f0711c",
     * "iss": "https:\/\/services.poynt.net",
     * "poynt.kid": 6155246152827141777,
     * "poynt.aty": "S",
     * "exp": 1534773013,
     * "iat": 1534686613,
     * "jti": "533b9d4e-03e0-40de-9b27-bc2c4d4d8c6a"
     * }
     */
    public Map<String, Object> decode(String jwtString) {
        try {

            SignedJWT signedJWT = SignedJWT.parse(jwtString);

            JWTClaimsSet claimsSet = (JWTClaimsSet)signedJWT.getJWTClaimsSet();
            return claimsSet.getAllClaims();

        }
        catch (ParseException e) {
            throw new PoyntSdkException("Failed to parse given JWT.");
        }

    }

}
