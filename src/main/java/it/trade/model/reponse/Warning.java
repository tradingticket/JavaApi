package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Warning {
    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("requiresAcknowledgement")
    @Expose
    public boolean requiresAcknowledgement;

    @SerializedName("links")
    @Expose
    public List<WarningLink> links = new ArrayList<>();
}
