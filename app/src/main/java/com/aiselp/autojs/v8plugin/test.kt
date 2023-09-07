package com.aiselp.autojs.v8plugin

import androidx.compose.runtime.MutableState
import com.aiselp.autojs.v8engine.V8Engine
import com.caoccao.javet.annotations.V8Function

object test {

    suspend fun execJsString(jsString: String, out: MutableState<String>): Unit {
        val engine = V8Engine()
        try {
            engine.v8Runtime.createV8ValueObject().use {
                it.bind(object {
                    @V8Function
                    fun log(vararg obj: Any?) {
                        out.value = obj.joinToString(" ")
                    }
                })
                engine.inject("console", it)
            }
            engine.execJsString(jsString)
        } catch (e: Exception) {
            out.value = e.message.toString()
        } finally {
            engine.destroy()
        }
    }
}