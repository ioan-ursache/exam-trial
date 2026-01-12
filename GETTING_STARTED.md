# Getting Started with Bus Booking System

## Repository Overview

This repository contains two branches:

### 1. **`main` branch** - Complete Solution
Full implementation of the bus booking system exam problem with:
- ✅ All requirements implemented
- ✅ Layered architecture (Model, Repository, Service, Controller, View)
- ✅ Java Streams for sorting
- ✅ Observer design pattern (BONUS)
- ✅ Complete FXML UI files
- ✅ SQLite database with sample data
- ✅ Comprehensive documentation

**Use this to:**
- Study the complete solution
- Understand the architecture
- See how requirements map to code
- Learn JavaFX best practices
- Reference for your own implementation

### 2. **`template` branch** - Starting Scaffold
Blank template with:
- ✅ Project structure ready
- ✅ Package organization
- ✅ Maven configuration (pom.xml)
- ✅ Empty class stubs with TODO comments
- ✅ FXML layout templates
- ✅ Database utilities
- ✅ Step-by-step implementation guide

**Use this to:**
- Start fresh with a clean scaffold
- Follow the template guide for implementation
- Practice building the system
- Test your understanding of the requirements

## Quick Start

### For the Complete Solution (main branch)

```bash
# Clone repository
git clone https://github.com/ioan-ursache/exam-trial.git
cd exam-trial

# Make sure you're on main branch
git checkout main

# Build the project
mvn clean install

# Run the application
mvn javafx:run
```

### For the Template (template branch)

```bash
# Clone repository
git clone https://github.com/ioan-ursache/exam-trial.git
cd exam-trial

# Switch to template branch
git checkout template

# Build the project
mvn clean install

# Follow TEMPLATE_GUIDE.md to implement the system
```

## Project Structure

```
exam-trial/
├── README.md                           # Original exam problem statement
├── IMPLEMENTATION.md                   # Complete solution documentation
├── GETTING_STARTED.md                  # This file
├── TEMPLATE_GUIDE.md                   # Template implementation guide
├── pom.xml                             # Maven configuration
├── .gitignore
└── src/
    └── main/
        ├── java/com/buscompany/
        │   ├── BusBookingApplication.java       # Entry point
        │   ├── model/Route.java                 # Domain model
        │   ├── repository/RouteRepository.java  # Database layer
        │   ├── service/RouteService.java        # Business logic
        │   ├── controller/                      # UI controllers
        │   │   ├── MainSceneController.java
        │   │   └── ClientWindowController.java
        │   └── util/DatabaseUtils.java          # Database utilities
        └── resources/
            ├── fxml/
            │   ├── main-scene.fxml              # Main window UI
            │   └── client-window.fxml           # Booking window UI
            └── application.properties             # Configuration
```

## Requirements Implementation

### Core Requirements

| # | Requirement | Points | Status | File |
|---|-------------|--------|--------|------|
| 1 | Display all routes sorted by city and time | 2 | ✅ | RouteService.getAllRoutesSorted() |
| 2 | Dynamic destination city filtering | 2 | ✅ | ClientWindowController.onSourceCitySelected() |
| 3 | Display available routes with details | 1.5 | ✅ | ClientWindowController.onDestinationCitySelected() |
| 4 | Select route and book tickets | 1 | ✅ | ClientWindowController.onBookTickets() |
| 5 | Validate seat availability | 0.5 | ✅ | RouteService.bookTickets() |
| 6 | Update available seats | 1 | ✅ | RouteRepository.updateAvailableSeats() |
| 7 | Show total price | 2 | ✅ | ClientWindowController.updateTotalPrice() |
| 8 | Error message for insufficient seats | 0.5 | ✅ | ClientWindowController.onBookTickets() |
| **BONUS** | **Observer pattern for multiple windows** | **1** | **✅** | **RouteService + Controllers** |
| **TOTAL** | | **10.5+** | **✅ COMPLETE** | |

## Key Features

