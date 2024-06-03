*README is generated by AI. If you have any questions, please contact the author.* 📫 Can contact me at suthipong.c@obec.moe.go.th
# Movie Showtime Management System

The Movie Showtime Management System is a simple console-based Java application that allows administrators to manage movies, showtimes, and tickets. Users can view available movies and showtimes, and book tickets.

## Features

### Admin Menu
1. **Add Movie**: Add new movies to the system.
2. **Remove Movie**: Remove existing movies from the system.
3. **View All Showtimes**: Display all the showtimes available.
4. **Add Showtime**: Add new showtimes for movies.
5. **Edit Showtime**: Edit details of existing showtimes.
6. **Remove Showtime**: Remove showtimes from the system.
7. **View All Tickets**: View all booked tickets.
8. **Exit**: Exit the application.

### User Menu
1. **View Movies**: Display all available movies.
2. **Select Movie**: Choose a movie to see its showtimes.
3. **Select Showtime**: Choose a showtime to book a ticket.
4. **Select Seat**: Choose a seat to book.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher.

### Running the Application

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/movieshowtime.git
   cd movieshowtime
   ```

2. **Compile the application**:
   ```bash
   javac -d bin src/*.java
   ```

3. **Run the application**:
   ```bash
   java -cp bin UI
   ```

### Usage

1. **Admin Menu**: Start the application and log in as an administrator to manage movies and showtimes.
2. **User Menu**: Users can view movies and showtimes, and book tickets.

## Class Overview

### `UI.java`
The main user interface class that handles admin and user interactions through console input.

### `Service.java`
Handles the business logic and operations related to movies, showtimes, and tickets.

### `Movie.java`
Represents a movie with attributes like title, genre, duration, and subtitle.

### `Showtime.java`
Represents a showtime with attributes like theater, movie, language, time, and seats.

### `Ticket.java`
Represents a ticket with attributes like showtime and seat.

### `Seat.java`
Represents a seat with attributes like seat ID, and booking status.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
This project is licensed under the DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE - see the [LICENSE](LICENSE) file for details.