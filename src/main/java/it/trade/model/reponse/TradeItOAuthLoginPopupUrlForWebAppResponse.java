package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItOAuthLoginPopupUrlForWebAppResponse extends TradeItResponse {
    @SerializedName("oAuthURL")
    @Expose
    public String oAuthURL;
}
