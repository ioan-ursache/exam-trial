# Bus Booking System - Template Guide

## What is this template?

This is a **blank scaffold** for building a bus booking system. All files are created with:
- Package structure in place
- Proper layering (model, repository, service, controller)
- Empty method stubs with TODO comments
- FXML layout files with placeholders
- Maven configuration ready to go

## How to use this template

### Step 1: Set up the project
```bash
# Build the project
mvn clean install

# Verify all dependencies are downloaded
mvn dependency:resolve
```

### Step 2: Implement layer by layer

#### Layer 1: Model (Domain Objects)
**File:** `src/main/java/com/buscompany/model/Route.java`

Implement:
- Fields (id, sourceCity, destinationCity, departureTime, arrivalTime, totalSeats, availableSeats, price)
- Constructor
- Getters and setters
- Utility methods (getDurationMinutes, getDurationString)

#### Layer 2: Repository (Database Access)
**File:** `src/main/java/com/buscompany/repository/RouteRepository.java`

Implement:
- `initializeDatabase()` - Create tables and insert sample data
- `getAllRoutes()` - Retrieve all routes
- `getRoutesBySourceAndDestination()` - Filter by cities
- `getAllSourceCities()` - Get unique source cities
- `getDestinationCitiesForSource()` - Get destinations for a source
- `updateAvailableSeats()` - Update seat count after booking

#### Layer 3: Service (Business Logic)
**File:** `src/main/java/com/buscompany/service/RouteService.java`

Implement:
- `getAllRoutesSorted()` - **Use Java Streams** for sorting by city and time
- `getRoutesBySourceAndDestination()` - Return ObservableList
- `getAllSourceCities()` - Return ObservableList
- `getDestinationCitiesForSource()` - Return ObservableList
- `bookTickets()` - Validate and process booking
- Observer pattern methods (addObserver, removeObserver, notifyObservers)

#### Layer 4: Controllers (UI Logic)
**Files:**
- `src/main/java/com/buscompany/controller/MainSceneController.java`
- `src/main/java/com/buscompany/controller/ClientWindowController.java`

Implement:
- Event handlers for button clicks
- Combo box selection listeners
- List selection handlers
- Price calculations
- Window creation

#### Layer 5: Views (FXML)
**Files:**
- `src/main/resources/fxml/main-scene.fxml`
- `src/main/resources/fxml/client-window.fxml`

Create:
- Main window layout with routes list
- Client window layout with booking controls
- Proper component IDs matching controller @FXML fields
- Event handler bindings

#### Layer 6: Application Entry Point
**File:** `src/main/java/com/buscompany/BusBookingApplication.java`

Implement:
- Load main FXML file
- Set up primary stage
- Show window

### Step 3: Database Setup

Your `RouteRepository.initializeDatabase()` should:

1. **Create table:**
```sql
CREATE TABLE routes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    source_city TEXT NOT NULL,
    destination_city TEXT NOT NULL,
    departure_time TEXT NOT NULL,
    arrival_time TEXT NOT NULL,
    total_seats INTEGER NOT NULL,
    available_seats INTEGER NOT NULL,
    price REAL NOT NULL
);
```

2. **Insert sample data:**
```
Bucharest -> Brașov (08:00-10:30, 40 seats, 50 lei)
Bucharest -> Cluj (07:00-12:00, 50 seats, 100 lei)
Bucharest -> Constanța (09:00-11:30, 45 seats, 65 lei)
Brașov -> Cluj (10:00-14:00, 35 seats, 75 lei)
Brașov -> Bucharest (11:00-13:30, 40 seats, 50 lei)
Cluj -> Bucharest (08:00-13:00, 50 seats, 100 lei)
Cluj -> Brașov (15:00-19:00, 35 seats, 75 lei)
Constanța -> Bucharest (12:00-14:30, 45 seats, 65 lei)
```

### Step 4: Testing

Once implemented, test each feature:

1. **Routes Display**
   - Run application
   - Check all routes appear in main window
   - Verify sorting by city then time

2. **Dynamic Filtering**
   - Open client window
   - Select source city
   - Verify destination combo box updates

3. **Route Details**
   - Select source and destination
   - Verify routes list shows all required info

4. **Booking**
   - Select route and ticket count
   - Verify price calculation
   - Click book button
   - Verify success message and seat update

5. **Validation**
   - Try booking more tickets than available
   - Verify error message

6. **Observer Pattern (BONUS)**
   - Open two client windows
   - Book in window 1
   - Verify window 2 updates automatically

## Running the Application

```bash
# Build
mvn clean package

# Run
mvn javafx:run
```

## Key Implementation Notes

### Java Streams (REQUIREMENT)
Must sort routes using streams:
```java
list.stream()
    .sorted((r1, r2) -> ...)
    .collect(Collectors.toList())
```

### JavaFX ObservableList
Always use ObservableList for UI data:
```java
ObservableList<Route> routes = FXCollections.observableArrayList(list);
listView.setItems(routes);
```

### FXML Binding
Match @FXML field IDs with FXML component IDs:
```java
@FXML private ListView routesListView;  // Must have fx:id="routesListView"
```

### Event Handlers
Use # prefix in FXML:
```xml
<Button onAction="#onOpenClientWindow"/>
```

Implement in controller:
```java
@FXML
private void onOpenClientWindow() { }
```

## Troubleshooting

**Issue: FXML file not found**
- Check file is in `src/main/resources/fxml/`
- Verify file name in FXMLLoader

**Issue: Controller not found**
- Check fx:controller path matches class package
- Rebuild project

**Issue: Database not created**
- Delete `bus_booking.db` if exists
- Rebuild and run

**Issue: Dependencies not found**
- Run `mvn clean dependency:resolve`
- Rebuild project

## Success Criteria

✅ All routes display sorted by city and time
✅ Use Java Streams for sorting
✅ Destination cities filter dynamically
✅ Route details show all required fields
✅ Ticket booking works with validation
✅ Available seats update in database
✅ Total price calculates correctly
✅ Error messages shown for insufficient seats
✅ Multiple client windows can be open
✅ Observer pattern for real-time updates (BONUS)

## File Structure

```
src/main/java/com/buscompany/
├── BusBookingApplication.java     # Main entry point
├── model/
│   └── Route.java                  # Domain model
├── repository/
│   └── RouteRepository.java        # Database layer
├── service/
│   └── RouteService.java           # Business logic
├── controller/
│   ├── MainSceneController.java
│   └── ClientWindowController.java
└── util/
    └── DatabaseUtils.java          # Database utilities

src/main/resources/
├── fxml/
│   ├── main-scene.fxml
│   └── client-window.fxml
└── application.properties
```

Good luck with your implementation!
