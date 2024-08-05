package com.mikekuzn.ksptest

import com.mikekuzn.myksp.AutoElement

class Wrapper {
    @AutoElement
    class Cat(extraKey: String) : Animal(extraKey) {
        override fun sound() = "Cat($myName) sound"
    }
}