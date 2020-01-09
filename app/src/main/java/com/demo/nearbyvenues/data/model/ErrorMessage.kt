package com.demo.nearbyvenues.data.model


data class ErrorMessage(val message: String, var type: ErrorMessageType = ErrorMessageType.API_ERROR)

enum class ErrorMessageType {
    LOCATION_PERMISSION_ERROR,
    API_ERROR,
    LOCATION_PERMISSION_ERROR_SETTINGS
}
