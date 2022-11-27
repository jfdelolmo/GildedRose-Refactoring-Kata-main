package com.gildedrose

import com.gildedrose.QualityUpdater.Companion.MAX_QUALITY
import com.gildedrose.QualityUpdater.Companion.MIN_QUALITY
import com.gildedrose.QualityUpdater.Companion.QUALITY_INCREMENT_2
import com.gildedrose.QualityUpdater.Companion.QUALITY_INCREMENT_3
import com.gildedrose.QualityUpdater.Companion.QUALITY_INCREMENT_DEFAULT
import com.gildedrose.QualityUpdater.Companion.QUALITY_RESET
import java.lang.Integer.max
import java.lang.Integer.min

private const val AGED_BRIE = "Aged Brie"
private const val BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert"
private const val SULFURAS = "Sulfuras, Hand of Ragnaros"

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
            sellIn in lessThan10Days -> min(
                MAX_QUALITY, quality + QUALITY_INCREMENT_3
            )

            sellIn in lessThan5Days -> min(
                MAX_QUALITY, quality + QUALITY_INCREMENT_2
            )

            sellIn <= 0 -> QUALITY_RESET
            else -> min(MAX_QUALITY, quality + QUALITY_INCREMENT_DEFAULT)
        }
    }

}

class AgedBrieQualityUpdater : QualityUpdater {

    override operator fun invoke(item: Item) {
        item.quality = calculateUpdatedQuality(item.quality, item.sellIn)
    }

    private fun calculateUpdatedQuality(quality: Int, sellIn: Int): Int = quality +
            if (sellIn < 0) QUALITY_INCREMENT_2 else QUALITY_INCREMENT_DEFAULT

}

class DefaultQualityUpdater : QualityUpdater {

    override operator fun invoke(item: Item) {
        item.quality = calculateUpdatedQuality(item.quality, item.name)
    }

    private fun calculateUpdatedQuality(quality: Int, name: String): Int = if (name != SULFURAS) {
        max(MIN_QUALITY, quality - 1)
    } else {
        quality
    }

}

interface QualityUpdater {

    companion object {
        const val QUALITY_RESET = -1
        const val QUALITY_INCREMENT_DEFAULT = 1
        const val QUALITY_INCREMENT_2 = 2
        const val QUALITY_INCREMENT_3 = 3

        const val MAX_QUALITY = 50
        const val MIN_QUALITY = 0
    }

    operator fun invoke(item: Item)

}

class GildedRose(var items: Array<Item>) {

    private val qualityUpdatersMap = mapOf(
        AGED_BRIE to AgedBrieQualityUpdater(),
        BACKSTAGE to BackstageQualityUpdater(),
    )

    fun updateQuality() {
        items.map { updateItem(it) }
    }

    private fun updateItem(item: Item) {
        updateQuality(item)
        decreaseSellIn(item)
    }

    private fun updateQuality(item: Item) {
        qualityUpdatersMap.getOrDefault(item.name, DefaultQualityUpdater())(item)
    }

    private fun decreaseSellIn(item: Item) {
        if (item.name != SULFURAS) {
            item.sellIn -= 1
        }
    }

}
