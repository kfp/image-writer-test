import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO
import java.io.ByteArrayInputStream
import javax.imageio.stream.ImageInputStream
import java.io.FileInputStream



fun main(args: Array<String>) {
    println("Hello, world!")

    val width = 1920
    val height = 1080
    val list = ArrayList<BufferedImage>()
    val f = File("MyFile.png")

    for (i in 1..180){

        val img = BufferedImage(
                width, height, BufferedImage.TYPE_INT_RGB)

            for (x in 0..width-1) {
                for (y in 20..height-1) {
                    val r = Random().nextInt(255)
                    val g = Random().nextInt(255)
                    val b = Random().nextInt(255)
                    val col = r shl 16 or (g shl 8) or b
                    img.setRGB(x, y, col)
                }
            }
        list.add(img)
    }
    println("Created all images")
//    for(img in list) {
//        val timeStart = System.currentTimeMillis();
//        ImageIO.write(img, "PNG", f)
//        println("Total time: " + (System.currentTimeMillis() - timeStart))
//    }
    val timeStart = System.currentTimeMillis();
    test(list)
    println("Total time: " + (System.currentTimeMillis() - timeStart))
}


fun test(imgs: ArrayList<BufferedImage>){
    val ffmpeg_output_msg = File("ffmpeg_output_msg.txt")
    val pb = ProcessBuilder(
        "ffmpeg", "-i", "pipe:0", "out.avi"
    )
    pb.redirectErrorStream(true)
    pb.redirectOutput(ffmpeg_output_msg)
    pb.redirectInput(ProcessBuilder.Redirect.PIPE)
    val p = pb.start()
    val ffmpegInput = p.outputStream

    for(img in imgs) {
        ImageIO.write(img, "JPEG", ffmpegInput)
    }
}