package com.coradec.apps.trader.ibkr.com

import com.coradec.coradeck.com.model.Information
import com.coradec.coradeck.com.model.Recipient
import com.coradec.coradeck.com.model.impl.BasicCommand
import com.coradec.coradeck.core.model.Origin
import com.coradec.coradeck.core.model.Priority
import com.coradec.coradeck.core.model.Priority.Companion.defaultPriority
import com.coradec.coradeck.core.util.classname
import com.coradec.coradeck.text.model.LocalText

abstract class InteractiveRequest(
    origin: Origin,
    priority: Priority = defaultPriority
): BasicCommand(origin, priority = priority), Recipient {
    fun ignoreInformation(event: Information) {
        warn(TEXT_INFORMATION_IGNORED, this, event::class.classname)
    }

    companion object {
        val TEXT_INFORMATION_IGNORED = LocalText("InformationIgnored2")
    }
}
