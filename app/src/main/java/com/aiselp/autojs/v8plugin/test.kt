package com.aiselp.autojs.v8plugin

import androidx.compose.runtime.mutableStateListOf
import com.aiselp.autojs.v8engine.V8Engine
import com.caoccao.javet.annotations.V8Function
import com.caoccao.javet.values.reference.V8ValueError
import java.text.SimpleDateFormat
import java.util.Date

object test {
    val logList = mutableStateListOf<String>()
    var dateFormat = SimpleDateFormat("HH:mm:ss.SSS")

    fun execJsString(jsString: String): Unit {
        val engine = V8Engine()
        logList.clear()
        try {
            engine.v8Runtime.setPromiseRejectCallback { event, promise, value ->
                val err = value as V8ValueError
                val time = dateFormat.format( Date())
                logList.add("Unhandled Rejection at:', $promise")
                logList.add("${err.message}:'\nstack: ${err.stack}")
            }
            engine.v8Runtime.createV8ValueObject().use {
                it.bind(object {
                    @V8Function
                    fun log(vararg obj: Any?) {
                        val time = dateFormat.format( Date())
                        logList.add("[$time]:${obj.joinToString(" ")}")
                    }
                })
                engine.inject("console", it)
            }
            engine.execJsString(jsString)
        } catch (e: Exception) {
            logList.add(e.toString())
        } finally {
            engine.destroy()
        }
    }
}