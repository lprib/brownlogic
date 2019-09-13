import processing.core.PGraphics

class Redstone(loc: Vec2<Int>, parent: Map) : ConnectedTile(loc, 16, 1, parent),
    Powerable, RedstonePropagate, Update, LogicState {
    override var state = false
    var updatedThisPass = false
//    var state = false
    override var powered = false

    override fun draw(g: PGraphics) {
        val image = map.spriteSheet[spriteSheetStartIndex + spriteSheetOffset + if (state) 16 else 0]
        g.image(
            image,
            (loc.x * Map.TILE_SIZE_PIXELS).toFloat(),
            (loc.y * Map.TILE_SIZE_PIXELS).toFloat(),
            Map.TILE_SIZE_PIXELS.toFloat(),
            Map.TILE_SIZE_PIXELS.toFloat()
        )
    }

    override fun update() {
        if (powered && !state) {
            //flipping from off to on
            propagateActivation()
        }
    }

    override fun propagateActivation() {
        if (!updatedThisPass) {
            state = true
            updatedThisPass = true

            for (n in getNeighbors()) {
                if (n is RedstonePropagate) {
                    n.propagateActivation()
                }
            }
        }
    }
}