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

# PROJECT 2 RUBRIC
    The goal of this project is to allow students to understand how important managing computer memory
    is for a programmer. Programmers can do this by understanding three major concepts: 1) Stacks, 2)
    Heaps, and 3) Buffers. How each programming language works with the OS in managing this is different.
    Some languages allow for a programmer in that language to give very specific instructions in memory
    management and other languages really cede all control to the OS and there are minimal instructions
    the language gives for any type of memory management.
    • Each team will first have to research and learn not only the general concepts of what
    programming stacks, heaps, and buffers are, but then will have to learn how their specific OS
    and programming language works in managing those.
    • Each team will have to show some code that will reflect the findings of their research to show
    how a programmer, using the team’s specific programming language, would write code to
    effectively and efficiently use computer memory when it comes to stacks, heaps and buffers.
    • The team will also need to explain and demonstrate how the IDE they are using has or does not
    have any built-in tools to help in understanding a program’s memory usage.
    • The team can also demonstrate any OS tools that would help show the memory usage by a
    specific process. (optional)
    • Each team will then make a 15–20-minute presentation on all their research findings.
    Presentations will be made on Tuesday October 28.
    o It will be important that each team member have complete understanding of everything
    the team is presenting
    o There should be clear explanations and descriptions of all material presented and no
    just reading of information.
    o Make sure the presentations are well rehearsed.
    This assignment is going to be graded not on just the code and quick explanation, but on each team
    member’s depth of understanding of the material. I am expecting each team to be able to teach the
    rest of the class not only the code, but what the code does, how and when to use it compared to other
    code, and why your team decided to do it this way.
# MemTest.kt
    This is my final version of the memory management project. I did alot of research on stacks, heaps, and buffers.
    This project was coded in kotlin and it demonstrates the call stack (calling a function) and the allocation of heap memory. 
    Did research on how the JVM works with operating systems to manage the memory.

# PROJECT 3 RUBRIC
    The goal of this project is to allow students to understand how a developer can use a current high level
    programming language to connect to a device (webcam) and comprehend the different levels of the OS
    device stack that are implemented along the way.
    • Each team will write a simple program in the assigned programming language that will:
    o Make a connection to the computers webcam
    o Display the live image
    o Apply some type of filter and manipulate the live image in some way
    • Each team will need to research and find the appropriate APIs/Libraries necessary to import to
    connect a basic application to the computers webcam. The application must then capture a
    single picture from the webcam and display it in the app in some format.
    • The final step will be to add a filter to the displayed photo.
    • It will be necessary to get this to work on each team member’s individual computer.
    • On Thursday Nov. 6 each team will then have to show me the working code app on each team
    member’s computer.
    o The team will also have to describe to me the process the TEAM went through to find
    the correct solution and code.
    ▪ What websites the team used to find the solution
    ▪ Did the team use AI and how did it help and what did you learn
    o I also want to hear about all the problems the team ran into to get the solution to work
    on each computer and how they solved those problems.
    o I also want the team to explain to me what each part of the code does and why it is
    necessary
    This assignment is not going to be graded on just if the code works or does not work on each team
    member’s computer, but how well everyone worked together to solve the problem. If just one person
    finds the solution and just puts it on everyone’s computer, then the team will be significantly marked
    down. Every team member must be involved in finding the solution, applying the solution, and testing
    the solution and be able to explain the solution.

# WebcamProject.kt
    This is our final version of project 3 . It uses opencv to manage the webcam 
    and apply a filter . We had to alot of research on converting a MAT to a bufferedByte
    so that Swing can paint the image . We did lot of research on how images are stored
    in multidimensional arrays and how to manipulate the individual raw pixel data .

# PROJECT 4 RUBRIC
    The goal of this project is to allow team members to learn how to create a file, write information to
    the file and then read the information from the file. Programmers do not have to write lines and
    lines of code to do this. Most of the work is done by the OS. Programmers request the OS to create
    a file by passing in a file name and location. The OS will then work with the proper device to confirm
    the file can be created and then make the request to create the file. The device will report back to
    the OS that the file has been created, the OS will then add that information to the FAT, (File
    Allocation Table), that the OS manages. Once all that is done the OS will report back to the
    application that the file has been created. If there were problems at any point in that process the OS
    will send back an exception that the programmer can decide to handle and how to handle it.
    To write a file a programmer must decide how to layout the file format. How will the data be laid out
    and then the code will be sent to the OS to request that the data be written in the appropriate file
    layout. The other option is that the file is already create and the developer is only requesting the
    new data to be appended to the file. Again, the OS will work with the appropriate storage device to
    write the data and then update the OS file management tables. If all goes well the program will be
    notified a success code, if not an exception will be returned.
    To read from a file the programmer must first request the file be opened, meaning loaded into
    memory. This request is handled by the OS. The OS will work with the appropriate storage device to
    assure the file is present and available and has access clearance. If all those parameters are cleared
    the OS will load the file to RAM ready for the application to begin processing the file. The file status
    will be reported back to the application. The developer must have an understanding of the file
    format in order to write the application code to properly read and write to the file. The OS will notify
    the application when records are available to be worked with.
    For this project each team will write a total of six different programs. Some programs will create
    new files and write data to them, and some will open files and append data to the already created
    file. Some programs will open files and read data from the already created and written to files.
    Text Files:
    The first type of files that the team will work with will be text files. The format of these files is
    comma delimited with a line break separating records of data. This means each line in the file will
    hold one record of data that is broken into fields of data separated by commas. One record per line.
    • The team will create one program to create the file and write 10 records to it doing the
    following:
    o Create a file in a specific directory
    o Write 100 single line records to the file. You can use a random data generator to come
    up with the data.
    ▪ Each record will be made up of the following data:
    • First name
    • Last name
    • Date of birth
    • Phone number
    • Street address
    • City
    • State
    • Zip
    ▪ All attributes should be separated in each line by a comma
    ▪ Each record will be on a new line
    o Close and save the file
    • The team will create a second program that will open the file that was created in the first
    program and append 50 new records to it by performing the following:
    o Reopen the file and append 50 new records following the same format as listed above
    o Close and save the file
    • The team will create a third program that does the following:
    o Will open the file that has the 150 records in it and will read each of the records
    attributes and store the data in a list/array.
    o Will print out the data in the list/array in table format.
    Json Files:
    Each team will have to do some research and find out the layout/format of a Json file. Json files are
    the most popular way to transfer data between applications across the web. The format has a very
    strict layout and must be followed. If it is followed, then different applications can write and read
    the files very quickly. Each programming language in this course has an API that will help in writing
    and reading files in Json format.
    • Re do the same assignment as above but do all writing and reading in Json format.
    • You must use the appropriate API to do this
    • You will be creating three more programs.
