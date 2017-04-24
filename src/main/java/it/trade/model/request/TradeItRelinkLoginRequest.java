package it.trade.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItRelinkLoginRequest extends TradeItRequestWithKey {
    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("password")
    @Expose
    public String password;

    @SerializedName("broker")
    @Expose
    public String broker;

    @SerializedName("userId")
    @Expose
    public String userId;

    public TradeItRelinkLoginRequest(TradeItLinkedLogin linkedLogin, String brokerLoginId, String password) {
        this.id = brokerLoginId;
        this.password = password;
        this.broker = linkedLogin.broker;
        this.userId = linkedLogin.userId;
    }

    public TradeItRelinkLoginRequest(String brokerLoginId, String password, String broker, String linkedLoginUserId) {
        this.id = brokerLoginId;
        this.password = password;
        this.broker = broker;
        this.userId = linkedLoginUserId;
    }

    @Override
    public String toString() {
        return "TradeItRelinkLoginRequest{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", broker='" + broker + '\'' +
                ", userId='" + userId + '\'' +
                "}, " + super.toString();
    }
}
