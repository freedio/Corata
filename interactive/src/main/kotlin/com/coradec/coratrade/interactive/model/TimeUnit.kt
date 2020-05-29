/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

enum class TimeUnit(val repr: String) {
    Seconds("S"), Days("D"), Weeks("W"), Months("M"), Years("Y");

    override fun toString(): String = repr
}
