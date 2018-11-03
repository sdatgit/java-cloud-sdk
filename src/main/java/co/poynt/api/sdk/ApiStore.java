package co.poynt.api.sdk;

import java.util.List;


import co.poynt.api.model.Store;

public class ApiStore extends Api {

    public ApiStore(PoyntSdk sdk) {
        super(sdk, Constants.POYNT_API_HOST + Constants.API_STORES);
    }

    public List<Store> getAll(String businessId) {
        return this.getAllFromBusiness(Store.class, businessId);
    }

    public Store get(String businessId, String storeId) {
        return this.getFromBusiness(Store.class, businessId, storeId);
    }
}
