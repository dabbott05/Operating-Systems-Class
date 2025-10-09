import java.lang.Thread

class MyRunnable : Runnable {
    private val lock = Any() // The Any() Class is Kotlin's root class for non-nullable types (ex: String, Int)
    // it's just the safest class to use because we aren't going to use it anywhere else

    override fun run() { // Override the original run() function and replace with ours
        for (i in 1..10) {
            Thread.sleep(1000L) // 1 second long. L defines 1000 as a long since that is what .sleep() expects
            /*
            "When we use a synchronized block, Java internally uses a monitor, also known as a monitor lock or intrinsic
            lock, to provide synchronization. These monitors are bound to an object; therefore,
            all synchronized blocks of the same object can have only one thread executing them at the same time"
            https://www.baeldung.com/java-synchronized
             */
            synchronized(lock) { // synchronized is a built-in function that ensures
                                // threads execute this function one at a time. They are still running concurrently in the OS
                println("$i  Thread: ${Thread.currentThread().name}")
                // returns the index and name of the currentThread running
            }
        // lock gets dropped
        }
    }
}

fun main(){

    val myrunnable1 = MyRunnable() // create an instance of MyRunnable()
    val thread1 = Thread(myrunnable1, "Thread1")
    // makes val1 a Thread object, makes myrunnable1 the target and sets the name
    // the OS does not allocate this thread yet only the Thread object in memory

    val myrunnable2 = MyRunnable() // ^^^
    val thread2 = Thread(myrunnable2, "Thread2")

    // priorities tell the CPU how much time should be dedicated to each thread. A thread with a higher priority gets
    // more time in the CPU. affects the .sleep function because priority doesn't affect sleep time but does affect
    // the race for each thread to execute the synchronized() block. giving high priority threads the benefit

    // thread1.priority = Thread.MIN_PRIORITY // sets priority. MIN_PRIORITY = 1, NORM_PRIORITY = 5, MAX_PRIORITY = 10
    // thread2.priority = Thread.MAX_PRIORITY // you can set a custom priority 1 -10 by doing, thread2.priority = 8
                                                // .priority ALWAYS expects an integer. .priority is the equivalent to Java's .setPriority()

    thread1.start() // The thread is executed and JVM allocates an OS level thread and schedules run() to execute
    thread2.start() // ^

    thread1.join() // I've got to wait for the thread to be done. helps with synchronization, no premature exits, and
            // resource cleanup from the threads. only called after start()
    thread2.join()

    println("Threads are done") // waits for both threads to be done before printing (thanks to the .join())
}

/*

EXAMPLE OUTPUT

1  Thread: Thread1
1  Thread: Thread2
2  Thread: Thread1
2  Thread: Thread2
3  Thread: Thread2
3  Thread: Thread1
4  Thread: Thread2
4  Thread: Thread1
5  Thread: Thread1
5  Thread: Thread2
6  Thread: Thread2
6  Thread: Thread1
7  Thread: Thread2
7  Thread: Thread1
8  Thread: Thread2
8  Thread: Thread1
9  Thread: Thread2
9  Thread: Thread1
10  Thread: Thread2
10  Thread: Thread1
Threads are done

 */


// Research shared locks so its flawless everytime
// explore more about Threads and different things that can be done
// Dive into how priority works deeper within the OS
// commit test
