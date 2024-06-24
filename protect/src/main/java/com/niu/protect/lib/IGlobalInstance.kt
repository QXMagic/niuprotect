package com.niu.protect.lib

import android.content.ComponentCallbacks2
import android.content.Context
import android.content.pm.PackageManager
import java.io.File

interface IGlobalInstance : ComponentCallbacks2 {// extends ContextWrapper implements ComponentCallbacks2{
    fun getCacheDir(): File
    fun getContext():Context
    fun getPackageManager():PackageManager
}