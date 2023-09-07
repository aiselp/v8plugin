package com.aiselp.autojs.v8engine

import android.os.Looper

class V8Looper(val looper: Looper) {

    constructor(): this(Looper.getMainLooper()){

    }
}