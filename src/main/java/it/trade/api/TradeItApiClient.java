package it.trade.api;


import com.google.gson.annotations.SerializedName;
import it.trade.model.callback.AuthenticationCallback;
import it.trade.model.callback.TradeItCallback;
import it.trade.model.reponse.*;
import it.trade.model.reponse.TradeItAvailableBrokersResponse.Broker;
import it.trade.model.request.*;
import okhttp3.Interceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;
import java.util.UUID;

public class TradeItApiClient {
    protected transient StatelessTradeItApiClient statelessTradeItApiClient;

    @SerializedName("requestInterceptor")
    protected Interceptor requestInterceptor;
    @SerializedName("serverUuid")
    protected String serverUuid;
    @SerializedName("sessionToken")
    protected String sessionToken;
    @SerializedName("environment")
    protected TradeItEnvironment environment;
    @SerializedName("apiKey")
    protected String apiKey;

    public TradeItApiClient(String apiKey, TradeItEnvironment environment) {
        this(apiKey, environment, null, false);
    }

    public TradeItApiClient(String apiKey, TradeItEnvironment environment, boolean forceTLS12) {
        this(apiKey, environment, null, forceTLS12);
    }

    /**
     * Use this constructor if you want to add your custom headers to each request
     */
    public TradeItApiClient(String apiKey, TradeItEnvironment environment, Interceptor requestInterceptor) {
        this(apiKey, environment, requestInterceptor, false);
    }

    public TradeItApiClient(
        String apiKey,
        TradeItEnvironment environment,
        Interceptor requestInterceptor,
        boolean forceTLS12
    ) {
        this.apiKey = apiKey;
        this.environment = environment;
        this.requestInterceptor = requestInterceptor;
        this.statelessTradeItApiClient = createStatelessTradeItApiClient(environment, requestInterceptor, forceTLS12);
    }

    protected StatelessTradeItApiClient createStatelessTradeItApiClient(
        TradeItEnvironment environment,
        final Interceptor requestInterceptor,
        boolean forceTLS12
    ) {
        return new StatelessTradeItApiClient(environment, requestInterceptor, forceTLS12);
    }

    protected TradeItApiClient(String apiKey, StatelessTradeItApiClient statelessTradeItApiClient) { //used for unit tests
        this.apiKey = apiKey;
        this.statelessTradeItApiClient = statelessTradeItApiClient;
    }

    protected TradeItApiClient() {
    }

    private void injectSession(TradeItRequestWithSession request) {
        request.sessionToken = sessionToken;
    }

    public void getAvailableBrokers(final TradeItCallback<List<Broker>> callback) {
        this.statelessTradeItApiClient.getAvailableBrokers(this.apiKey, callback);
    }

    public void getOAuthLoginPopupUrlForMobile(
        String broker,
        String deepLinkCallback,
        final TradeItCallback<String> callback
    ) {
        this.statelessTradeItApiClient.getOAuthLoginPopupUrlForMobile(this.apiKey, broker, deepLinkCallback, callback);
    }

    public void getOAuthLoginPopupUrlForWebApp(String broker, final TradeItCallback<String> callback) {
        this.statelessTradeItApiClient.getOAuthLoginPopupUrlForWebApp(this.apiKey, broker, callback);
    }

    public void getOAuthLoginPopupUrlForTokenUpdate(
        String broker,
        String userId,
        String userToken,
        String deepLinkCallback,
        final TradeItCallback<String> callback
    ) {
        this.statelessTradeItApiClient.getOAuthLoginPopupUrlForTokenUpdate(
            this.apiKey,
            broker,
            userId,
            userToken,
            deepLinkCallback,
            callback
        );
    }

    public void linkBrokerWithOauthVerifier(String oAuthVerifier, final TradeItCallback<TradeItLinkedLogin> callback) {
        this.statelessTradeItApiClient.linkBrokerWithOauthVerifier(this.apiKey, oAuthVerifier, callback);
    }

    public void unlinkBrokerAccount(TradeItLinkedLogin linkedLogin, final TradeItCallback callback) {
        this.statelessTradeItApiClient.unlinkBrokerAccount(this.apiKey, linkedLogin, callback);
    }

    public void authenticate(
        TradeItLinkedLogin linkedLogin,
        final AuthenticationCallback<TradeItAuthenticateResponse, ?> callback
    ) {
        if (serverUuid == null) {
            serverUuid = UUID.randomUUID().toString();
        }

        TradeItAuthenticateRequest authenticateRequest = new TradeItAuthenticateRequest(this.apiKey, linkedLogin);
        this.statelessTradeItApiClient.tradeItApi.authenticate(authenticateRequest).enqueue(
            new Callback<TradeItAuthenticateResponse>() {
                public void onResponse(
                    Call<TradeItAuthenticateResponse> call,
                    Response<TradeItAuthenticateResponse> response
                ) {
                    if (response.isSuccessful()) {
                        TradeItAuthenticateResponse authenticateResponse = response.body();
                        if (authenticateResponse.status == TradeItResponseStatus.SUCCESS
                            || authenticateResponse.status == TradeItResponseStatus.INFORMATION_NEEDED) {
                            sessionToken = authenticateResponse.sessionToken;
                        }
                    }
                    callback.onResponse(call, response);
                }

                public void onFailure(Call<TradeItAuthenticateResponse> call, Throwable t) {
                    callback.onFailure(call, t);
                }
            }
        );
    }

