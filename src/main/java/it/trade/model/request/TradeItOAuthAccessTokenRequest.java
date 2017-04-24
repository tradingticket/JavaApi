package it.trade.model.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItOAuthAccessTokenRequest extends TradeItRequestWithKey {

    @SerializedName("oAuthVerifier")
    @Expose
    public String oAuthVerifier;

    public TradeItOAuthAccessTokenRequest(String oAuthVerifier) {
        this.oAuthVerifier = oAuthVerifier;
    }

    @Override
    public String toString() {
        return "TradeItOAuthAccessTokenRequest{" +
                "oAuthVerifier='" + oAuthVerifier + '\'' +
                '}';
    }
}
