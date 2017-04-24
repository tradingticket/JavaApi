package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItOAuthAccessTokenResponse extends TradeItLinkLoginResponse {

    @SerializedName("broker")
    @Expose
    public String broker;

    @SerializedName("activationTime")
    @Expose
    public String activationTime;
}
