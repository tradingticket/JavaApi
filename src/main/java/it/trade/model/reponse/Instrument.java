package it.trade.model.reponse;

import com.google.gson.annotations.SerializedName;


public enum Instrument {

	@SerializedName("equities")
	EQUITIES,
	@SerializedName("fx")
	FX,
	@SerializedName("options")
	OPTIONS,
	UNKNOWN;
}