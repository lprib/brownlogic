import processing.core.PGraphics
import processing.core.PImage

abstract class Tile(val loc: Vec2<Int>, val map: Map, val connectionClass: Int = 0) {
    abstract fun draw(g: PGraphics)

    //up, left, down, right
    //ie clockwise starting with up
    fun getNeighbors(): Array<Tile?> {
        return arrayOf(
            map.getTile(loc + Vec2(0, -1)),
            map.getTile(loc + Vec2(-1, 0)),
            map.getTile(loc + Vec2(0, 1)),
            map.getTile(loc + Vec2(1, 0))
        )
    }

    //called after this tile has been inserted into the map
    //ie neighbor tiles know about this tile
    open fun onMapPlace() {
        updateNeighborConnectedTextures()
    }

    private fun updateNeighborConnectedTextures() {
        for (n in getNeighbors()) {
            if (n is ConnectedTile) {
                n.updateConnections()
            }
        }
    }

    fun logicValue(): Boolean = this is LogicState && this.state
}

//Things that can be powered by switches, etc
//this does NOT include being powered by redstone dust, that is handled by RedstonePropagate
interface Powerable {
    var powered: Boolean
}

//redstone signal will propagate to tiles which implement this
interface RedstonePropagate {
    fun propagateActivation()
}

//tiles that update each frame
interface Update {
    fun update()
}

//tiles that can be clicked
interface Interactive {
    fun interact()
}

//tiles that have a logic state that can be examined by logic gates, etc
interface LogicState {
    var state: Boolean
}


//for tiles which are affected by redstone but DO NOT output redstone themselves
//(use RedstonePropagate if you want be to part of the propagation process and
//send out your own redstone propagations

//this is run after all redstone has been propagated
interface RedstoneListener {
    fun onRedstoneState(state: Boolean)
}