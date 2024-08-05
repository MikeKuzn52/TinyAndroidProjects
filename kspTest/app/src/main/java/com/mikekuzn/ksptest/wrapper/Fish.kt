package com.mikekuzn.ksptest.wrapper

import com.mikekuzn.ksptest.Animal
import com.mikekuzn.myksp.AutoElement

@AutoElement
class Fish(extraKey: String) : Animal(extraKey) {
    override fun sound() = "Fish($myName) no sound"
}