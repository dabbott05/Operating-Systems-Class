import kotlin.concurrent.thread
import java.util.concurrent.locks.ReentrantLock

class Worker(val name: String) : Runnable {
    override fun run() = println("$name running") // simple task for the threads to print
}

fun main() {
    println("======NO SYNC======") // header
    // create 5 threads and have them execute the run() function
    val tasks = List(5) {thread {Worker("Task $it").run()}}
    tasks.forEach {it.join()} // for each thread .join()

    println("======SYNC======")
    val lock = ReentrantLock() // instance of ReentrantLock class. provides a mutual exclusion lock allowing threads to lock and unlock
    val condition = lock.newCondition() // Condition object. allows threads to wait for a specific condition to be met before continuing
    var count = 0 // shared counter to check which threads turn it is

    val syncTasks = List(5) { i -> // create lambda function. i is used for task order. each iteration of i executes the lambda function
        thread{ // threads will run concurrently, not at the same time but in the same period of time
            lock.lock() // acquires the lock. ensures each thread executes the following blocks one at a time since count and condition cant be executed concurrently
            try { // if something goes wrong we need the lock to unlock in the finally clause
                while (count != i) { // the thread checks to see if it is its turn to run
                    condition.await() // .await(), "Causes the current thread to wait until it is signalled or interrupted." https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/Condition.html
                }
                Worker("Task $i").run() // creates and runs an instance of the Worker class with the name Task $i
                count++ // increment so that the next thread in line can proceed
                condition.signalAll() //  wake up all the threads that were hit with the .await() method
            }
            finally {
                lock.unlock() // need to unlock the lock so the next thread can execute the lambda function
            }
        }

    }
    syncTasks.forEach() {it.join()} // wait for all threads to finish before proceeding. .join() - blocks the main thread until each worker thread completes

    println("======Priorities======")
    val pTasks = List(5) { i -> // create 5 threads and a lambda function with code for each thread to execute
        val t = thread(start = false) { // we do not start the threads right away
            println("Thread ${i+1} priority: ${Thread.currentThread().priority}") // print the current thread and its priority
            repeat(2) { // repeat this function twice
                println("Thread ${i+1}: Step ${it+1}") // i is used for our thread task count and it is used to show what iteration of the repeat() function we are one
                Thread.sleep(10)  // small delay
            }
        }
        // most modern operating system schedulers ignore priorities since they think they know better
        if (i == 0) {
            t.priority = 2  //4
        }
        else if (i == 1) {
            t.priority = 1 //5
        }
        else if (i == 2) {
            t.priority = 9 //2
        }
        else if (i == 3) {
            t.priority = Thread.MAX_PRIORITY //1
        }
        else if (i == 4) {
            t.priority = 6 //3
        }
        else{
            println("Something super weird just happened")
        }
        t  // return thread object so pTasks will be a list of them
    }
    pTasks.forEach { it.start() }  // start all
    pTasks.forEach { it.join() }   // wait for completion

    }


