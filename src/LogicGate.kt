import processing.core.PGraphics

class AndGate(loc: Vec2<Int>, map: Map) :
    SpriteSheetTile(loc, 41, map, 1), RedstonePropagate {
    override fun propagateActivation() {
        val left = map.getTile(loc + Vec2(-1, 0))
        val right = map.getTile(loc + Vec2(1, 0))
        val output = map.getTile(loc + Vec2(0, 1))

        if (left is Redstone && left.state && right is Redstone && right.state && output is RedstonePropagate) {
            output.propagateActivation()
        }
    }

}

//todo reorganise files

class Lever(loc: Vec2<Int>, map: Map) :
    SpriteSheetTile(loc, 43, map, 1),
    Interactive, Update {
    private var state = false
    private var changeQueued = false

    override fun interact() {
        changeQueued = true
        //swap the display texture. The actual redstone effect will happen in the update() method
        spriteSheetIndex = if (state) 43 else 44
    }

    override fun update() {
        if (changeQueued) {
            state = !state
            changeQueued = false
        }
        if (state) {
            getNeighbors().filterIsInstance<RedstonePropagate>().forEach { it.propagateActivation() }
        }
    }

}