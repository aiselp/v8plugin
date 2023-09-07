package com.aiselp.autojs.v8engine

import com.aiselp.autojs.v8engine.module.V8EsmLoader
import com.caoccao.javet.interop.V8Host
import com.caoccao.javet.interop.V8Runtime
import com.caoccao.javet.interop.converters.JavetProxyConverter
import com.caoccao.javet.values.V8Value
import java.io.File


class V8Engine {
    val v8Runtime: V8Runtime = V8Host.getV8Instance().createV8Runtime<V8Runtime>()
    private val workdir = "/"

    private val esmLoader = V8EsmLoader(workdir)

    init {
        //设置java对象转换器
        v8Runtime.converter = JavetProxyConverter()
        v8Runtime.v8ModuleResolver = esmLoader.v8ModuleResolver
    }

    fun execJsString(jsString: String): V8Value? {
        val module = v8Runtime.getExecutor(jsString).setModule(true)
        return module.execute<V8Value>()
    }
    fun execFile(file: File): V8Value? {
        val module = v8Runtime.getExecutor(file).setModule(true)
        module.resourceName = file.path
        return module.execute<V8Value>()
    }

    fun inject(name: String, obj: Any) {
        v8Runtime.globalObject[name] = obj
    }

    fun destroy() {
        v8Runtime.close()
    }
}