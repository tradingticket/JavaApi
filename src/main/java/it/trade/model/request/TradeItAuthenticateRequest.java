package it.trade.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItAuthenticateRequest extends TradeItRequestWithKey {
    @SerializedName("userToken")
    @Expose
    public String userToken;

    @SerializedName("userId")
    @Expose
    public String userId;

    @SerializedName("srv")
    @Expose
    public String serverUuid;

    public TradeItAuthenticateRequest(TradeItLinkedLogin linkedLogin) {
        this.userToken = linkedLogin.userToken;
        this.userId = linkedLogin.userId;
    }

    public TradeItAuthenticateRequest(String apiKey, TradeItLinkedLogin linkedLogin) {
        super(apiKey);
        this.userToken = linkedLogin.userToken;
        this.userId = linkedLogin.userId;
    }

    private TradeItAuthenticateRequest() {}

    @Override
    public String toString() {
        return "TradeItAuthenticateRequest{" +
                "userToken='" + userToken + '\'' +
                ", userId='" + userId + '\'' +
                ", srv='" + serverUuid + '\'' +
                "}, " + super.toString();
    }
}