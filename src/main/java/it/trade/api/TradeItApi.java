package it.trade.api;


import it.trade.model.reponse.*;
import it.trade.model.request.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TradeItApi {
    @POST("/api/v1/user/getOAuthLoginPopupUrlForWebApp")
    Call<TradeItOAuthLoginPopupUrlForWebAppResponse> getOAuthLoginPopupUrlForWebApp(@Body TradeItOAuthLoginPopupUrlForWebAppRequest request);

    @POST("/api/v1/user/getOAuthLoginPopupUrlForMobile")
    Call<TradeItOAuthLoginPopupUrlForMobileResponse> getOAuthLoginPopupUrlForMobile(@Body TradeItOAuthLoginPopupUrlForMobileRequest request);

    @POST("/api/v1/user/getOAuthLoginPopupURLForTokenUpdate")
    Call<TradeItOAuthLoginPopupUrlForTokenUpdateResponse> getOAuthLoginPopupURLForTokenUpdate(@Body TradeItOAuthLoginPopupUrlForTokenUpdateRequest request);

    @POST("/api/v1/user/getOAuthAccessToken")
    Call<TradeItOAuthAccessTokenResponse> getOAuthAccessToken(@Body TradeItOAuthAccessTokenRequest request);

    @POST("/api/v1/user/oAuthLink")
    Call<TradeItLinkLoginResponse> linkLogin(@Body TradeItLinkLoginRequest request);

    @POST("/api/v1/user/oAuthUpdate")
    Call<TradeItLinkLoginResponse> relinkLogin(@Body TradeItRelinkLoginRequest request);

    @POST("/api/v1/preference/getStocksOrEtfsBrokerList")
    Call<TradeItAvailableBrokersResponse> getAvailableBrokers(@Body TradeItRequestWithKey request);

    @POST("/api/v1/user/oAuthDelete")
    Call<TradeItResponse> unlinkLogin(@Body TradeItUnlinkLoginRequest request);

    @POST("/api/v1/user/authenticate")
    Call<TradeItAuthenticateResponse> authenticate(@Body TradeItAuthenticateRequest request);

    @POST("/api/v1/user/closeSession")
    Call<TradeItResponse> closeSession(@Body TradeItRequestWithSession request);

    @POST("/api/v1/user/answerSecurityQuestion")
    Call<TradeItAuthenticateResponse> answerSecurityQuestion(@Body TradeItAnswerSecurityQuestionRequest request);

    @POST("/api/v1/user/keepSessionAlive")
    Call<TradeItResponse> keepSessionAlive(@Body TradeItRequestWithSession request);

    @POST("/api/v1/order/previewStockOrEtfOrder")
    Call<TradeItPreviewStockOrEtfOrderResponse> previewStockOrEtfOrder(@Body TradeItPreviewStockOrEtfOrderRequest request);

    @POST("/api/v1/order/placeStockOrEtfOrder")
    Call<TradeItPlaceStockOrEtfOrderResponse> placeStockOrEtfOrder(@Body TradeItPlaceStockOrEtfOrderRequest request);

    @POST("/api/v1/balance/getAccountOverview")
    Call<TradeItGetAccountOverviewResponse> getAccountOverview(@Body TradeItGetAccountOverviewRequest request);

    @POST("/api/v1/position/getPositions")
    Call<TradeItGetPositionsResponse> getPositions(@Body TradeItGetPositionsRequest request);

    @POST("/api/v1/order/getAllOrderStatus")
    Call<TradeItOrderStatusResponse> getAllOrderStatus(@Body TradeItGetAllOrderStatusRequest request);

    @POST("/api/v1/order/getSingleOrderStatus")
    Call<TradeItOrderStatusResponse> getSingleOrderStatus(@Body TradeItGetSingleOrderStatusRequest request);

    @POST("/api/v1/order/cancelOrder")
    Call<TradeItOrderStatusResponse> cancelOrder(@Body TradeItCancelOrderRequest request);

    @POST("/api/v1/account/getAllTransactionsHistory")
    Call<TradeItGetAllTransactionsHistoryResponse> getAllTransactionsHistory(@Body TradeItGetAllTransactionsHistoryRequest request);
}
