package com.mikekuzn.examle.duggetrtest

import javax.inject.Inject
import javax.inject.Singleton

class testClassB @Inject constructor(val t_c:testClassC){
    val s = "testClassB"
}

class testClassC @Inject constructor() {
    val s = "testClassC"
}

/*
class testClassD (val N: Int) {
    val s = "testClassD"
}/ */
class testClassD @Inject constructor(val  te:testClassE) {
    val s = "testClassD"
}// */

class testClassE() {
    val s = "testClassE"
}

data class testClassA @Inject constructor(val tb:testClassB, val tc:testClassC, val td:testClassD) {
    val s = "testClassA"
}