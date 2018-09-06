package co.poynt.api.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
                     "createdAt",
                     "updatedAt",
                     "startAt",
                     "businessId",
                     "appId",
                     "subscriptionId",
                     "phase",
                     "planId",
                     "bundleId",
                     "status"
})
public class Subscription {

    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("updatedAt")
    private String updatedAt;
    @JsonProperty("startAt")
    private String startAt;
    @JsonProperty("businessId")
    private String businessId;
    @JsonProperty("appId")
    private String appId;
    @JsonProperty("subscriptionId")
    private String subscriptionId;
    @JsonProperty("phase")
    private String phase;
    @JsonProperty("planId")
    private String planId;
    @JsonProperty("bundleId")
    private String bundleId;
    @JsonProperty("status")
    private String status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("updatedAt")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updatedAt")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("startAt")
    public String getStartAt() {
        return startAt;
    }

    @JsonProperty("startAt")
    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    @JsonProperty("businessId")
    public String getBusinessId() {
        return businessId;
    }

    @JsonProperty("businessId")
    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    @JsonProperty("appId")
    public String getAppId() {
        return appId;
    }

    @JsonProperty("appId")
    public void setAppId(String appId) {
        this.appId = appId;
    }

    @JsonProperty("subscriptionId")
    public String getSubscriptionId() {
        return subscriptionId;
    }

    @JsonProperty("subscriptionId")
    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    @JsonProperty("phase")
    public String getPhase() {
        return phase;
    }

    @JsonProperty("phase")
    public void setPhase(String phase) {
        this.phase = phase;
    }

    @JsonProperty("planId")
    public String getPlanId() {
        return planId;
    }

    @JsonProperty("planId")
    public void setPlanId(String planId) {
        this.planId = planId;
    }

    @JsonProperty("bundleId")
    public String getBundleId() {
        return bundleId;
    }

    @JsonProperty("bundleId")
    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}