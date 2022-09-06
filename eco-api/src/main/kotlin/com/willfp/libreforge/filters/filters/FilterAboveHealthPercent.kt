package com.willfp.libreforge.filters.filters

import com.willfp.eco.core.config.interfaces.Config
import com.willfp.eco.util.NumberUtils
import com.willfp.libreforge.filters.FilterComponent
import com.willfp.libreforge.triggers.TriggerData
import org.bukkit.attribute.Attribute

class FilterAboveHealthPercent : FilterComponent() {
    override fun passes(data: TriggerData, config: Config): Boolean {
        val entity = data.victim ?: return true
        val player = data.player

        return config.withInverse("aboveHealthPercent", Config::getStringOrNull) {
            if (it == null) {
                return@withInverse true
            }

            val maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value ?: 20.0
            val percent = (entity.health / maxHealth) * 100

            val minimumPercent = NumberUtils.evaluateExpression(
                it,
                player,
                config
            )

            percent >= minimumPercent
        }
    }
}
