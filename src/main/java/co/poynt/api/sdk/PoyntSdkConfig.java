package co.poynt.api.sdk;

import java.io.Reader;

//by Wavelety, Inc.
public class PoyntSdkConfig extends Config {
    
    private Reader keyPairReader;
    
    public void setAppId(String appId) {
        prop.setProperty(Constants.PROP_APP_ID, appId);
    }

    public Reader getKeyPair() {
        return keyPairReader;
    }

    public void setKeyPair(Reader keyPairReader) {
        this.keyPairReader = keyPairReader;
    }
  
    
    
}
