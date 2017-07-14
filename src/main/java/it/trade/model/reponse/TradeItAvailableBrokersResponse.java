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

		@SerializedName("brokerInstruments")
		@Expose
		public List<BrokerInstrument> brokerInstruments = new ArrayList<>();

		public class BrokerInstrument {
			@SerializedName("instrument")
			@Expose
			public String instrument;

			@SerializedName("supportsAccountOverview")
			@Expose
			public boolean supportsAccountOverview;

			@SerializedName("supportsOrderCanceling")
			@Expose
			public boolean supportsOrderCanceling;

			@SerializedName("isFeatured")
			@Expose
			public boolean isFeatured;

			@SerializedName("supportsOrderStatus")
			@Expose
			public boolean supportsOrderStatus;

			@SerializedName("supportsPositions")
			@Expose
			public boolean supportsPositions;

			@SerializedName("supportsFxRates")
			@Expose
			public boolean supportsFxRates;

			@SerializedName("supportsTrading")
			@Expose
			public boolean supportsTrading;

			@SerializedName("supportsTransactionHistory")
			@Expose
			public boolean supportsTransactionHistory;

			@java.lang.Override
			public java.lang.String toString() {
				return "BrokerInstrument{" +
						"instrument='" + instrument + '\'' +
						", supportsAccountOverview=" + supportsAccountOverview +
						", supportsOrderCanceling=" + supportsOrderCanceling +
						", isFeatured=" + isFeatured +
						", supportsOrderStatus=" + supportsOrderStatus +
						", supportsPositions=" + supportsPositions +
						", supportsFxRates=" + supportsFxRates +
						", supportsTrading=" + supportsTrading +
						", supportsTransactionHistory=" + supportsTransactionHistory +
						'}';
			}
		}

		@java.lang.Override
		public java.lang.String toString() {
			return "Broker{" +
					"shortName='" + shortName + '\'' +
					", longName='" + longName + '\'' +
					", brokerInstruments=" + brokerInstruments +
					'}';
		}
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "TradeItAvailableBrokersResponse{" +
				"brokerList=" + brokerList +
				'}';
	}
}
