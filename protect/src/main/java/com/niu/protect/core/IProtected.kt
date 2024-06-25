package com.niu.protect.core

interface IProtected {
    fun setAppBlack(list: List<String>);

    fun cleanAppBlack();
}