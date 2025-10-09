import java.util.concurrent.locks.ReentrantLock;
import kotlin.concurrent.thread
class Twentylines(val name : String) : Runnable {
    override fun run() {
        println("$name running")
        println("Thread priority: ${Thread.currentThread().priority}")
        for (i in 0..5) { println(i)
        }
    }
}
fun main() {
    println("=========UNSYNC=========")
    val tasks = ArrayList<Thread>()
    repeat(5) { tasks.add(thread { Twentylines("Thread$it").run() }) };
    tasks.forEach { it.join() }
    println("====SYNC====")
    val lock = ReentrantLock()
    val condition = lock.newCondition()
    var count = 0
    repeat(5) { i ->
        tasks.add(thread {
            lock.lock()
            try {
                while (count != i) {
                    condition.await()
                }; Twentylines("task $i").run(); count++; condition.signalAll()
            } finally {
                lock.unlock()
            }
        })
    }
    tasks.forEach {
        it.join()
    } // wait for all threads to finish before proceeding. .join() - blocks the main thread until each worker thread completes
    println("====priorities====")
    tasks.clear()
    repeat(5) { i ->
        tasks.add(thread(start = false) { Twentylines("Thread$i").run() })
    }
    tasks[0].priority = Thread.MAX_PRIORITY // 10
    tasks[1].priority = 8
    tasks[2].priority = 5 // default
    tasks[3].priority = 3
    tasks[4].priority = Thread.MIN_PRIORITY // 1

    tasks.forEach { it.start() }
    tasks.forEach { it.join() }
    
}
