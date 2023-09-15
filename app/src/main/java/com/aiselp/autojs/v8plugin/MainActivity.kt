package com.aiselp.autojs.v8plugin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

        Column(Modifier.padding(8.dp)) {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(8.dp)) {
                    Text(text = "测试js代码:")
                    TextField(
                        code.value, { code.value = it },
                        Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(onClick = { mainScope.launch { test.execJsString(code.value) } }) {
                    Text(text = "运行")
                }
            }
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(8.dp).fillMaxSize()) {
                    Text(text = "输出：")
                    LazyColumn(
                        contentPadding = PaddingValues(5.dp)
                    ) {
                        test.logList.forEach {
                            item {
                                Text(text = it)
                            }
                        }
                    }
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}


