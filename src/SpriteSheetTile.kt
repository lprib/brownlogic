import processing.core.PGraphics

open class SpriteSheetTile(
    loc: Vec2<Int>,
    protected var spriteSheetIndex: Int,
    map: Map,
    connectionClass: Int = 0
) :
    Tile(loc, map, connectionClass) {

    override fun draw(g: PGraphics) {
        g.image(
            map.spriteSheet[spriteSheetIndex],
            (loc.x * Map.TILE_SIZE_PIXELS).toFloat(),
            (loc.y * Map.TILE_SIZE_PIXELS).toFloat(),
            Map.TILE_SIZE_PIXELS.toFloat(),
            Map.TILE_SIZE_PIXELS.toFloat()
        )
    }
}