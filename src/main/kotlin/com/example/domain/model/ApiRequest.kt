package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiRequest(
    val tokenId: String
)
