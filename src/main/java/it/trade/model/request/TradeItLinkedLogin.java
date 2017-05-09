package it.trade.model.request;

import com.google.gson.annotations.SerializedName;
import it.trade.model.reponse.TradeItLinkLoginResponse;
import it.trade.model.reponse.TradeItOAuthAccessTokenResponse;

public class TradeItLinkedLogin {
    @SerializedName("label")
    public String label = "";

    @SerializedName("broker")
    public String broker = "";

    @SerializedName("userToken")
    public String userToken = "";

    @SerializedName("userId")
    public String userId = "";

    public TradeItLinkedLogin(String broker, String userId, String userToken) {
        this.broker = broker;
        this.userId = userId;
        this.userToken = userToken;
    }

    public TradeItLinkedLogin(TradeItLinkLoginRequest linkLoginRequest, TradeItLinkLoginResponse linkLoginResponse) {
        this.broker = linkLoginRequest.broker;
        this.userId = linkLoginResponse.userId;
        this.userToken = linkLoginResponse.userToken;
    }

    public TradeItLinkedLogin(TradeItOAuthAccessTokenRequest oAuthAccessTokenRequest,
                              TradeItOAuthAccessTokenResponse oAuthAccessTokenResponse) {
        this.broker = oAuthAccessTokenResponse.broker;
        this.userId = oAuthAccessTokenResponse.userId;
        this.userToken = oAuthAccessTokenResponse.userToken;
    }

    protected TradeItLinkedLogin() {}

    public void update(TradeItLinkLoginResponse linkLoginResponse) {
        this.userId = linkLoginResponse.userId;
        this.userToken = linkLoginResponse.userToken;
    }

    @Override
    public String toString() {
        return "TradeItLinkedLogin{" +
                "userToken='" + userToken + '\'' +
                ", userId='" + userId + '\'' +
                ", broker='" + broker + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
