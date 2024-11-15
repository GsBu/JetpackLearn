package com.jobs.android.jetpacklearn.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.jobs.android.jetpacklearn.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoroutineActivity : AppCompatActivity() {
    private val TAG = "CoroutineActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)

        findViewById<Button>(R.id.bt_back).setOnClickListener {
            finish()
        }

        GlobalScope.launch{
            delay(1000)
            Log.e(TAG,"Hello world 1 ${Thread.currentThread()}")
        }

        CoroutineScope(Dispatchers.Main).launch{
            delay(1200)
            Log.e(TAG,"Hello world 2 ${Thread.currentThread()}")
        }

        Log.e(TAG,"onCreate ${Thread.currentThread()}")

        Thread {
            Log.e(TAG,"Thread ${Thread.currentThread()}")
        }.start()
    }
}