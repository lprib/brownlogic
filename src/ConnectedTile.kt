import processing.core.PGraphics
import processing.core.PImage

//marching squares
open class ConnectedTile(loc: Vec2<Int>, val spriteSheetStartIndex: Int, connectionClass: Int, map: Map) :
    Tile(loc, map, connectionClass) {
    protected var spriteSheetOffset = 0

    override fun draw(g: PGraphics) {
        g.image(
            map.spriteSheet[spriteSheetStartIndex + spriteSheetOffset],
            (loc.x * Map.TILE_SIZE_PIXELS).toFloat(),
            (loc.y * Map.TILE_SIZE_PIXELS).toFloat(),
            Map.TILE_SIZE_PIXELS.toFloat(),
            Map.TILE_SIZE_PIXELS.toFloat()
        )
    }

    fun updateConnections() {
        spriteSheetOffset =
            connectionExists(Vec2(0, -1)) +
                    2 * connectionExists(Vec2(-1, 0)) +
                    4 * connectionExists(Vec2(0, 1)) +
                    8 * connectionExists(Vec2(1, 0))
    }

    protected fun connectionExists(offset: Vec2<Int>): Int {
        val other = map.getTile(loc + offset)
        return if (other?.connectionClass == connectionClass) 1 else 0
    }

    override fun onMapPlace() {
        updateConnections()
        super.onMapPlace()
    }
}

