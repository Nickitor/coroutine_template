package com.nikitazamyslov.coroutinetemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch(Dispatchers.Default) {
            doWork()
            val result: Deferred<String> = async {
                getData()
            }
            println(result.await())
            doWork()
        }
    }

    private suspend fun doWork() {
        delay((500L..5000L).random())
        println("Done some work!")
    }

    suspend fun getData(): String {
        delay((500L..5000L).random())
        return "Get some Data!"
    }
}