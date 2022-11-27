package com.gildedrose

import com.gildedrose.update.AgedBrieQualityUpdater
import com.gildedrose.update.BackstageQualityUpdater
import com.gildedrose.update.DefaultQualityUpdater

const val AGED_BRIE = "Aged Brie"
const val BACKSTAGE = "Backstage passes to a TAFKAL80ETC concert"
const val SULFURAS = "Sulfuras, Hand of Ragnaros"

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
