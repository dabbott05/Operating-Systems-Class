# PROJECT 1 RUBRIC
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

# threadTest.kt
    This is my first version of the multi-threading kotlin project.
    
    Implements what ive learned so far about Threads in the 
    Kotlin/JVM frameworks. A synchronized block is added
    allowing the first pairs of threads to print before the next
    pair of threads. Does not garuntee thread1 will print before 
    thread2. It is simply a race to whoever executes the 
    synchronize() block first.

# semaphorTest.kt
    This is my  second version of the ulti-threaded kotlin project

    Implements what i have learned so far about Threads and
    Semaphors in the Kotlin/JVM frameworks. Allows Thread1 to
    always print before Thread2 does. I did alot of research regarding 
    semaphores and how they can help us obtain synchronization

# roughDraft335Project.kt 
    This is the rough draft of the multi-threaded kotlin project. I 
    did alot of research and decided that using a Reentrant lock and 
    Conditions was the best way to go about it. Reentrant locks help 
    with only allowing one thread to execute the critical section at a single time.
    Conditions help us change the state of the current thread holding the lock

# FinalProject.kt
    This is the final program we submitted. It builds off of the rough draft but 
    is way more polished. This is the program we presented to the class with.
