package com.gildedrose.update

import com.gildedrose.Item

class AgedBrieQualityUpdater : QualityUpdater {

    override operator fun invoke(item: Item) {
        item.quality = calculateUpdatedQuality(item.quality, item.sellIn)
    }

    private fun calculateUpdatedQuality(quality: Int, sellIn: Int): Int = quality +
            if (sellIn < 0) QualityUpdater.QUALITY_INCREMENT_2 else QualityUpdater.QUALITY_INCREMENT_DEFAULT

}