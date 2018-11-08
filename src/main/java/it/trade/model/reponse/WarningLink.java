package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WarningLink {
    @SerializedName("label")
    @Expose
    public String label;

    @SerializedName("url")
    @Expose
    public String url;
}