### ✅ Java Streams for Sorting (Required)
```java
routes.stream()
    .sorted((r1, r2) -> {
        int sourceCityComparison = r1.getSourceCity().compareTo(r2.getSourceCity());
        if (sourceCityComparison != 0) {
            return sourceCityComparison;
        }
        return r1.getDepartureTime().compareTo(r2.getDepartureTime());
    })
    .collect(Collectors.toList())
```

### ✅ Dynamic City Filtering
- When source city selected → destination combo box updates automatically
- Only shows cities reachable from selected source
- No manual refresh needed

### ✅ Real-time Price Calculation
- Price updates as ticket count changes
- Spinner for easy quantity selection
- Total displayed in real-time

### ✅ Seat Availability Validation
- Checks available seats before booking
- Shows error if insufficient seats
- Updates database and UI on success

### ✅ Observer Pattern (BONUS)
- Multiple client windows can be open simultaneously
- When booking in one window, all other windows update
- All windows stay synchronized
- Uses JavaFX Observable pattern

## Technologies Used

### Core
- **Java 17+** - Programming language
- **JavaFX 22.0.1** - GUI framework
- **SQLite** - Database
- **Maven 3.6+** - Build tool

### Dependencies
- `org.openjfx:javafx-controls` - UI controls
- `org.openjfx:javafx-fxml` - XML-based UI markup
- `org.openjfx:javafx-graphics` - Graphics engine
- `org.xerial:sqlite-jdbc` - SQLite JDBC driver

## Documentation

### Main Documentation Files

1. **README.md**
   - Original exam problem statement
   - Requirements specification
   - Grading criteria

2. **IMPLEMENTATION.md** (main branch only)
   - Detailed architecture explanation
   - Layer-by-layer breakdown
   - How requirements are implemented
   - Database schema
   - Testing guide
   - Troubleshooting
   - Code quality notes

3. **TEMPLATE_GUIDE.md** (template branch only)
   - Step-by-step implementation guide
   - What to implement in each layer
   - Database schema with SQL
   - Testing checklist
   - Success criteria

4. **GETTING_STARTED.md** (this file)
   - Quick start instructions
   - Project overview
   - Requirements mapping
   - Technology stack

## Development Workflow

### Using the Main Branch
1. Study the code structure
2. Read IMPLEMENTATION.md
3. Run the application
4. Test different features
5. Reference while building your own version

### Using the Template Branch
1. Read README.md to understand requirements
2. Read TEMPLATE_GUIDE.md for implementation plan
3. Implement each layer following TODOs:
   - Model (Route class)
   - Repository (database operations)
   - Service (business logic, streams, observer)
   - Controllers (UI event handling)
   - FXML files (UI layout)
4. Test each feature as you implement
5. Compare with main branch when stuck

## Running the Application

### Build
```bash
# Build and compile
mvn clean install

# Or just clean and package
mvn clean package
```

### Run
```bash
# Using Maven JavaFX plugin (recommended)
mvn javafx:run

# Or run JAR directly
java -m javafx.controls target/bus-booking-system-1.0.0.jar
```

### Database
- Database is created automatically on first run
- Located at: `bus_booking.db` (SQLite)
- Sample data inserted automatically
- Delete `bus_booking.db` to reset and recreate

## Testing the Application

### Test Scenario 1: Routes Display
```
1. Run application
2. Main window shows all routes
3. Routes sorted by source city first, then departure time
4. Count routes and verify sorting
```

### Test Scenario 2: Dynamic Filtering
```
1. Click "Open Client Window"
2. Select "Bucharest" from source cities
3. Destination combo box shows: Brașov, Cluj, Constanța
4. Select "Cluj" from destinations
5. Routes list shows only Bucharest -> Cluj routes
```

### Test Scenario 3: Booking
```
1. Select a route
2. Change ticket count spinner to 10
3. Total price shows: 10 × route.price
4. Click "Book Now"
5. Success message appears
6. Seat count decreases by 10
```

