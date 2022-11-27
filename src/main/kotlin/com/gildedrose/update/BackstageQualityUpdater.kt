package com.gildedrose.update

import com.gildedrose.Item

class BackstageQualityUpdater : QualityUpdater {

    companion object {
        private const val DAYS_BACKSTAGE_QUALITY_PLUS_3 = 10
        private const val DAYS_BACKSTAGE_QUALITY_PLUS_2 = 5
    }

    override operator fun invoke(item: Item) {
        item.quality = calculateUpdatedQuality(item.quality, item.sellIn)
    }

    private fun calculateUpdatedQuality(quality: Int, sellIn: Int): Int {
        val lessThan10Days: IntProgression =
            DAYS_BACKSTAGE_QUALITY_PLUS_3 downTo DAYS_BACKSTAGE_QUALITY_PLUS_2 + 1
        val lessThan5Days: IntProgression = DAYS_BACKSTAGE_QUALITY_PLUS_2 downTo 0

        return when {
            sellIn in lessThan10Days -> Integer.min(
                QualityUpdater.MAX_QUALITY, quality + QualityUpdater.QUALITY_INCREMENT_3
            )

            sellIn in lessThan5Days -> Integer.min(
                QualityUpdater.MAX_QUALITY, quality + QualityUpdater.QUALITY_INCREMENT_2
            )

            sellIn <= 0 -> QualityUpdater.QUALITY_RESET
            else -> Integer.min(QualityUpdater.MAX_QUALITY, quality + QualityUpdater.QUALITY_INCREMENT_DEFAULT)
        }
    }

}