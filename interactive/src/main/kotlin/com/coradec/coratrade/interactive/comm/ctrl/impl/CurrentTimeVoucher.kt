/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.ctrl.impl

import com.coradec.coradeck.com.model.Information
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coradeck.type.model.GenericType
import com.coradec.coratrade.interactive.comm.model.impl.CurrentTimeEvent
import com.coradec.coratrade.interactive.ctrl.RequestDispatcher
import java.time.*
import java.time.temporal.ChronoUnit.NANOS

class CurrentTimeVoucher(origin: Origin) :
        RequestDispatcher.IBrokerVoucher<LocalDateTime>(GenericType.of(LocalDateTime::class.java), origin) {
    override val interests: Set<Class<out Information>> = setOf(CurrentTimeEvent::class.java)

    private lateinit var myLocalTime: ZonedDateTime
    /* Add this to localtime to get the TWS time. */
    val Δt get() = Duration.of(NANOS.between(myLocalTime, value().atZone(ZoneId.systemDefault())), NANOS)
    val localTime: ZonedDateTime by lazy {
        standby()
        myLocalTime
    }

    override fun trigger() = RequestDispatcher.requestCurrentTime(this)

    override fun notify(info: Information): Boolean =
            if (info is CurrentTimeEvent) {
                myLocalTime = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault())
                value = info.localTime
                succeed()
                true
            } else super.notify(info)

}
