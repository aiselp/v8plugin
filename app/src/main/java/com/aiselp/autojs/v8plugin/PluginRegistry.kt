package com.aiselp.autojs.v8plugin

import org.autojs.plugin.sdk.PluginRegistry

open class MyPluginRegistry : PluginRegistry() {
    companion object {
        init {
            // 注册默认插件
            registerDefaultPlugin { context, selfContext, runtime, topLevelScope ->
                AutoJsPlugin(context, selfContext, runtime, topLevelScope)
            }
        }
    }
}