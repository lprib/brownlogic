abstract class LogicGate(loc: Vec2<Int>, map: Map, spriteSheetIndex: Int) :
    SpriteSheetTile(loc, spriteSheetIndex, map, 1), RedstonePropagate {
    override fun propagateActivation() {
        val neighbors = getNeighbors()
        val output = eval(
            neighbors[3]?.logicValue() ?: false,
            neighbors[3]?.logicValue() ?: false,
            neighbors[3]?.logicValue() ?: false
        )

        val outputTile = neighbors[2]
        if (output && outputTile is RedstonePropagate) {
            outputTile.propagateActivation()
        }
    }


    abstract fun eval(a: Boolean, b: Boolean, c: Boolean): Boolean
}

class AndGate(loc: Vec2<Int>, map: Map) : LogicGate(loc, map, 9) {
    override fun eval(a: Boolean, b: Boolean, c: Boolean): Boolean = a && b && c
}

class OrGate(loc: Vec2<Int>, map: Map) : LogicGate(loc, map, 8) {
    override fun eval(a: Boolean, b: Boolean, c: Boolean): Boolean = a || b || c
}

//todo reorganise files

class Lever(loc: Vec2<Int>, map: Map) :
    SpriteSheetTile(loc, 11, map, 1),
    Interactive, Update {
    var state = false
    private var changeQueued = false

    override fun interact() {
        changeQueued = true
        //swap the display texture. The actual redstone effect will happen in the update() method
        spriteSheetIndex = if (state) 11 else 12
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

class Lamp(loc: Vec2<Int>, map: Map) :
    SpriteSheetTile(loc, 13, map, 1), RedstoneListener {
    override fun onRedstoneState(state: Boolean) {
        spriteSheetIndex = if (state) 14 else 13
    }
}

class Bridge(loc: Vec2<Int>, map: Map) :
    SpriteSheetTile(loc, 15, map, 1), RedstonePropagate {
    override fun propagateActivation() {
        val n = getNeighbors()
        if (n[0]?.logicValue() == true) {
            val out = n[2]
            if (out is RedstonePropagate) out.propagateActivation()
        }
        if (n[1]?.logicValue() == true) {
            val out = n[3]
            if (out is RedstonePropagate) out.propagateActivation()
        }
        if (n[2]?.logicValue() == true) {
            val out = n[0]
            if (out is RedstonePropagate) out.propagateActivation()
        }
        if (n[3]?.logicValue() == true) {
            val out = n[1]
            if (out is RedstonePropagate) out.propagateActivation()
        }
    }
}

class Transistor(loc: Vec2<Int>, map: Map) :
    SpriteSheetTile(loc, 10, map, 1), RedstonePropagate {
    override fun propagateActivation() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}