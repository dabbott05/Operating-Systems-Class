import nu.pattern.OpenCV
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import org.opencv.videoio.VideoCapture
import org.opencv.videoio.Videoio
import org.opencv.imgcodecs.Imgcodecs
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities

fun main() {
    // Load OpenCV with bundled natives (must be first)
    OpenCV.loadShared()

    SwingUtilities.invokeLater {
        val frame = JFrame("Webcam Live Feed with Filter")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        val panel = VideoPanel()
        frame.contentPane.add(panel)

        val removeButton = JButton("Remove Filter")
        removeButton.addActionListener {
            panel.setGrayscale(false)
        }
        val applyButton = JButton("Apply Filter")
        applyButton.addActionListener {
            panel.setGrayscale(true)
        }

        frame.add(removeButton, "South")  // Button at bottom
        frame.add(applyButton, "North")

        frame.setSize(640, 520)  // Extra height for button
        frame.isVisible = true

        val cap = VideoCapture(0)
        if (!cap.isOpened) {
            println("Error: Could not open camera")
            return@invokeLater
        }

        cap.set(Videoio.CAP_PROP_FRAME_WIDTH, 640.0)
        cap.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480.0)

        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            val colorMat = Mat()
            val grayMat = Mat()
            while (true) {
                if (!cap.read(colorMat)) break

                val displayMat = if (panel.isGrayscale()) {
                    Imgproc.cvtColor(colorMat, grayMat, Imgproc.COLOR_BGR2GRAY)
                    grayMat
                } else {
                    colorMat
                }

                panel.updateImage(displayMat)
            }
            cap.release()
            colorMat.release()
            grayMat.release()
        }
    }
}

class VideoPanel : JPanel() {
    private var image: BufferedImage? = null
    private var currentMat: Mat? = null
    private val grayscaleFlag: AtomicBoolean = AtomicBoolean(false)

    fun isGrayscale(): Boolean = grayscaleFlag.get()

    fun setGrayscale(enabled: Boolean) {
        grayscaleFlag.set(enabled)
    }

    fun updateImage(mat: Mat) {
        currentMat = mat.clone()

        val width = mat.cols()
        val height = mat.rows()

        val channels = mat.channels()
        val type = if (channels > 1) BufferedImage.TYPE_3BYTE_BGR else BufferedImage.TYPE_BYTE_GRAY

        val bufferSize = channels * width * height
        val byteArray = ByteArray(bufferSize)
        mat[0, 0, byteArray]

        image = BufferedImage(width, height, type)
        val targetPixels = (image!!.raster.dataBuffer as DataBufferByte).data
        System.arraycopy(byteArray, 0, targetPixels, 0, byteArray.size)

        repaint()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        image?.let { g.drawImage(it, 0, 0, width, height - 40, null) }  // Adjust for button
    }
}
