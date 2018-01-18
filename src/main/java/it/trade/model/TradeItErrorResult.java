package it.trade.model;

import com.google.gson.annotations.SerializedName;
import it.trade.model.reponse.TradeItErrorCode;

import java.util.Arrays;
import java.util.List;

public class TradeItErrorResult {

	@SerializedName("errorCode")
    protected TradeItErrorCode errorCode = null;

	@SerializedName("shortMessage")
	protected String shortMessage = null;

	@SerializedName("longMessages")
	protected List<String> longMessages = Arrays.asList("Something went wrong. Please try again.");

	@SerializedName("systemMessage")
	protected String systemMessage = "Unknown response sent from the server.";

	@SerializedName("httpCode")
	protected int httpCode = 200;

     public TradeItErrorResult(TradeItErrorCode errorCode, String shortMessage, List<String> longMessages) {
        this.errorCode = errorCode;
        this.shortMessage = shortMessage;
        this.longMessages = longMessages;
        this.systemMessage = null;
    }

    public TradeItErrorResult(int httpCode) {
        this.httpCode = httpCode;
        this.systemMessage = "error sending request to ems server";
    }

    public TradeItErrorResult(String title, String message) {
        this.shortMessage = title;
        this.longMessages = Arrays.asList(message);
        this.systemMessage = message;
        this.errorCode = TradeItErrorCode.SYSTEM_ERROR;
    }

    public TradeItErrorResult() {
    }

    public boolean requiresAuthentication() {
        return errorCode == TradeItErrorCode.SESSION_EXPIRED || errorCode == TradeItErrorCode.BROKER_ACCOUNT_ERROR;
    }

    public boolean requiresRelink() {
        return errorCode == TradeItErrorCode.BROKER_AUTHENTICATION_ERROR || errorCode == TradeItErrorCode.TOKEN_INVALID_OR_EXPIRED;
    }

    public boolean isConcurrentAuthenticationError() {
        return errorCode == TradeItErrorCode.CONCURRENT_AUTHENTICATION_ERROR;
    }

    public boolean isAccountLinkDelayedError() {
        return errorCode == TradeItErrorCode.BROKER_ACCOUNT_NOT_AVAILABLE;
    }

    public boolean isTooManyLoginAttemptsError() {
        return errorCode == TradeItErrorCode.TOO_MANY_LOGIN_ATTEMPTS;
    }

    public TradeItErrorCode getErrorCode() {
        return errorCode;
    }

    public String getShortMessage() {
        return shortMessage;
    }

    public List<String> getLongMessages() {
        return longMessages;
    }

    public String getSystemMessage() {
        return systemMessage;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setErrorCode(TradeItErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public void setShortMessage(String shortMessage) {
        this.shortMessage = shortMessage;
    }

    public void setLongMessages(List<String> longMessages) {
        this.longMessages = longMessages;
    }

    public void setSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    @Override
    public String toString() {
        return "TradeItErrorResult{" +
                "errorCode=" + errorCode +
                ", shortMessage='" + shortMessage + '\'' +
                ", longMessages=" + longMessages +
                ", systemMessage='" + systemMessage + '\'' +
                ", httpCode=" + httpCode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TradeItErrorResult that = (TradeItErrorResult) o;

        if (httpCode != that.httpCode) return false;
        if (errorCode != that.errorCode) return false;
        if (shortMessage != null ? !shortMessage.equals(that.shortMessage) : that.shortMessage != null) return false;
        if (longMessages != null ? !longMessages.equals(that.longMessages) : that.longMessages != null) return false;
        return systemMessage != null ? systemMessage.equals(that.systemMessage) : that.systemMessage == null;
    }

    @Override
    public int hashCode() {
        int result = errorCode != null ? errorCode.hashCode() : 0;
        result = 31 * result + (shortMessage != null ? shortMessage.hashCode() : 0);
        result = 31 * result + (longMessages != null ? longMessages.hashCode() : 0);
        result = 31 * result + (systemMessage != null ? systemMessage.hashCode() : 0);
        result = 31 * result + httpCode;
        return result;
    }
}
