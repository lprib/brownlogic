import processing.core.PGraphics

class Map(val spriteSheet: SpriteSheet, val width: Int, val height: Int) {
    val tiles = arrayOfNulls<Tile?>(width * height)

    fun draw(g: PGraphics) {
        //background

        for (tile in tiles) {
            tile?.draw(g)
        }
    }

    fun setTile(loc: Vec2<Int>, tile: Tile) {
        tiles[loc.y * width + loc.x] = tile
        tile.onMapPlace()
    }

    fun getTile(loc: Vec2<Int>): Tile? {
        return if (loc.withinRect(0, 0, width, height)) {
            tiles[loc.y * width + loc.x]
        } else null
    }

    fun tilePositionFromScreenPosition(loc: Vec2<Int>): Vec2<Int>? {
        return if (loc.withinRect(0, 0, width * TILE_SIZE_PIXELS, height * TILE_SIZE_PIXELS)) {
            Vec2(loc.x / TILE_SIZE_PIXELS, loc.y / TILE_SIZE_PIXELS)
        } else null
    }

    fun update() {
        val redstone = tiles.filterIsInstance<Redstone>()
        //start fresh
        //more efficient way to do this
        redstone.forEach { it.state = false }
        tiles.filterIsInstance<Update>().forEach { it.update() }
        redstone.forEach { it.updatedThisPass = false }

        //after redstone propagation, notify listeners of their new redstone state
        for (tile in tiles) {
            if (tile is RedstoneListener) {
                var redstoneVal = false
                for (n in tile.getNeighbors()) {
                    if (n is LogicState && n.state) {
                        redstoneVal = true
                        break
                    }
                }
                tile.onRedstoneState(redstoneVal)
            }
        }
    }

    companion object {
        const val TILE_SIZE_PIXELS = 32
    }
}