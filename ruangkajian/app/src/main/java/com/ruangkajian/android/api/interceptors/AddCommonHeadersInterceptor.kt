package com.ruangkajian.android.api.interceptors

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response

class AddCommonHeadersInterceptor(
    private val platform: String,
    private val auth: String,
    private val appVersionName: String,
    private val appVersionCode: Int,
    private val osVersion: String,
    private val languageCode: String
) : Interceptor {

    private val userAgent by lazy { createUserAgent() }
    private val appInfo by lazy { createAppInfo() }

    override fun intercept(chain: Chain): Response {
        val request = addHeaders(chain.request())
        return chain.proceed(request)
    }

    private fun addHeaders(request: Request): Request {
        return request.newBuilder()
            .addHeader(PLATFORM, platform)
            .addHeader(KEY, auth)
            .addHeader(USER_AGENT, userAgent)
            .addHeader(APP_INFO, appInfo)
            .addHeader(ACCEPT_LANGUAGE, normalizeLanguageCode(languageCode))
            .build()
    }

    private fun createUserAgent(): String {
        return "basic/$appVersionName ($appVersionCode)"
    }

    private fun createAppInfo(): String {
        return "android/$osVersion/$appVersionName-$appVersionCode"
    }

    /*  In order to support backward compatibility based on
     *  https://developer.android.com/reference/java/util/Locale.html#Locale(java.lang.String)
     *
     *  We need to add simple logic to handle it.
     *
     */
    private fun normalizeLanguageCode(localeCode: String): String {
        return when (localeCode) {
            "in" -> "id"
            "iw" -> "he"
            "ji" -> "yi"
            else -> localeCode
        }
    }

    companion object {
        private const val PLATFORM = "X-API-Platform"
        private const val KEY = "X-API-KEY"
        private const val USER_AGENT = "User-Agent"
        private const val APP_INFO = "X-API-App-Info"
        private const val ACCEPT_LANGUAGE = "Accept-Language"
    }
}
