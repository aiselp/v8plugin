package com.aiselp.autojs.v8engine

import com.caoccao.javet.interop.V8Host
import com.caoccao.javet.interop.V8Runtime
import com.caoccao.javet.interop.converters.JavetProxyConverter
import com.caoccao.javet.values.V8Value
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class V8Engine {
    val v8Runtime: V8Runtime = V8Host.getV8Instance().createV8Runtime<V8Runtime>()
    val v8Thread: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        //设置java对象转换器
        v8Runtime.converter = JavetProxyConverter()
    }

    fun execJsString(jsString: String): Unit = v8Thread.run {
        v8Runtime.getExecutor(jsString).execute<V8Value>()
    }

    fun inject(name: String, o: Any) {
        v8Runtime.globalObject[name] = o
    }

    fun destroy() {
        v8Thread.shutdownNow()
        v8Runtime.close()
    }
}