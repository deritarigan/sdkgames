package com.akggames.akg_sdk.dao

import org.json.JSONArray
import org.json.JSONObject

class PaymentDao() {

    private fun getBaseRequest(): JSONObject {
        return JSONObject()
            .put("apiVersion", 2)
            .put("apiVersionMinor", 0)
    }

    private fun getTokenizationSpecification(): JSONObject {
        val tokenizationSpecification = JSONObject()
        tokenizationSpecification.put("type", "PAYMENT_GATEWAY")
        tokenizationSpecification.put(
            "parameters",
            JSONObject()
                .put("gateway", "example")
                .put("gatewayMerchantId", "exampleMerchantId")
        )

        return tokenizationSpecification
    }

    private fun getAllowedCardNetworks(): JSONArray {
        return JSONArray()
            .put("MASTERCARD")
            .put("VISA")
    }

    private fun getAllowedCardAuthMethods(): JSONArray {
        return JSONArray()
            .put("PAN_ONLY")
            .put("CRYPTOGRAM_3DS")
    }

    private fun getBaseCardPaymentMethod(): JSONObject {
        val cardPaymentMethod = JSONObject()
        cardPaymentMethod.put("type", "CARD")
        cardPaymentMethod.put(
            "parameters",
            JSONObject()
                .put("allowedAuthMethods", getAllowedCardAuthMethods())
                .put("allowedCardNetworks", getAllowedCardNetworks())
        )

        return cardPaymentMethod
    }

    private fun getCardPaymentMethod(): JSONObject {
        val cardPaymentMethod = getBaseCardPaymentMethod()
        cardPaymentMethod.put("tokenizationSpecification", getTokenizationSpecification())

        return cardPaymentMethod
    }

}