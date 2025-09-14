# main
    Both files simply iterate 2 different threads 10 times. There
    is priority added but I commented it out.

# threadTest.kt

    Implements what ive learned so far about Threads in the 
    Kotlin/JVM frameworks. A synchronized block is added
    allowing the first pairs of threads to print before the next
    pair of threads. Does not garuntee thread1 will print before 
    thread2. It is simply a race to whoever executes the 
    synchronize() block first.

# semaphorTest.kt
    Implements what i have learned so far about Threads and
    Semaphors in the Kotlin/JVM frameworks. Allows Thread1 to
    always print before Thread2 does.

# to-do
    explore more about Threads and different things that can be
    done Dive into how priority works deeper within the OS
    start thinking of what tasks should be done for the project
    meet with team
