package it.trade.model.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItRequestWithKey {
    @SerializedName("apiKey")
    @Expose
    public String apiKey;

    public TradeItRequestWithKey(String apiKey) {
        this.apiKey = apiKey;
    }

    protected TradeItRequestWithKey() {}

    @Override
    public String toString() {
        return "TradeItRequestWithKey{" +
                "apiKey='" + apiKey + '\'' +
                '}';
    }
}
