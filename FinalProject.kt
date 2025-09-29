import java.util.concurrent.locks.ReentrantLock // imports our locks and our consitions
import kotlin.concurrent.thread // this also implements runnable
class Twentylines(val name : String) : Runnable {
    override fun run() { // we need to override the run() method since Runnable comes with one defined
        println("$name running") // simple print statement
        for (i in 0..5) { // loop through 0-5 printing 6 integers
            println(i) // print i
        }
    }
}
fun main() { // main method
    println("=========UNSYNC=========") // header
    val tasks = ArrayList<Thread>() // create a variable called tasks that is an ArrayList full of Threads
    repeat(5) { tasks.add(thread { Twentylines("Thread$it").run() }) } // repeat 5 times; add a thread to tasks, make the target of the thread an instance of the TwentyLines class, and run it
    tasks.forEach { it.join() } // when all threads are created and started join them together so we dont get ahead of the main method
    println("====SYNC====") // header
    val lock = ReentrantLock() // make lock an instance of our Reentrant Lock class
    val condition = lock.newCondition() // make condition an instance of our Reentrant Lock class, more specifically the Condition Class which is included in the Reentrant lock class
    var count = 0 // mutable counter
    repeat(5) { i -> // repeat our lambda 5 times with i as our iterator
        tasks.add(thread { // add a thread to tasks list
            lock.lock() // lock the lock
            try { // need to try/finally because we need to unlock if something goes wrong
                while (count != i) { // while it is not the current threads turn
                    condition.await() // go into a wait state until awoken
                }
                Twentylines("task $i").run()// if its the current threads turn, make it an instance of the TwentyLines class adn run it
                count++ // increment count so the other threads can continue in order
                condition.signalAll() // tell all threads that are in the wait state to wake up and check the while loop
            } finally {
                lock.unlock() // unlock lock so another thread can grab it
            }
        })
    }
    tasks.forEach {
        it.join()
    } // wait for all threads to finish before proceeding. .join() - blocks the main thread until each worker thread completes
    tasks.clear()
    println("====priorities====")
    repeat(5) { i ->  // repeat 5 times with i as our iterator, add threads to tasks but dont start them
        tasks.add(thread(start = false) { Twentylines("Thread$i").run() })}
        tasks[0].priority = Thread.MAX_PRIORITY // 10
        tasks[1].priority = 8
        tasks[2].priority = 5 // default
        tasks[3].priority = 3
        tasks[4].priority = Thread.MIN_PRIORITY // 1

    repeat(5) { i ->
        tasks[i].start()
        tasks[i].join()
    }
}
