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
    // load OpenCV with bundled natives (must be first)
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

        frame.setSize(1280, 1280) // sets the frame size
        frame.isVisible = true // we need this to actually see the GUI

        val cap = VideoCapture(0) // this is initializing our camera , 0 is the default camera (usually the laptop camera)
        if (!cap.isOpened) { // if the camera does not open we stop executing
            println("Error: Could not open camera")
            return@invokeLater // aborts the lambda . will not move towards the executor
        }

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
    var image: BufferedImage? = null
    var currentMat: Mat? = null
    val grayscaleFlag: AtomicBoolean = AtomicBoolean(false)

    fun isGrayscale(): Boolean = grayscaleFlag.get()

    fun setGrayscale(enabled: Boolean) {
        grayscaleFlag.set(enabled)
    }

    fun updateImage(mat: Mat) { // this method converts the image MAT to a buffered image so that Swing can display the image
        currentMat = mat.clone() // clone the matrix for safety reasons

        val width = mat.cols() // the amount of columns of pixels we have is the width of the panel
        val height = mat.rows() // the amount of rows of pixels we have is the height of the panel

        val channels = mat.channels() // get how many channels we have (1 = grayscale 3 = color)
        val type = if (channels > 1) BufferedImage.TYPE_3BYTE_BGR else BufferedImage.TYPE_BYTE_GRAY

        val bufferSize = channels * width * height
        val byteArray = ByteArray(bufferSize)
        mat[0, 0, byteArray] // start at col 1 and row 1 and copy it into byteArray

        image = BufferedImage(width, height, type) // creates an instance of the BufferedImage . BufferedImage is
        // optimized for pixel management . Swing cannot draw a MAT type so thats why we convert it to a BufferedImage .
        // BufferedImage represents the image in memory
        val targetPixels = (image!!.raster.dataBuffer as DataBufferByte).data // image!! - assumes that image is NOT NULL .
        // raster - BufferedImage internally use raster objects to manage the pixel data . We get the dataBuffer which holds the
        // actual data and cast it as a DataBufferByte . we cast it because BufferedImage uses type byte .
        // .data - accesses the data property of DataBufferByte, which is the raw ByteArray holding all the pixel bytes
        System.arraycopy(byteArray, 0, targetPixels, 0, byteArray.size) // copies the byteArray
        // starting at position 0 to targetPixels at position 0 with the size of our byteArray

        repaint() // "something changed you need to call me"
    }

    override fun paintComponent(g: Graphics) { // we override becaus eby default JPanel paints a black screen
        super.paintComponent(g) // clears background , paints borders . super calls the superclass of JPanel
        image?.let { g.drawImage(it, 0, 0, width, height, null) }
        // image? makes sure that image is not null . we need to safely draw the BufferedImage onto the panel . .let
        // runs the code block in the curly braces . it stands for image . .let helps us avoid using if statements by saying
        // "If this thing exists, let me do something with it." .  we pass null since our image is already loaded in memory .
        // the observer monitors image loading
    }
}
