package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItProxyVoteUrlResponse extends TradeItResponse {
    @SerializedName("proxyVoteUrl")
    @Expose
    public String proxyVoteUrl;
}
