package com.mikekuzn.ksptest

import com.mikekuzn.myksp.AutoFactory

@AutoFactory
abstract class Animal(val myName: String) {
    abstract fun sound(): String
}
