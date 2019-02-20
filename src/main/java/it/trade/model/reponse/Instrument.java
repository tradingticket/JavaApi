package it.trade.model.reponse;

import com.google.gson.annotations.SerializedName;


public enum Instrument {

    @SerializedName("EQUITIES")
    EQUITIES,
    @SerializedName("FX")
    FX,
    @SerializedName("OPTIONS")
    OPTIONS,
    @SerializedName("CRYPTO")
    CRYPTO,
    UNKNOWN;
}