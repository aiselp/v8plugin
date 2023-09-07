package com.aiselp.autojs.v8plugin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.aiselp.autojs.v8plugin.ui.theme.V8pluginTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val mainScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            V8pluginTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContext()
                }
            }
        }
    }

    @Composable
    fun MainContext() {
        val code = remember { mutableStateOf("let a = 1 + 2;\nconsole.log(a)") }
        val out = remember { mutableStateOf("") }
        Column {
            Text(text = "测试js代码")
            TextField(code.value, { code.value = it }, Modifier.fillMaxWidth())
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(onClick = { mainScope.launch { test.execJsString(code.value, out) } }) {
                    Text(text = "运行")
                }
            }
            Text(text = "输出：")
            TextField(value = out.value, { out.value = it }, Modifier.fillMaxWidth())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}


