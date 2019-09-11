import processing.core.PGraphics

abstract class Tile(val loc: Vec2<Int>, val map: Map, val connectionClass: Int = 0) {
    abstract fun draw(g: PGraphics)

    //up, left, down, right
    //ie clockwise starting with up
    protected fun getNeighbors(): Array<Tile?> {
        return arrayOf(
            map.getTile(loc + Vec2(0, -1)),
            map.getTile(loc + Vec2(-1, 0)),
            map.getTile(loc + Vec2(0, 1)),
            map.getTile(loc + Vec2(1, 0))
        )
    }

    fun updateNeighborConnectedTextures() {
        for (n in getNeighbors()) {
            if (n is ConnectedTile && n.connectionClass == connectionClass) {
                n.updateConnections()
            }
        }
    }
}

interface Powerable {
    var powered: Boolean
}

interface RedstonePropagate {
    fun propagateActivation()
}

interface Update {
    fun update()
}

interface Interactive {
    fun interact()
}

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
}

