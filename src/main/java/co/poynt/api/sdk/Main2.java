package co.poynt.api.sdk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import co.poynt.api.model.Business;
import co.poynt.api.model.BusinessUser;
import co.poynt.api.model.Hook;

public class Main2 {
    public static void main(String[] args) throws FileNotFoundException {

        final String businessId = "feb2ea1a-d05b-4fa2-bc93-dfb9fdd4cb8f"; //geneva
        //"891b396d-fe26-4339-a810-ff2af2e277ba"; opinion
        PoyntSdk sdk = PoyntSdk.builder().configure("config.properties").build();

        PoyntSdkBuilder builder2 = new PoyntSdkBuilder();
        Reader reader = new InputStreamReader(Main2.class.getClassLoader().getResourceAsStream("urn_aid_08bc7784-a262-4b97-a84c-348ba0f21c3f_publicprivatekey.pem"));
        builder2.configure("urn:aid:08bc7784-a262-4b97-a84c-348ba0f21c3f", reader);
        builder2.rebuild(sdk);

        Map<String, Object> claims = ((JsonWebTokenService)builder2.jwt).decode(sdk.getAccessToken());
        System.out.println("============= claims = " + claims.toString());
        //String businessId = (String)claims.get(PoyntSdkBuilder.POYNT_JWT_CLAIM_ORG);
        System.out.println("============= BUSINESS id=" + businessId);
        
        Business business = sdk.business().get(businessId);
        System.out.println("============= BUSINESS =" + business);

        System.out.println("============= BUSINESS USER");
        List<BusinessUser> users = sdk.businessUser().getAll(businessId);
        System.out.println(users);

        //		System.out.println("=============CATALOG");
        //		CatalogWithProduct catalog = sdk.catalog().get(businessId, "675f0c80-6db8-4584-a444-6b213d0f4f66");
        //		System.out.println(catalog);
        //
        //		System.out.println("=============PRODUCT");
        //		Product product = sdk.product().get(businessId, "675f0c80-6db8-4584-a444-6b213d0f4f66");
        //		System.out.println(product);

        System.out.println("=============Webhooks");
        Hook hook = new Hook();
        hook.setApplicationId(sdk.getConfig().getAppId());
        hook.setBusinessId(UUID.fromString(businessId));
        hook.setDeliveryUrl("https://engage.colligso.com/poynt");
        hook.setEventTypes(Arrays.asList(new String[] { "ORDER_COMPLETED", "TRANSACTION_AUTHORIZED" }));

        // Poynt will use the secret below to generate a signature using
        // HmacSHA1 of the webhook payload
        // The signature will be send as an http header called
        // "poynt-webhook-signature".
        hook.setSecret("my-shared-secret-with-poynt");
        sdk.webhook().register(hook);

        System.out.println("=============Done!");
    }
}
