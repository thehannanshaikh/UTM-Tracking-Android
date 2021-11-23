package com.example.utmtracking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails

class MainActivity : AppCompatActivity() {

    private lateinit var referrerClient: InstallReferrerClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        

        referrerClient = InstallReferrerClient.newBuilder(this).build()
        referrerClient.startConnection(object : InstallReferrerStateListener {

            override fun onInstallReferrerSetupFinished(responseCode: Int) {
                when (responseCode) {
                    InstallReferrerClient.InstallReferrerResponse.OK -> {
                        // Connection established.
                        obtainReferrerDetails()
                    }
                    InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {
                        // API not avinitTracking()
                        //    }
                        //
                        //
                        //    private fun initTracking() {ailable on the current Play Store app.
                    }
                    InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {
                        // Connection couldn't be established.
                    }
                }
            }

            override fun onInstallReferrerServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })
    }

    private fun obtainReferrerDetails() {
        val response: ReferrerDetails = referrerClient.installReferrer
        Log.d("response - ", response.toString())
        val referrerUrl: String = response.installReferrer
        Log.d("referrerUrl - ", referrerUrl)
        val referrerClickTime: Long = response.referrerClickTimestampSeconds
        Log.d("referrerClickTime - ", referrerClickTime.toString())
        val appInstallTime: Long = response.installBeginTimestampSeconds
        Log.d("appInstallTime - ", appInstallTime.toString())
        val instantExperienceLaunched: Boolean = response.googlePlayInstantParam
        Log.d("instantExperienceLaunch", instantExperienceLaunched.toString())
    }
}