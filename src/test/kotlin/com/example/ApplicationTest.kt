package com.example

import com.example.domain.model.ApiRequest
import com.example.domain.model.Endpoint
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    private val tokenId: String =
        "eyJhbGciOiJSUzI1NiIsImtpZCI6IjNkZDZjYTJhODFkYzJmZWE4YzM2NDI0MzFlN2UyOTZkMmQ3NWI0NDYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIyNzE1MDUzNjI0MTctdDBqaGR1dHA5YWpzdWJscXQ3cDFjMGM1MnYzaGh0ZzIuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIyNzE1MDUzNjI0MTctdTdvYXE0NHEzbzR1bzhyaXRwYWpuMzJjaW5sM3BhOGEuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDY0NDYxODc3OTYyODA1MTk3NDQiLCJlbWFpbCI6InN0ZWZhbi5qb3ZhbmF2aWNoQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoi0KHRgtC10YTQsNC9INCI0L7QstCw0L3QvtCy0LjRmyIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS0vQU9oMTRHZ1ZhNUE0V3oxTDBUTjhlZzRJU1VpZktkT2V5cVBodHV4MGhCMFJiZz1zOTYtYyIsImdpdmVuX25hbWUiOiLQodGC0LXRhNCw0L0iLCJmYW1pbHlfbmFtZSI6ItCI0L7QstCw0L3QvtCy0LjRmyIsImxvY2FsZSI6InNyIiwiaWF0IjoxNjQ2NjQ2Njc1LCJleHAiOjE2NDY2NTAyNzV9.CmYG3ph9TWDtmPPCThth-MHiRcMHU3UpmqV18feGUGW2n06dJGNXPx0cM6OIMZpax61X9XBHYRNgFKkqivo3yfiwwv9t-nRUOrOBp8__iR-R1Yt8u4Ohzd_hzIa44QO2MDC6H2ZquBO_zGZ5gsE6KdK5l0pqHWXJBDZwFynLTrbpKntQy4qtW2SPtL7Og78D0MTohRtcz-XAqj5Y52h2I_hSabYvPUEChL9HpuplFf4gO44PdnqUHote7vA9JBvEC9EWJb8Wa1z9GzNbCATroWYwtzjp29CS-AYXail3hShYVlwD2aNw_ZbqZ6FQaOlqKb3pp_h3FCCwVYmPWCgN2w"
    private val invalidTokenId: String = "INVALID"

    @Test
    fun `access ROOT Route, assert WELCOME message`() {
        testApplication {
            val response = client.get(urlString = Endpoint.Root.path)
            assertEquals(
                expected = "Welcome to Ktor Server!",
                actual = response.bodyAsText()
            )
        }
    }

    @Test
    fun `access UNAUTHORIZED Route, assert NOT AUTHORIZED status and message`() {
        testApplication {
            val response = client.get(urlString = Endpoint.Unauthorized.path)
            assertEquals(
                expected = HttpStatusCode.Unauthorized,
                actual = response.status
            )
            assertEquals(
                expected = "Not Authorized.",
                actual = response.bodyAsText()
            )
        }
    }

    @Test
    fun `access AUTHORIZED Route, pass correct session, assert OK status and success response`() {
        testApplication {
            val myClient = createClient {
                install(HttpCookies)
                install(ContentNegotiation) {
                    json()
                }
            }

            val tokenResponse = myClient.post(urlString = Endpoint.TokenVerification.path) {
                contentType(ContentType.Application.Json)
                header("Content-Type", "application/json")
                setBody(ApiRequest(tokenId = tokenId))
            }
            assertEquals(
                expected = HttpStatusCode.OK,
                actual = tokenResponse.status
            )
        }
    }
}