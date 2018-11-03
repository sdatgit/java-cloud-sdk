package co.poynt.api.sdk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import co.poynt.api.model.Business;
import co.poynt.api.model.BusinessUser;
import co.poynt.api.model.Hook;
import co.poynt.api.model.Plan;
import co.poynt.api.model.ResourceList;
import co.poynt.api.model.Store;
import co.poynt.api.model.Subscription;

public class Main2 {
    public static void main(String[] args) throws FileNotFoundException {

        final String businessId = "11616893-4a93-4f1a-b81a-8762abf1d8c6"; //cafe cafe //"feb2ea1a-d05b-4fa2-bc93-dfb9fdd4cb8f"; //geneva
        final String appId = "urn:aid:08bc7784-a262-4b97-a84c-348ba0f21c3f";
        //"891b396d-fe26-4339-a810-ff2af2e277ba"; opinion
        PoyntSdk sdk = PoyntSdk.builder().configure("config.properties").build();

        PoyntSdkBuilder builder2 = new PoyntSdkBuilder();
        Reader reader = new InputStreamReader(Main2.class.getClassLoader().getResourceAsStream("urn_aid_08bc7784-a262-4b97-a84c-348ba0f21c3f_publicprivatekey.pem"));
        builder2.configure(appId, reader);
        builder2.rebuild(sdk);

        Map<String, Object> claims = ((JsonWebTokenService)builder2.jwt).decode(sdk.getAccessToken());
        System.out.println("============= claims = " + claims.toString());
        //String businessId = (String)claims.get(PoyntSdkBuilder.POYNT_JWT_CLAIM_ORG);
        System.out.println("============= BUSINESS id=" + businessId);

        Business business = sdk.business().get(businessId);
        System.out.println("============= BUSINESS =" + business);

        System.out.println("============= BUSINESS USERS");
        List<BusinessUser> users = sdk.businessUser().getAll(businessId);
        System.out.println(users);

        System.out.println("============= STORES");
        ApiStore apiStore = new ApiStore(sdk);
        List<Store> stores = apiStore.getAll(businessId);
        System.out.println(stores);
        
        Store store = apiStore.get(businessId, "fb436567-f8e0-46c4-9309-348c4278745b");
        System.out.println(store);
        
        System.out.println("============= SUBSCRIPTIONS");
        ApiSubscription apiSubscription = new ApiSubscription(sdk, appId);
        ResourceList<Subscription> subscriptions = apiSubscription.getAllFromBusiness(businessId);
        System.out.println(subscriptions);
        for(Object subscription: subscriptions.getList()) {
            System.out.println("subscription=" + ((Map)subscription).toString());
        }


        //		System.out.println("=============CATALOG");
        //		CatalogWithProduct catalog = sdk.catalog().get(businessId, "675f0c80-6db8-4584-a444-6b213d0f4f66");
        //		System.out.println(catalog);
        //
        //		System.out.println("=============PRODUCT");
        //		Product product = sdk.product().get(businessId, "675f0c80-6db8-4584-a444-6b213d0f4f66");
        //		System.out.println(product);


        System.out.println("=============Done!");
    }
}
