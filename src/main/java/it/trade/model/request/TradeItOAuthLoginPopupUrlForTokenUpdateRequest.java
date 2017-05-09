package it.trade.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItOAuthLoginPopupUrlForTokenUpdateRequest extends TradeItOAuthLoginPopupUrlForMobileRequest {

    @SerializedName("userId")
    @Expose
    public String userId;

    public TradeItOAuthLoginPopupUrlForTokenUpdateRequest(String broker, String interAppAddressCallback, String userId) {
        super(broker, interAppAddressCallback);
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "TradeItOAuthLoginPopupUrlForTokenUpdateRequest{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
