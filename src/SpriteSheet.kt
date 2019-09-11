import processing.core.PImage

class SpriteSheet(image: PImage, width: Int, height: Int, spriteDimension: Int) {
    val sprites: Array<PImage> = (0 until height).flatMap { y ->
        (0 until width).map { x ->
            image.get(x * spriteDimension, y * spriteDimension, spriteDimension, spriteDimension)
        }
    }.toTypedArray()

    operator fun get(index: Int): PImage = sprites[index]
}