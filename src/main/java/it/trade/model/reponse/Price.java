package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price {
        @SerializedName("type")
        @Expose
        public String type;

        @SerializedName("limitPrice")
        @Expose
        public Integer limitPrice;

        @SerializedName("stopPrice")
        @Expose
        public Integer stopPrice;

        @SerializedName("last")
        @Expose
        public Double last;

        @SerializedName("bid")
        @Expose
        public Double bid;

        @SerializedName("ask")
        @Expose
        public Double ask;

        @SerializedName("timestamp")
        @Expose
        public String timestamp;

        @Override
        public String toString() {
            return "Price{" +
                    "type='" + type + '\'' +
                    ", limitPrice=" + limitPrice +
                    ", stopPrice=" + stopPrice +
                    ", last=" + last +
                    ", bid=" + bid +
                    ", ask=" + ask +
                    ", timestamp='" + timestamp + '\'' +
                    '}';
        }
    }