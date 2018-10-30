package co.poynt.api.sdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiPlan extends ApiBilling {

    static final Logger logger = LoggerFactory.getLogger(ApiPlan.class);

    public static final String API_PLANS = "/apps/{appId}/plans";

    public ApiPlan(PoyntSdk sdk, String appId) {

        super(sdk, Constants.POYNT_BILLING_API_HOST + API_PLANS.replace("{appId}", appId));

    }
    /**
     * {
        "list": [{
            "description": "Wifi plan",
            "scope": "STORE",
            "createdAt": "2018-10-23T19:18:25Z",
            "updatedAt": "2018-10-23T19:18:25Z",
            "amounts": [{
                "country": "US",
                "currency": "USD",
                "value": 100
            }],
            "interval": "MONTH",
            "trialPeriodDays": 0,
            "startPolicy": "IMMEDIATE",
            "cancelPolicy": "BILLING",
            "appId": "urn:aid:08bc7784-a262-4b97-a84c-348ba0f21c3f",
            "amount": 100,
            "planId": "21e8ad2b-0fde-43b6-bcd9-276855ed092c",
            "status": "ACTIVE",
            "name": "Test WiFi"
        }, {
            "description": "Use this test plan for testing",
            "scope": "STORE",
            "createdAt": "2018-10-23T19:18:21Z",
            "updatedAt": "2018-10-23T19:18:21Z",
            "amounts": [{
                "country": "US",
                "currency": "USD",
                "value": 100
            }],
            "interval": "MONTH",
            "trialPeriodDays": 0,
            "startPolicy": "IMMEDIATE",
            "cancelPolicy": "BILLING",
            "appId": "urn:aid:08bc7784-a262-4b97-a84c-348ba0f21c3f",
            "amount": 100,
            "planId": "9f89bac6-732d-4fdf-b8f5-9f255e246489",
            "status": "ACTIVE",
            "name": "Test Email"
        }],
        "start": 0,
        "total": 2,
        "count": 2
     */
}
