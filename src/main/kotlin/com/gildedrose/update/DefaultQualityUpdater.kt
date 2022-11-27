package com.gildedrose.update

import com.gildedrose.Item
import com.gildedrose.SULFURAS

class DefaultQualityUpdater : QualityUpdater {

    override operator fun invoke(item: Item) {
        item.quality = calculateUpdatedQuality(item.quality, item.name)
    }

    private fun calculateUpdatedQuality(quality: Int, name: String): Int = if (name != SULFURAS) {
        Integer.max(QualityUpdater.MIN_QUALITY, quality - 1)
    } else {
        quality
    }

}