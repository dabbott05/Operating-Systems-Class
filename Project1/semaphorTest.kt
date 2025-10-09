import java.lang.Thread
import java.util.concurrent.Semaphore

class MyRunnable : Runnable {
    companion  object {
        private val sem1 = Semaphore(1) // make sem1 a Semaphor instance with 1 permit
        private val sem2 = Semaphore(0) // make sem2 a Semaphor instance with 0 permits
    }
    override fun run() { // Override the original run() function and replace with ours
        val threadName = Thread.currentThread().name
        val isThread1 = threadName == "Thread1" // isThread1 becomes a boolean type. If its named "Thread1" it will return True

        for (i in 1..10) {
            Thread.sleep(1000L) // 1 second long. L defines 1000 as a long since that is what .sleep() expects
            if (isThread1) { // if isThread == "Thread1"
                sem1.acquire() // since sem 1 already has a permit we call the .acquire function to decrement its permits by 1 and allows it to print
                println("Thread$i is $threadName") // prints the thread # and the threadName. sem1 releases a permit after printing
                sem2.release() // increments sem2 permit to the count of 1. it is now no longer waiting and can aqcuire a permit
            }
            else {
                sem2.acquire()
                println("Thread$i is ${Thread.currentThread().name}")
                sem1.release()
            }
        }
    }
}


fun main(){

    val myrunnable1 = MyRunnable() // create an instance of MyRunnable()
    val thread1 = Thread(myrunnable1, "Thread1")
    // makes val1 a Thread object, makes myrunnable1 the target and sets the name
    // the OS does not allocate this thread yet only the Thread object into memory

    val myrunnable2 = MyRunnable() // ^^^
    val thread2 = Thread(myrunnable2, "Thread2")

    // priorities tell the CPU how much time should be dedicated to each thread. A thread with a higher priority gets
    // more time in the CPU. affects the .sleep function because priority doesn't affect sleep time.

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

Thread1 is Thread1
Thread1 is Thread2
Thread2 is Thread1
Thread2 is Thread2
Thread3 is Thread1
Thread3 is Thread2
Thread4 is Thread1
Thread4 is Thread2
Thread5 is Thread1
Thread5 is Thread2
Thread6 is Thread1
Thread6 is Thread2
Thread7 is Thread1
Thread7 is Thread2
Thread8 is Thread1
Thread8 is Thread2
Thread9 is Thread1
Thread9 is Thread2
Thread10 is Thread1
Thread10 is Thread2
Threads are done

 */


// explore more about Threads and different things that can be done
// Dive into how priority works deeper within the OS
// start thinking of what tasks should be done for the project
// meet with team
