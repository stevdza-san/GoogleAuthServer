package com.example.plugins

import com.example.domain.model.UserSession
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import java.io.File

fun Application.configureSession() {
    install(Sessions) {
        val secretEncryptKey = hex("00112233445566778899aabbccddeeff")
        val secretAuthKey = hex("02030405060708090a0b0c")
        cookie<UserSession>(
            name = "USER_SESSION",
            storage = directorySessionStorage(File(".sessions"))
        ) {
            transform(SessionTransportTransformerEncrypt(secretEncryptKey, secretAuthKey))
        }
    }
}