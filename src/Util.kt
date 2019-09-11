fun forXY(x: Int, y: Int, func: (Int, Int) -> Unit) {
    for (i in 0 until x) {
        for (j in 0 until y) {
            func(i, j)
        }
    }
}

data class Vec2<T>(val x: T, val y: T)

//INCLUSIVE on x0, y0, EXCLUSIVE on x1, y1
fun <T : Comparable<T>> Vec2<T>.withinRect(x0: T, y0: T, x1: T, y1: T): Boolean =
    x >= x0 && y >= y0 && x < x1 && y < y1

operator fun Vec2<Int>.plus(other: Vec2<Int>) = Vec2(x + other.x, y + other.y)