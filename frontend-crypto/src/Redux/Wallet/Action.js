import api from "@/Page/Api/api";
import * as types from "./ActionTypes";

// Action Creators
export const getUserWallet = (jwt) => async (dispatch) => {
  dispatch({ type: types.GET_USER_WALLET_REQUEST });
  console.log("jwt", jwt);
  try {
    const response = await api.get("/api/wallet", {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    });
   
    dispatch({
      type: types.GET_USER_WALLET_SUCCESS,
      payload: response.data,
    });
   ;
  } catch (error) {
    console.log(error);
    dispatch({
      type: types.GET_USER_WALLET_FAILURE,
      error: error.message,
    });
  }
};

export const getWalletTransactions =
  ({jwt}) =>
  async (dispatch) => {
    dispatch({ type: types.GET_WALLET_TRANSACTION_REQUEST });

    try {
      const response = await api.get("/api/wallet/transactions", {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });

      dispatch({
        type: types.GET_WALLET_TRANSACTION_SUCCESS,
        payload: response.data,
      });
    
    } catch (error) {
      console.log(error);
      dispatch({
        type: types.GET_WALLET_TRANSACTION_FAILURE,
        error: error.message,
      });
    }
  };

export const depositMoney =
  ({ jwt, orderId, paymentId,navigate }) =>
  async (dispatch) => {
    dispatch({ type: types.DEPOSIT_MONEY_REQUEST });

    try {
      const response = await api.put(`/api/wallet/deposit`, null, {
        params: {
          order_id: orderId,
          payment_id: paymentId,
        },
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });

      dispatch({
        type: types.DEPOSIT_MONEY_SUCCESS,
        payload: response.data,
      });
      navigate("/wallet")
      console.log(response.data);
    } catch (error) {
      console.error(error);
      dispatch({
        type: types.DEPOSIT_MONEY_FAILURE,
        error: error.message,
      });
    }
  };

export const paymentHandler =
  ({ jwt, amount, paymentMethod }) =>
  async (dispatch) => {
    dispatch({ type: types.DEPOSIT_MONEY_REQUEST });

    try {
      const response = await api.post(
        `/api/payment/${paymentMethod}/amount/${amount}`,
        null,
        {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        }
      );

      window.location.href = response.data.payment_url;

      dispatch({
        type: types.DEPOSIT_MONEY_SUCCESS,
        payload: response.data,
      });
    } catch (error) {
      console.log("error", error);
      dispatch({
        type: types.DEPOSIT_MONEY_FAILURE,
        error: error.message,
      });
    }
  };

export const transferMoney =
  ({ jwt, walletId, reqData }) =>
  async (dispatch) => {
    dispatch({ type: types.TRANSFER_MONEY_REQUEST });

    try {
      const response = await api.put(
        `/api/wallet/${walletId}/transfer`,
        reqData,
        {
          headers: {
            Authorization: `Bearer ${jwt}`,
          },
        }
      );

      dispatch({
        type: types.TRANSFER_MONEY_SUCCESS,
        payload: response.data,
      });

      console.log("Transfer Successful:", response.data);
    } catch (error) {
      dispatch({
        type: types.TRANSFER_MONEY_FAILURE,
        error: error.message,
      });
    }
  };

  // At the bottom of Action.js

export const addPaymentDetails =
  ({ jwt, paymentDetails }) =>
  async (dispatch) => {
    dispatch({ type: types.ADD_PAYMENT_DETAILS_REQUEST });

    try {
      const response = await api.put(`/api/payment-details`, paymentDetails, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      });

      dispatch({
        type: types.ADD_PAYMENT_DETAILS_SUCCESS,
        payload: response.data,
      });
    } catch (error) {
      dispatch({
        type: types.ADD_PAYMENT_DETAILS_FAILURE,
        error: error.message,
      });
    }
  };
