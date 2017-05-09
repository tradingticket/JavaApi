package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TradeItAuthenticateResponse extends TradeItResponse {
    @SerializedName("accounts")
    @Expose
    public List<TradeItBrokerAccount> accounts = new ArrayList<TradeItBrokerAccount>();

    @SerializedName("challengeImage")
    @Expose
    public String challengeImage;

    @SerializedName("informationType")
    @Expose
    public String informationType;

    @SerializedName("securityQuestion")
    @Expose
    public String securityQuestion;

    @SerializedName("securityQuestionOptions")
    @Expose
    public List<String> securityQuestionOptions;

    @Override
    public String toString() {
        return "TradeItAuthenticateResponse{" +
                "accounts=" + accounts +
                ", challengeImage='" + challengeImage + '\'' +
                ", informationType='" + informationType + '\'' +
                ", securityQuestion='" + securityQuestion + '\'' +
                ", securityQuestionOptions=" + securityQuestionOptions +
                "}, " + super.toString();
    }
}