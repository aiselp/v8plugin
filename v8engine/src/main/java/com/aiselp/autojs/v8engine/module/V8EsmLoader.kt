package com.aiselp.autojs.v8engine.module

import android.util.Log
import com.caoccao.javet.interfaces.IV8ModuleResolver
import java.io.File
import java.net.URI

class V8EsmLoader() {
    private val loadDirs = ArrayList<URI>()

    constructor(res: String) : this() {
        loadDirs.add(File(res).toURI())
    }

    constructor(res: File) : this() {
        loadDirs.add(res.toURI())
    }

    val v8ModuleResolver = IV8ModuleResolver { v8Runtime, resourceName, v8ModuleReferrer ->
        Log.d(LOG_TAG, "loadModule $resourceName")
        for (uri in loadDirs) {
            val file = File(uri.resolve(resourceName))
            if (file.exists()) {
                return@IV8ModuleResolver v8Runtime.getExecutor(file).setResourceName(resourceName)
                    .compileV8Module()
            }
        }
        return@IV8ModuleResolver null
    }

    companion object {
        const val LOG_TAG = "V8EsmLoader"
    }
}