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

        val captureButton = JButton("Capture Filtered Image")
        captureButton.addActionListener {
            panel.captureFilteredImage()
        }
        frame.add(captureButton, "South")  // Button at bottom

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
            val mat = Mat()
            while (true) {
                if (!cap.read(mat)) break

                // Apply filter: Grayscale
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2GRAY)

                panel.updateImage(mat)
            }
            cap.release()
        }
    }
}

class VideoPanel : JPanel() {
    private var image: BufferedImage? = null
    private var currentMat: Mat? = null

    fun updateImage(mat: Mat) {
        currentMat = mat.clone()

        val channels = mat.channels()
        val type = if (channels > 1) BufferedImage.TYPE_3BYTE_BGR else BufferedImage.TYPE_BYTE_GRAY

        val bufferSize = channels * mat.cols() * mat.rows()
        val byteArray = ByteArray(bufferSize)
        mat[0, 0, byteArray]

        image = BufferedImage(mat.cols(), mat.rows(), type)
        val targetPixels = (image!!.raster.dataBuffer as DataBufferByte).data
        System.arraycopy(byteArray, 0, targetPixels, 0, byteArray.size)

        repaint()
    }

    fun captureFilteredImage() {
        currentMat?.let {
            Imgcodecs.imwrite("captured_filtered.jpg", it)
            println("Captured and saved filtered image!")
        }
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        image?.let { g.drawImage(it, 0, 0, width, height - 40, null) }  // Adjust for button
    }
}
