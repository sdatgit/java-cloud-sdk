package co.poynt.api.sdk;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.UUID;

import co.poynt.api.model.Hook;
import co.poynt.api.model.Plan;
import co.poynt.api.model.ResourceList;

/**
 * Utility to register webhook for givne app
 * @author sanjay
 *
 */
public class RegisterWebhook {
    public static void main(String[] args) throws FileNotFoundException {

        final String businessId = "6dc5a48d-8f99-41e9-b716-b3c564f0711c"; //colligso
        final String appId = "urn:aid:f3bb639a-2073-4928-9d2d-6ebf34a80f04";
        final String webhookUrl = "https://stage.colligso.com/poynt/webhook";
        
        PoyntSdk sdk = PoyntSdk.builder().configure("config.properties").build();

        PoyntSdkBuilder builder2 = new PoyntSdkBuilder();
        Reader reader = new InputStreamReader(RegisterWebhook.class.getClassLoader().getResourceAsStream("urn_aid_f3bb639a-2073-4928-9d2d-6ebf34a80f04_publicprivatekey.pem"));

        builder2.configure(appId, reader);
        builder2.rebuild(sdk);

        System.out.println("============= Plans");
        ApiBilling apiPlan = new ApiPlan(sdk, appId);
        ResourceList<Plan> plans = apiPlan.getAll();
        System.out.println(plans);
        for (Object plan: plans.getList()) {
            System.out.println("plan=" + plan.toString());
        }


        System.out.println("=============Webhooks");
        Hook hook = new Hook();
        hook.setApplicationId(sdk.getConfig().getAppId());
        hook.setBusinessId(UUID.fromString("11616893-4a93-4f1a-b81a-8762abf1d8c6")); //cafe cafe 
        hook.setDeliveryUrl(webhookUrl);
        hook.setEventTypes(Arrays.asList(new String[] { "ORDER_COMPLETED"}));

        // Poynt will use the secret below to generate a signature using
        // HmacSHA1 of the webhook payload
        // The signature will be send as an http header called
        // "poynt-webhook-signature".
        hook.setSecret("my-shared-secret-with-poynt");
        Hook resHook = sdk.webhook().register(hook);
        System.out.println("Hook response=" + resHook.toString());
        
        
        hook = new Hook();
        hook.setApplicationId(sdk.getConfig().getAppId());
        hook.setBusinessId(UUID.fromString(businessId)); //orgid of Colligso
        hook.setDeliveryUrl(webhookUrl);
        hook.setEventTypes(Arrays.asList(new String[] { "APPLICATION_SUBSCRIPTION_START", "APPLICATION_SUBSCRIPTION_END"}));

        // Poynt will use the secret below to generate a signature using
        // HmacSHA1 of the webhook payload
        // The signature will be send as an http header called
        // "poynt-webhook-signature".
        hook.setSecret("my-shared-secret-with-poynt");
        resHook = sdk.webhook().register(hook);
        System.out.println("=============Done!");
    }
}
