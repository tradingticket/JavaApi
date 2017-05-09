package it.trade.model.reponse;

import com.google.gson.annotations.SerializedName;

public enum TradeItResponseStatus {
    @SerializedName("SUCCESS")
    SUCCESS,

    @SerializedName("ERROR")
    ERROR,

    @SerializedName("INFORMATION_NEEDED")
    INFORMATION_NEEDED,
    
    @SerializedName("REVIEW_ORDER")
    REVIEW_ORDER;

    private String status;

    private TradeItResponseStatus() {
        this.status = status;
    }

    public String value() {
        return status;
    }
}
