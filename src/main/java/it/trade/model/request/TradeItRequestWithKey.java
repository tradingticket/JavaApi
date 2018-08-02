package it.trade.model.request;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItRequestWithKey {
    public static String API_KEY; // TODO: DELETE THIS

    @SerializedName("apiKey")
    @Expose
    public String apiKey;

    public TradeItRequestWithKey() {
        this.apiKey = API_KEY;
    }

    public TradeItRequestWithKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String toString() {
        return "TradeItRequestWithKey{" +
                "apiKey='" + apiKey + '\'' +
                '}';
    }
}
