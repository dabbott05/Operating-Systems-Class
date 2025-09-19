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

# roughDraft335Project
    The goal of this project is to allow students to understand how a developer can use a current high level
    programming language to create threads, synchronize threads and set priorities for threads.
    Each team will have to research and find the appropriate programming language code that will
    show how to create, synchronize, and change the priority of threads. The code needs to be able
    to:
    Create at minimum 5 threads, each thread needs to accomplish one
    task/function/method
    Must demonstrate that the threads are working in order and not randomly, they must
    be synchronized in some manner. The output must be able to show when they are not
    synchronized how random things happen, and then when synchronized the calls happen
    in order, by using synchronization.
    Must also demonstrate using at least 3 threads the use of giving specific priorities to the
    threads. Show what priority they have naturally and then show them giving them a
    specific priority and how it affects the code.
    The code that each team writes will include creating instances of different classes and using
    methods/functions in those classes
    Each team will then have to research what exactly those classes and methods do
    Each team will explain how these classes and methods perform the requirements as listed
    above
    Each team will need to explain specifically how each line of the code works and what its
    purpose is.
    Each team and team member will need to show a full understanding of all the code and
    the pros and cons of using the code the team selected
    Each team member will be called on and asked questions about the code. Team members will
    all have to understand and comprehend all the code and be ready to report on any part of the
    code.
    Showing and explaining code may be done to just the instructor or to the whole class
    It will be important that each team member has a complete understanding of everything
    the team is presenting
    This assignment is going to be graded not on just the code and quick explanation, but on each team
    member’s depth of understanding of the material. Each team has some people who are better
    programmers than other team members. There will have to be much collaboration amongst the team to
    help educate each other

# to-do
    explore more about Threads and different things that can be
    done Dive into how priority works deeper within the OS
    start thinking of what tasks should be done for the project
    meet with team
