package it.trade.model.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TradeItAvailableBrokersResponse extends TradeItResponse {
    @SerializedName("brokerList")
    @Expose
    public List<Broker> brokerList = new ArrayList<>();

    public class Broker {
        @SerializedName("shortName")
        @Expose
        public String shortName;

        @SerializedName("longName")
        @Expose
        public String longName;

        @Override
        public String toString() {
            return "Broker{" +
                    "shortName='" + shortName + '\'' +
                    ", longName='" + longName + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TradeItAvailableBrokersResponse{" +
                "brokerList=" + brokerList +
                "}, " + super.toString();
    }
}
