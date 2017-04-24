package it.trade.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItOAuthLoginPopupUrlForWebAppRequest extends TradeItRequestWithKey {

    @SerializedName("broker")
    @Expose
    public String broker;


    public TradeItOAuthLoginPopupUrlForWebAppRequest(String broker) {
        this.broker = broker;
    }

    @Override
    public String toString() {
        return "TradeItOAuthLoginPopupUrlForWebAppRequest{" +
                "broker='" + broker +
                '}';
    }
}
