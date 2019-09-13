import processing.core.PImage

class Inventory(spriteSheet: SpriteSheet) {
    val entries = arrayOf(
        InventoryEntry("redstone", spriteSheet[16]) { l, m -> Redstone(l, m) },
        InventoryEntry("or gate", spriteSheet[8]) { l, m -> OrGate(l, m) },
        InventoryEntry("and gate", spriteSheet[9]) { l, m -> AndGate(l, m) },
        InventoryEntry("bridge", spriteSheet[15]) { l, m -> Bridge(l, m) },
        InventoryEntry("lever", spriteSheet[11]) { l, m -> Lever(l, m) },
        InventoryEntry("lamp", spriteSheet[13]) { l, m -> Lamp(l, m) },
        InventoryEntry("tree", spriteSheet[1]) { l, m -> SpriteSheetTile(l, 1, m, 0) },
        InventoryEntry("chest", spriteSheet[6]) { l, m -> SpriteSheetTile(l, 6, m, 0) }
    )
}

data class InventoryEntry(val name: String, val displayImage: PImage, val constructor: (Vec2<Int>, Map) -> Tile)