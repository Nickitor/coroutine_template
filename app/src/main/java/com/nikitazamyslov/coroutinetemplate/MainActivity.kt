package com.nikitazamyslov.coroutinetemplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    // Обрабатывает все необработанные исключения подобно uncaughtExceptionHandler
    private val handler = CoroutineExceptionHandler { context, exception ->
        println("CoroutineExceptionHandler got $exception from ${context[CoroutineName]?.name}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch(handler + CoroutineName("GetProfiles") + Dispatchers.Default) {
            val profiles: List<Profile> =
                withContext(Dispatchers.Default) { Database().getProfiles() }
            profiles.map { println(it) }
        }
    }
}