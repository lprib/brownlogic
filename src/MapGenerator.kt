import processing.core.PApplet

object OpenSimplexGenerator {
    fun generate(seed: Long, ss: SpriteSheet): Map {
        val noise = OpenSimplexNoise(seed)
        val map = Map(ss, 32, 32)
        forXY(map.width, map.height) { x, y ->
            val n = noise.eval(x * 0.1, y * 0.1)
            val index = PApplet.map(n.toFloat(), -1f, 1f, 0f, 7f).toInt()
            map.setTile(Vec2(x, y), SpriteSheetTile(Vec2(x, y), index, map))
        }
        return map
    }
}