package it.trade.api;


import it.trade.model.reponse.*;
import it.trade.model.request.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TradeItApi {
    @POST("api/v2/user/getOAuthLoginPopupUrlForWebApp?fidelityPilot=true")
    Call<TradeItOAuthLoginPopupUrlForWebAppResponse> getOAuthLoginPopupUrlForWebApp(@Body TradeItOAuthLoginPopupUrlForWebAppRequest request);

    @POST("api/v2/user/getOAuthLoginPopupUrlForMobile?fidelityPilot=true")
    Call<TradeItOAuthLoginPopupUrlForMobileResponse> getOAuthLoginPopupUrlForMobile(@Body TradeItOAuthLoginPopupUrlForMobileRequest request);

    @POST("api/v2/user/getOAuthLoginPopupURLForTokenUpdate?fidelityPilot=true")
    Call<TradeItOAuthLoginPopupUrlForTokenUpdateResponse> getOAuthLoginPopupURLForTokenUpdate(@Body TradeItOAuthLoginPopupUrlForTokenUpdateRequest request);

    @POST("api/v2/user/getOAuthAccessToken")
    Call<TradeItOAuthAccessTokenResponse> getOAuthAccessToken(@Body TradeItOAuthAccessTokenRequest request);

    @POST("api/v2/preference/getBrokerList?fidelityPilot=true")
    Call<TradeItAvailableBrokersResponse> getAvailableBrokers(@Body TradeItRequestWithKey request);

    @POST("api/v2/user/oAuthDelete")
    Call<TradeItResponse> unlinkLogin(@Body TradeItUnlinkLoginRequest request);

    @POST("api/v2/user/authenticate")
    Call<TradeItAuthenticateResponse> authenticate(@Body TradeItAuthenticateRequest request);

    @POST("api/v2/user/closeSession")
    Call<TradeItResponse> closeSession(@Body TradeItRequestWithSession request);

    @POST("api/v2/user/answerSecurityQuestion")
    Call<TradeItAuthenticateResponse> answerSecurityQuestion(@Body TradeItAnswerSecurityQuestionRequest request);

    @POST("api/v2/user/keepSessionAlive")
    Call<TradeItResponse> keepSessionAlive(@Body TradeItRequestWithSession request);

    @POST("api/v2/order/previewStockOrEtfOrder")
    Call<TradeItPreviewStockOrEtfOrderResponse> previewStockOrEtfOrder(@Body TradeItPreviewStockOrEtfOrderRequest request);

    @POST("api/v2/order/placeStockOrEtfOrder")
    Call<TradeItPlaceStockOrEtfOrderResponse> placeStockOrEtfOrder(@Body TradeItPlaceStockOrEtfOrderRequest request);

    @POST("api/v2/balance/getAccountOverview")
    Call<TradeItAccountOverviewResponse> getAccountOverview(@Body TradeItGetAccountOverviewRequest request);

    @POST("api/v2/position/getPositions")
    Call<TradeItGetPositionsResponse> getPositions(@Body TradeItGetPositionsRequest request);

    @POST("api/v2/order/getAllOrderStatus")
    Call<TradeItOrderStatusResponse> getAllOrderStatus(@Body TradeItGetAllOrderStatusRequest request);

    @POST("api/v2/order/getSingleOrderStatus")
    Call<TradeItOrderStatusResponse> getSingleOrderStatus(@Body TradeItGetSingleOrderStatusRequest request);

    @POST("api/v2/order/cancelOrder")
    Call<TradeItOrderStatusResponse> cancelOrder(@Body TradeItCancelOrderRequest request);

    @POST("api/v2/account/getAllTransactionsHistory")
    Call<TradeItGetAllTransactionsHistoryResponse> getAllTransactionsHistory(@Body TradeItGetAllTransactionsHistoryRequest request);
}