    public void answerSecurityQuestion(
        TradeItAnswerSecurityQuestionRequest request,
        final Callback<TradeItAuthenticateResponse> callback
    ) {
        request.serverUuid = serverUuid;
        injectSession(request);
        this.statelessTradeItApiClient.tradeItApi.answerSecurityQuestion(request).enqueue(
            new Callback<TradeItAuthenticateResponse>() {
                public void onResponse(Call<TradeItAuthenticateResponse> call, Response<TradeItAuthenticateResponse> response) {
                    if (response.isSuccessful()) {
                        TradeItAuthenticateResponse authenticateResponse = response.body();
                        if (authenticateResponse.status == TradeItResponseStatus.SUCCESS
                            || authenticateResponse.status == TradeItResponseStatus.INFORMATION_NEEDED) {
                            sessionToken = authenticateResponse.sessionToken;
                        }
                    }
                    callback.onResponse(call, response);
                }

                public void onFailure(Call<TradeItAuthenticateResponse> call, Throwable t) {
                    callback.onFailure(call, t);
                }
            }
        );

    }

    public void keepSessionAlive(final TradeItCallback<TradeItResponse> callback) {
        this.statelessTradeItApiClient.keepSessionAlive(sessionToken, callback);
    }

    public void closeSession(final TradeItCallback<TradeItResponse> callback) {
        this.statelessTradeItApiClient.closeSession(sessionToken, callback);
    }

    public void previewStockOrEtfOrder(
        TradeItPreviewStockOrEtfOrderRequest request,
        final TradeItCallback<TradeItPreviewStockOrEtfOrderResponse> callback
    ) {
        injectSession(request);
        this.statelessTradeItApiClient.previewStockOrEtfOrder(request, callback);
    }

    public void placeStockOrEtfOrder(String orderId, final TradeItCallback<TradeItPlaceStockOrEtfOrderResponse> callback) {
        TradeItPlaceStockOrEtfOrderRequest request = new TradeItPlaceStockOrEtfOrderRequest(orderId);
        injectSession(request);
        this.statelessTradeItApiClient.placeStockOrEtfOrder(request, callback);
    }

    public void getAccountOverview(String accountNumber, final TradeItCallback<TradeItAccountOverviewResponse> callback) {
        TradeItGetAccountOverviewRequest request = new TradeItGetAccountOverviewRequest(accountNumber);
        injectSession(request);
        this.statelessTradeItApiClient.getAccountOverview(request, callback);
    }

    public void getPositions(String accountNumber, final TradeItCallback<List<TradeItPosition>> callback) {
        TradeItGetPositionsRequest request = new TradeItGetPositionsRequest(accountNumber, null);
        injectSession(request);
        this.statelessTradeItApiClient.getPositions(request, callback);
    }

    public void getAllOrderStatus(String accountNumber, final TradeItCallback<List<OrderStatusDetails>> callback) {
        TradeItGetAllOrderStatusRequest request = new TradeItGetAllOrderStatusRequest(accountNumber);
        injectSession(request);
        this.statelessTradeItApiClient.getAllOrderStatus(request, callback);
    }

    public void getSingleOrderStatus(
        String accountNumber,
        String orderNumber,
        final TradeItCallback<OrderStatusDetails> callback
    ) {
        TradeItGetSingleOrderStatusRequest request = new TradeItGetSingleOrderStatusRequest(accountNumber, orderNumber);
        injectSession(request);
        this.statelessTradeItApiClient.getSingleOrderStatus(request, callback);
    }

    public void cancelOrder(String acccountNumber, String orderNumber, final TradeItCallback<OrderStatusDetails> callback) {
        TradeItCancelOrderRequest request = new TradeItCancelOrderRequest(acccountNumber, orderNumber);
        injectSession(request);
        this.statelessTradeItApiClient.cancelOrder(request, callback);
    }

    public void getAllTransactionsHistory(String accountNumber, final TradeItCallback<List<TransactionDetails>> callback) {
        TradeItGetAllTransactionsHistoryRequest request = new TradeItGetAllTransactionsHistoryRequest(accountNumber);
        injectSession(request);
        this.statelessTradeItApiClient.getAllTransactionsHistory(request, callback);
    }

    public void previewCryptoOrder(
            TradeItPreviewCryptoOrderRequest request,
            final TradeItCallback<TradeItPreviewCryptoOrderResponse> callback
    ) {
        injectSession(request);
        this.statelessTradeItApiClient.previewCryptoOrder(request, callback);
    }

    public void placeCryptoOrder(String orderId, TradeItCallback<TradeItPlaceCryptoOrderResponse> callback) {
        TradeItPlaceCryptoOrderRequest request = new TradeItPlaceCryptoOrderRequest(this.sessionToken, orderId);
        this.statelessTradeItApiClient.placeCryptoOrder(request, callback);
    }

    public void getCryptoQuote(String accountNumber, String pair, TradeItCallback<TradeItCryptoQuoteResponse> callback) {
        TradeItCryptoQuoteRequest request = new TradeItCryptoQuoteRequest(
                this.sessionToken,
                accountNumber,
                pair
        );
        this.statelessTradeItApiClient.getCryptoQuote(request, callback);
    }

    public void getProxyVoteUrl(
        String accountNumber,
        String symbol,
        TradeItCallback<TradeItProxyVoteUrlResponse> callback
    ) {
        TradeItProxyVoteUrlRequest request = new TradeItProxyVoteUrlRequest(
            this.sessionToken,
            accountNumber,
            symbol
        );
        this.statelessTradeItApiClient.getProxyVoteUrl(request, callback);
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    private class PassthroughCallback<T> implements Callback<T> {
        Callback<T> callback;

        PassthroughCallback(Callback<T> callback) {
            this.callback = callback;
        }

        public void onResponse(Call<T> call, Response<T> response) {
            callback.onResponse(call, response);
        }

        public void onFailure(Call<T> call, Throwable t) {
            callback.onFailure(call, t);
        }
    }

    public String getApiKey() {
        return apiKey;
    }

    public TradeItEnvironment getEnvironment() {
        return this.environment;
    }
}