package it.trade.model.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItOAuthLoginPopupUrlForMobileRequest extends TradeItOAuthLoginPopupUrlForWebAppRequest {

    @SerializedName("interAppAddressCallback")
    @Expose
    public String interAppAddressCallback;


    public TradeItOAuthLoginPopupUrlForMobileRequest(String broker, String interAppAddressCallback) {
        super(broker);
        this.interAppAddressCallback = interAppAddressCallback;
    }

    public TradeItOAuthLoginPopupUrlForMobileRequest(String apiKey, String broker, String interAppAddressCallback) {
        super(apiKey, broker);
        this.interAppAddressCallback = interAppAddressCallback;
    }

    @Override
    public String toString() {
        return "TradeItOAuthLoginPopupUrlForMobileRequest{" +
                "broker='" + broker + '\'' +
                ", interAppAddressCallback='" + interAppAddressCallback + '\'' +
                '}';
    }
}
