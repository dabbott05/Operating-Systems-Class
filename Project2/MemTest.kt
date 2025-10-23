fun recursive(depth: Int, maxDepth: Int): Int {
    println(">>> PUSH Frame for depth $depth >>>")
    val localInt = depth // local variable so its stored on the stack
    val localString = "Depth $depth" // local variable reference . localString is a
    // reference to the Object type String . The actual string is stored on the heap .
    // so localString is stored on the stack and holds the memory address of the Object
    // which is stored on the heap

    println("Stack frame $depth") // println statement
    try {
        if (depth == maxDepth){
            return depth
        }
        // helps with stack overflow . if depth reaches
    // its maxDepth it should immediatly return . this pops the current frame off of the
    // call stack . The return tells the JVM: "I'm done—pass back any value (if not void)
    // and clean up this frame."

    return recursive(depth + 1, maxDepth) // recursively calls itself until depth == maxDepth (5)
    }
    finally{
        println("<<< POP Frame for depth $depth <<<")
    }

}

data class Box(val id: Int, val payload: ByteArray) // creates a new data class for Box (implicitly defining toString() and isEqual() methods) .
// parameters are id and payload . the box Object is stored on the heap and it makes room
// in the heap for the parameters as well

val boxes = mutableListOf<Box>() // create an ArrayList with the type Box . val boxes is
// stored as a reference (on the stack) to the ArrayList (on the heap)

fun addBox(id: Int, size: Int) {
    val payload = ByteArray(size) // ByteArray acts as our buffer . val payload is stored
    // as reference on the stack to the ByteArray which is stored on the heap
    boxes.add(Box(id, payload)) // add a new box into the boxes list with an id and a payload
    // we need to add the boxes to an ArrayList because if we didnt they could be GCed immeditaly
    println("Added Box#$id (${size} bytes)") // println stateent
}

fun main() {
    println("Stack Demo:") // Header
    recursive(1, 5) // call the recursive function which will add a new
    // frame onto the call stack
    println() // println statement for spacing

    println("Heap Demo:") // header
    repeat(100) { addBox(it + 1, 1024 * 2048) } // repeat 3 times , add a new box
    // with an id and a size for the payload . each allocates 1MB of memory onto the heap

    Thread.sleep(50000) // sleep for 50 seconds to allow profiling
}
