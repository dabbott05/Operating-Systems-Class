import nu.pattern.OpenCV
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import org.opencv.videoio.VideoCapture
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
    OpenCV.loadLocally()

    SwingUtilities.invokeLater {
        val frame = JFrame("Webcam Live Feed with Filter") // makes our main frame . this isnt the video but the background
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE // when you click the red button it will exit the program

        val panel = VideoPanel() // this is the actual video panel that will be displayed
        frame.contentPane.add(panel) // adds the video panel onto the main frame

        val removeButton = JButton("Remove Filter") // create our remove button
        removeButton.addActionListener { // listens for button input
            panel.setGrayscale(false) // when button is pressed make it colored
        }
        val applyButton = JButton("Apply Filter") // create our apply button
        applyButton.addActionListener { // listens for button input
            panel.setGrayscale(true) // when button is pressed make it gray
        }

        frame.add(removeButton, "South")  // places the remove button at the bottom
        frame.add(applyButton, "North") // places the apply button at the top

        frame.setSize(500, 500) // sets the frame size
        frame.isVisible = true // we need this to actually see the GUI

        val cap = VideoCapture(0) // this is initializing our camera , 0 is the default camera (usually the laptop camera)
        if (!cap.isOpened) { // if the camera does not open we stop executing
            println("Error: Could not open camera")
            return@invokeLater // aborts the lambda . will not move towards the executor
        }

        //cap.set(Videoio.CAP_PROP_FRAME_WIDTH, 640.0)
        //cap.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480.0)

        val executor = Executors.newSingleThreadExecutor() // we need a seperate thread pool from the GUI so that the background
        // video proccesses do not interfere with the GUI . without this we wouldnt be able to click the buttons or resize the window
        // we only need one thread since only one task is being executed (the video loop)
        executor.execute { // starts the lambda . this will be what is executed in the background , frame by frame
            val colorMat = Mat() // create a matrix for our colored image . multi dimensional array for pixels
            val grayMat = Mat() // this matrix needs to be created since gray images and colored images use a different amount of channels
            while (true) { // infinite loop since video capture is continuous
                if (!cap.read(colorMat)) break // takes next frame

                val displayMat = if (panel.isGrayscale()) { //  if the user presses apply filter button it changes it to grayscale
                    Imgproc.cvtColor(colorMat, grayMat, Imgproc.COLOR_BGR2GRAY)
                    grayMat
                } else { // if not keep no filter on
                    colorMat
                }
                Core.flip(displayMat, displayMat, 1) // mirrors the camera

                panel.updateImage(displayMat) // update the panel with correct display . copies pixels from a MAT format to a BufferedImage format
            }
            cap.release()
            colorMat.release() // we need to release everything upon close . its good practice and prevents memory leaks
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
        currentMat = mat.clone() // clone the matrix for safety reasons

        val width = mat.cols() // the amount of columns of pixels we have is the width of the panel
        val height = mat.rows() // the amount of rows of pixels we have is the height of the panel

        val channels = mat.channels() // get how many channels we have (1 = grayscale 3 = color)
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
