import com.sun.org.apache.xpath.internal.operations.And
import processing.core.PApplet
import processing.event.KeyEvent
import processing.event.MouseEvent
import kotlin.random.Random

fun main(args: Array<String>) {
    PApplet.main("Main");
}

class Main : PApplet() {
    lateinit var sheet: SpriteSheet
    lateinit var map: Map
    lateinit var inventory: Inventory
    var inventoryIndex = 0

    override fun settings() {
        size(512, 512);
        noSmooth()
    }

    override fun setup() {
        sheet = SpriteSheet(loadImage("spritesheet2.png"), 16, 16, 8)
//        map = OpenSimplexGenerator.generate(69, sheet)
        map = Map(sheet, 16, 16)
        inventory = Inventory(sheet)
    }

    override fun draw() {
        background(128)
        image(inventory.entries[inventoryIndex].displayImage, 0f, 0f, 64f, 64f)
        map.update()
        map.draw(this.graphics)
    }

    override fun mousePressed(event: MouseEvent) {
        val loc = map.tilePositionFromScreenPosition(Vec2(event.x, event.y)) ?: return
        if (mouseButton == RIGHT) {
            val newTile = inventory.entries[inventoryIndex].constructor(loc, map)
            map.setTile(loc, newTile)
        } else {
            val pressedTile = map.getTile(loc)
            if (pressedTile is Interactive) {
                pressedTile.interact()
            }
        }
    }

    override fun keyPressed(event: KeyEvent) {
        when (event.key) {
            '[' -> {
                inventoryIndex--
                if (inventoryIndex == -1) {
                    inventoryIndex = inventory.entries.size - 1
                }
            }
            ']' -> {
                inventoryIndex++
                if (inventoryIndex == inventory.entries.size) {
                    inventoryIndex = 0
                }
            }
        }
    }
}