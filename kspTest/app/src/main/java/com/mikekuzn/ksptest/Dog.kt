package com.mikekuzn.ksptest

import com.mikekuzn.myksp.AutoElement

@AutoElement
class Dog(myName: String) : Animal(myName) {
    override fun sound() = "Dog($myName) sound"
}