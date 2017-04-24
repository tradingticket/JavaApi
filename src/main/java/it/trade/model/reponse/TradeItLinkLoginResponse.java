package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItLinkLoginResponse extends TradeItResponse {
    @SerializedName("userToken")
    @Expose
    public String userToken;

    @SerializedName("userId")
    @Expose
    public String userId;

    @Override
    public String toString() {
        return "TradeItLinkLoginResponse {" +
                "userToken='" + userToken + '\'' +
                ", userId='" + userId + '\'' +
                "}, " + super.toString();
    }
}