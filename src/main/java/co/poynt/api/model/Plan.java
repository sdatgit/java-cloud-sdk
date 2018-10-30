package co.poynt.api.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
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
                     "amounts",
                     "interval",
                     "trialPeriodDays",
                     "startPolicy",
                     "cancelPolicy",
                     "appId",
                     "amount",
                     "planId",
                     "scope",
                     "description",
                     "status",
                     "name"
})
public class Plan {

    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("updatedAt")
    private String updatedAt;
    @JsonProperty("amounts")
    @Valid
    private List<Amount> amounts = null;
    @JsonProperty("interval")
    private String interval;
    @JsonProperty("trialPeriodDays")
    private Integer trialPeriodDays;
    @JsonProperty("startPolicy")
    private String startPolicy;
    @JsonProperty("cancelPolicy")
    private String cancelPolicy;
    @JsonProperty("appId")
    private String appId;
    @JsonProperty("amount")
    private Integer amount;
    @JsonProperty("planId")
    private String planId;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("description")
    private String description;
    @JsonProperty("status")
    private String status;
    @JsonProperty("name")
    private String name;
    @JsonIgnore
    @Valid
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

    @JsonProperty("amounts")
    public List<Amount> getAmounts() {
        return amounts;
    }

    @JsonProperty("amounts")
    public void setAmounts(List<Amount> amounts) {
        this.amounts = amounts;
    }

    @JsonProperty("interval")
    public String getInterval() {
        return interval;
    }

    @JsonProperty("interval")
    public void setInterval(String interval) {
        this.interval = interval;
    }

    @JsonProperty("trialPeriodDays")
    public Integer getTrialPeriodDays() {
        return trialPeriodDays;
    }

    @JsonProperty("trialPeriodDays")
    public void setTrialPeriodDays(Integer trialPeriodDays) {
        this.trialPeriodDays = trialPeriodDays;
    }

    @JsonProperty("startPolicy")
    public String getStartPolicy() {
        return startPolicy;
    }

    @JsonProperty("startPolicy")
    public void setStartPolicy(String startPolicy) {
        this.startPolicy = startPolicy;
    }

    @JsonProperty("cancelPolicy")
    public String getCancelPolicy() {
        return cancelPolicy;
    }

    @JsonProperty("cancelPolicy")
    public void setCancelPolicy(String cancelPolicy) {
        this.cancelPolicy = cancelPolicy;
    }

    @JsonProperty("appId")
    public String getAppId() {
        return appId;
    }

    @JsonProperty("appId")
    public void setAppId(String appId) {
        this.appId = appId;
    }

    @JsonProperty("amount")
    public Integer getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @JsonProperty("planId")
    public String getPlanId() {
        return planId;
    }

    @JsonProperty("planId")
    public void setPlanId(String planId) {
        this.planId = planId;
    }

    @JsonProperty("scope")
    public String getScope() {
        return scope;
    }

    @JsonProperty("scope")
    public void setScope(String scope) {
        this.scope = scope;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Plan [createdAt=");
        builder.append(createdAt);
        builder.append(", updatedAt=");
        builder.append(updatedAt);
        builder.append(", amounts=");
        builder.append(amounts);
        builder.append(", interval=");
        builder.append(interval);
        builder.append(", trialPeriodDays=");
        builder.append(trialPeriodDays);
        builder.append(", startPolicy=");
        builder.append(startPolicy);
        builder.append(", cancelPolicy=");
        builder.append(cancelPolicy);
        builder.append(", appId=");
        builder.append(appId);
        builder.append(", amount=");
        builder.append(amount);
        builder.append(", planId=");
        builder.append(planId);
        builder.append(", scope=");
        builder.append(scope);
        builder.append(", description=");
        builder.append(description);
        builder.append(", status=");
        builder.append(status);
        builder.append(", name=");
        builder.append(name);
        builder.append(", additionalProperties=");
        builder.append(additionalProperties);
        builder.append("]");
        return builder.toString();
    }

}