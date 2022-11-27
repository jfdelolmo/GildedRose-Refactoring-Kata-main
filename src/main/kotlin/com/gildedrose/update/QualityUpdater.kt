package com.gildedrose.update

import com.gildedrose.Item

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