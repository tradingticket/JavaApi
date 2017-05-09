package it.trade.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TradeItAnswerSecurityQuestionRequest extends TradeItRequestWithSession {
    @SerializedName("securityAnswer")
    @Expose
    public String securityAnswer;

    @SerializedName("srv")
    @Expose
    public String serverUuid;

    public TradeItAnswerSecurityQuestionRequest(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    @Override
    public String toString() {
        return "TradeItAnswerSecurityQuestionRequest{" +
                ", securityAnswer='" + securityAnswer + '\'' +
                ", srv='" + serverUuid + '\'' +
                "}, " + super.toString();
    }
}
