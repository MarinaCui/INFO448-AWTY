package edu.uw.ischool.chym2002.awty

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.os.Handler
import android.util.Log
import android.widget.Toast

class MyIntentService : IntentService("MyIntentService") {

    private val TAG = "MyIntentService"

    private lateinit var mHandler: Handler
    private var isRunning = false
    private var phoneNumber = ""
    private var message = ""
    private var time = 0

    override fun onCreate() {
        super.onCreate()
        mHandler = Handler()

        isRunning = true

        Log.i(TAG, "Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // get intent data
        phoneNumber = reformatPhoneNumber(intent?.getStringExtra("phone_number")!!)
        message = intent.getStringExtra("message")!!
        time = intent.getStringExtra("time")!!.toInt()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        while (isRunning) {
            Log.i(TAG, "Sending message to $phoneNumber: $message")

            mHandler.post {
                Toast.makeText(this, "$phoneNumber: $message", Toast.LENGTH_SHORT).show()
            }

            try {
                Thread.sleep((time * 1000 * 60).toLong())
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }

    override fun onDestroy() {
        Log.i(TAG, "Service destroyed")
        isRunning = false
        super.onDestroy()
    }

    private fun reformatPhoneNumber(phoneNumber: String): String {
        return "(${phoneNumber.substring(0, 3)}) ${phoneNumber.substring(3, 6)}-${phoneNumber.substring(6, 10)}"
    }
}