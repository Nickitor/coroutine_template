package com.nikitazamyslov.coroutinetemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val coroutine: Job = lifecycleScope.launch(Dispatchers.Default + handler) {
            try {
                withTimeout(2000) {
                    if (getData().contains("Some Data!"))
                        throw IndexOutOfBoundsException()
                }
                doWork()
                val data: Deferred<List<String>> = async {
                    getData()
                }
                if (data.await().contains("Some Data!")) {
                    println("Data error!")
                    cancel()
                } else {
                    println("Data received")
                }
                if (isActive) {
                    doWork()
                }
            } finally {
                withContext(NonCancellable) {
                    println("exception!")
                    delay(1000)
                    println("exception2!")
                }
            }
        }
    }

    private suspend fun doWork() {
        delay(1000L)
        println("Done some work!")
    }

    private suspend fun getData(): List<String> {
        delay(1000L)
        println("Data sent")
        return List(5) { "Some Data!" }
    }
}