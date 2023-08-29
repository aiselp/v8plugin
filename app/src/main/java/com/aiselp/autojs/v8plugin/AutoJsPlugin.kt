package com.aiselp.autojs.v8plugin

import android.content.Context
import org.autojs.plugin.sdk.Plugin
import com.aiselp.autojs.v8engine.V8Engine

class AutoJsPlugin (context: Context, selfContext: Context, runtime: Any?, topLevelScope: Any?) :
    Plugin(context, selfContext, runtime, topLevelScope) {
    override fun getAssetsScriptDir(): String {
        return "js-plugin"
    }
    fun createV8Engine(): V8Engine {
        return V8Engine()
    }
}