## PRACTICAL EXAMINATION
Below you will find a problem statement similar to what you can expect to receive during the practical examination. The problem statement will generally follow the requirements set out during this course, will require a graphical user interface (using JavaFX) and writing specifications, tests (if required) and the implementation of layered architecture.
Observations:
1. Solving the following problem statement completely should be possible for you in a time span of 60 minutes (Mathematics Computer Science), 90 minutes (Artificial Intelligence).
2. You are encouraged to bring your own laptop to the exam. You are free to use your preferred IDE. Make sure your IDE is set up correctly and it works!
3. You can start from the provided workspace and you are allowed to use any materials and resources, except for AI generated source code. But you must work individually!

## Problem statement (Mathematics Computer Science)
A bus company requires an automatic system for booking. All available routes are contained in a relational database table. A Route has a source city, a destination city, the departure and arrival times, the total available number of seats and the price for one ticket. Write a Java application, with a graphical user interface (using JavaFX), which allows the following:
1. Show all routes in a list (1p), sorted by departure city and departure time. Use Java streams for this sorting (1p). If you do not use Java streams, the maximum score is 0.5p.
2. The client has a window allowing them to choose the source city from a combo box. The destination city combo box will be automatically updated to contain only the cities reachable from the selected source city (2p).
3. After the client chooses the source and destination cities, a new list will show all available routes, for the selected cities, with the following information: source city, destination city, departure and arrival times, duration and ticket price (1.5p).
4. A client can select the desired route and book a given number of tickets. The available number of seats for that route must be updated (1p). The total price will be shown in the client’s window (2p). If there are not sufficient tickets available, the application will show a message in a new window (0.5p).

Bonus:
Show 2 client windows, which both update as the clients make bookings. Use the Observer design pattern. (1p)
1p of