### Test Scenario 4: Validation
```
1. Select route with 20 available seats
2. Try to book 30 tickets
3. Error message: "Not enough seats available"
4. Route details still show, can try again
```

### Test Scenario 5: Observer Pattern (BONUS)
```
1. Open Client Window #1
2. Open Client Window #2
3. In Window #1, select a route showing 50 available seats
4. In Window #2, select the same route (should show 50)
5. In Window #1, book 20 tickets
6. In Window #2, without clicking anything, the available seats update to 30
```

## IDE Setup

### IntelliJ IDEA
1. Open project folder
2. Maven should auto-detect pom.xml
3. Click "Load Maven Changes" if prompted
4. Configure JavaFX SDK:
   - File → Project Structure → Libraries
   - Add JavaFX library
5. Run → Run 'BusBookingApplication'

### Eclipse
1. File → Import → Existing Maven Projects
2. Select project folder
3. Configure JavaFX in project properties
4. Right-click project → Run As → Java Application

### VS Code
1. Install Extension Pack for Java
2. Open folder with project
3. Maven extension should auto-detect
4. Terminal → Run Task → maven: javafx:run

## Troubleshooting

### Build Issues

**Error: JavaFX modules not found**
```bash
mvn clean dependency:resolve
mvn clean install
```

**Error: FXML file not found**
- Verify files are in `src/main/resources/fxml/`
- Check file names match exactly in FXMLLoader

**Error: SQLite driver not found**
```bash
mvn dependency:tree | grep sqlite
mvn clean install -U
```

### Runtime Issues

**Database is locked**
- Close all application instances
- Delete `bus_booking.db`
- Run application again

**UI elements not appearing**
- Check FXML file syntax
- Verify controller class name in FXML
- Rebuild and run

**Combo boxes not updating**
- Check onAction handler is defined
- Verify combo boxes are initialized in controller
- Check service methods return non-null lists

## Additional Resources

### JavaFX Documentation
- [JavaFX API](https://openjfx.io/javadoc/)
- [FXML Tutorial](https://docs.oracle.com/javase/8/javafx/fxml-tutorial/)
- [JavaFX Controls](https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/)

### Java Streams
- [Stream API Documentation](https://docs.oracle.com/javase/8/docs/api/java/util/stream/)
- [Streams Tutorial](https://www.baeldung.com/java-8-streams)

### Design Patterns
- [Observer Pattern](https://refactoring.guru/design-patterns/observer)
- [Repository Pattern](https://refactoring.guru/design-patterns/repository)

### SQLite
- [SQLite JDBC Documentation](https://github.com/xerial/sqlite-jdbc)
- [SQLite SQL Tutorial](https://www.sqlitetutorial.net/)

## Contributing

This is an educational project. If you find improvements:
1. Test thoroughly
2. Add comments
3. Maintain layered architecture
4. Submit pull request

## License

Educational project - Use freely for learning.

## Author

**Ioan Ursache**
- Computer Science Student at UBB, Cluj-Napoca
- GitHub: [@ioan-ursache](https://github.com/ioan-ursache)

---

## Summary

### What You Get

✅ Complete, working solution on `main` branch
✅ Template scaffold on `template` branch  
✅ Full documentation and guides
✅ Maven configuration with all dependencies
✅ SQLite database setup
✅ JavaFX UI with FXML
✅ Layered architecture example
✅ Java Streams implementation
✅ Observer pattern example
✅ Testing guide

### Next Steps

1. **Explore the Solution**
   ```bash
   git checkout main
   mvn javafx:run
   ```

2. **Study the Code**
   - Read IMPLEMENTATION.md
   - Review each layer
   - Understand the architecture

3. **Build Your Own**
   ```bash
   git checkout template
   # Follow TEMPLATE_GUIDE.md
   ```

4. **Compare & Learn**
   - Implement on template branch
   - Compare with main branch
   - Identify differences
   - Understand design choices

Good luck with your implementation! 🚀
