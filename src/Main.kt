import processing.core.PApplet
import processing.event.MouseEvent
import kotlin.random.Random

fun main(args: Array<String>) {
    PApplet.main("Main");
}

class Main : PApplet() {
    lateinit var sheet: SpriteSheet
    lateinit var map: Map
    val rand = Random(4)

    override fun settings() {
        size(512, 512);
        noSmooth()
    }

    override fun setup() {
        sheet = SpriteSheet(loadImage("spritesheet.png"), 8, 8, 8)
//        map = OpenSimplexGenerator.generate(69, sheet)
        map = Map(sheet, 16, 16)
    }

    override fun draw() {
        background(128)
        map.update()
        map.draw(this.graphics)
    }

    override fun mousePressed(event: MouseEvent) {
        val loc = map.tilePositionFromScreenPosition(Vec2(event.x, event.y)) ?: return
        if (event.isControlDown) {
            val newTile = Lever (loc, map)
            map.setTile(loc, newTile)
            newTile.updateNeighborConnectedTextures()
        } else if (event.isShiftDown) {
            val newTile = Redstone(loc, map)
            map.setTile(loc, newTile)
            newTile.updateConnections()
            newTile.updateNeighborConnectedTextures()
        } else {
            val tile = map.getTile(loc)
            if (tile is Interactive) {
                tile.interact()
            }
        }
//        map.setTile(loc, SimpleTile(loc, 9, map))
//        println(map.tiles)
    }

    override fun keyPressed() {
        val tile = map.getTile(Vec2(0, 0))
        if (tile is Redstone) {
            tile.powered = !tile.powered
        }
    }
}