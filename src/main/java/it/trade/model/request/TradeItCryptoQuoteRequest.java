package it.trade.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItCryptoQuoteRequest extends TradeItRequestWithSession {

    @SerializedName("accountNumber")
    @Expose
    String accountNumber;

    @SerializedName("pair")
    @Expose
    String pair;

    public TradeItCryptoQuoteRequest(String sessionToken, String accountNumber, String pair) {
        this.sessionToken = sessionToken;
        this.accountNumber = accountNumber;
        this.pair = pair;
    }

    public TradeItCryptoQuoteRequest() {}
}
