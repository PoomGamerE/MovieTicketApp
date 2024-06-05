package int103.g28.project;

import int103.g28.project.domain.Movie;
import int103.g28.project.domain.Seat;
import int103.g28.project.domain.Showtime;
import int103.g28.project.domain.Ticket;
import int103.g28.project.repository.*;
import int103.g28.project.service.MovieService;
import int103.g28.project.service.ShowtimeService;
import int103.g28.project.service.TicketService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class UI {
    private static Scanner scanner = new Scanner(System.in);
    private static MovieService movieService;
    private static ShowtimeService showtimeService;
    private static TicketService ticketService;

    public static void main(String[] args) {
        selectstorage();
        mainmenu();
    }

    public static void selectstorage() {
        // Select repository type
        System.out.println("(1) In-memory, (2) File, (3) JDBC");
        System.out.println("Please enter number to select: ");

        int repoType = 0;
        try {
            repoType = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
        }

        while (repoType < 1 || repoType > 3) {
            System.out.println("Invalid repository type. Please enter again.");
            try {
                repoType = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
            }
        }

        switch (repoType) {
            case 1:
                movieService = new MovieService(new InMemoryMovieRepository());
                showtimeService = new ShowtimeService(new InMemoryShowtimeRepository());
                ticketService = new TicketService(new InMemoryTicketRepository());
                System.out.println("You have selected In-memory as the storage.");
                break;
            case 2:
                movieService = new MovieService(new FileMovieRepository());
                showtimeService = new ShowtimeService(new FileShowtimeRepository());
                ticketService = new TicketService(new FileTicketRepository());
                System.out.println("You have selected File as the storage.");
                break;
            case 3:
                selectstorage();
                //System.out.println("You have selected JDBC as the storage.");
                break;
        }
    }

    public static void mainmenu() {
        System.out.println("\n");
        System.out.println("-------------------------");
        System.out.println("1. Admin Mode");
        System.out.println("2. User Mode");
        System.out.println("-------------------------");
        System.out.println("9. Exit program");
        System.out.println("\n");
        System.out.println("Please enter number to select: ");

        int mode = 0;
        try {
            mode = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
        }

        while (mode != 1 && mode != 2 && mode != 9) {
            System.out.println("Invalid mode. Please enter again.");
            try {
                mode = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
            }
        }

        switch (mode) {
            case 1:
                login();
                break;
            case 2:
                showUserMenu();
                break;
            case 9:
                System.exit(0);
                break;
        }
    }

    // Admin Zone ---- Admin Zone ---- Admin Zone ---- Admin Zone ---- Admin Zone ---- Admin Zone ---- Admin Zone ---- Admin Zone ---- Admin Zone ---- Admin Zone

    public static void login(){
        if (System.console() == null) {
            System.out.println("Console is not available. Returning to main menu. auto in 3 Seconds.");
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
            }
            mainmenu();
        }

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        char[] password = System.console().readPassword();

        if (authenticate(username, password)) {
            showAdminMenu();
        } else {
            System.out.print("Wrong password");
            mainmenu();
        }
    }

    private static boolean authenticate(String username, char[] password) {
        // Simplified authentication logic
        return "admin".equals(username) && "password".equals(new String(password));
    }

    public static void showAdminMenu() {
        System.out.println("\n");
        System.out.println("Admin Menu List");
        System.out.println("-------------------------");
        System.out.println("1. View All Movies");
        System.out.println("2. Add Movie");
        System.out.println("3. Remove Movie");
        System.out.println("***** To edit details of movie, please select 'View All Movies' first.");
        System.out.println("-------------------------");
        System.out.println("4. View All Showtime");
        System.out.println("5. Add Showtime");
        System.out.println("6. Remove Showtime");
        System.out.println("***** To edit details of showtime, please select 'View All Movies' first.");
        System.out.println("-------------------------");
        System.out.println("7. View All Ticket");
        System.out.println("-------------------------");
        System.out.println("9. Exit program");
        System.out.println("10. Back to the previous menu.");
        System.out.println("\n");
        System.out.println("Please enter number to select: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
        }

        while (choice < 1 || choice > 10 || choice == 8) {
            System.out.println("Invalid choice. Please enter again.");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
            }
        }

        switch (choice) {
            case 1:
                viewAllMovies();
                break;
            case 2:
                addMovie();
                break;
            case 3:
                removeMovie();
                break;
            case 4:
                viewAllShowtime();
                break;
            case 5:
                addShowtime();
                break;
            case 6:
                removeShowtime();
                break;
            case 7:
                viewAllTicket();
                break;
            case 9:
                System.exit(0);
                break;
            case 10:
                mainmenu();
                break;
        }
    }

    public static void viewAllMovies() {
        System.out.println("***** All Movies *****");
        System.out.println("-----------");

        if (movieService.getMovies().isEmpty()) {
            System.out.println("No movies found.");
        } else {
            // using for-each loop for iteration over Map.entrySet()
            for (Map.Entry<String, Movie> entry : movieService.getMovies().entrySet()) {
                System.out.println("(" + entry.getKey() + ") " + entry.getValue().getTitle());
            }
            System.out.println("-----------");
            System.out.println("Enter ID of the movie to edit details.");
        }

        System.out.println("-----------");
        System.out.println("99. Exit program");
        System.out.println("100. Back to the previous menu.");
        System.out.println("\n");
        System.out.println("Please enter number to select: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
        }

        while (choice < 1 || choice > 100) {
            System.out.println("Invalid choice. Please enter again.");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
            }
        }

        if (choice == 99) {
            System.exit(0);
        } else if (choice == 100) {
            showAdminMenu();
        } else {
            editMovie(String.valueOf(choice));
        }
    }

    public static void addMovie() {
        System.out.println("***** Add Movie *****");
        System.out.println("-----------");

        System.out.print("Enter movie title: ");
        String title = scanner.nextLine();
        while (title == null || title.isEmpty() || title.isBlank()) {
            System.out.println("Title cannot be empty. Please enter again.");
            title = scanner.nextLine();
        }

        System.out.print("Enter movie duration: ");
        String duration = scanner.nextLine();
        while (duration == null || duration.isEmpty() || duration.isBlank()) {
            System.out.println("Duration cannot be empty. Please enter again.");
            duration = scanner.nextLine();
        }

        System.out.print("Enter movie genre: ");
        String genre = scanner.nextLine();
        while (genre == null || genre.isEmpty() || genre.isBlank()) {
            System.out.println("Genre cannot be empty. Please enter again.");
            genre = scanner.nextLine();
        }

        System.out.print("Enter movie subtitle: ");
        String subtitle = scanner.nextLine();

        String id = movieService.movienextId();

        Movie movie = new Movie(id, title, duration, genre, subtitle);
        movieService.addMovie(movie);

        System.out.println("\n");
        System.out.println("Movie added successfully.");
        System.out.println("\n");
        System.out.println("-----------");
        System.out.println("1. Add another movie");
        System.out.println("2. Back to the previous menu.");
        System.out.println("\n");
        System.out.println("Please enter number to select: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
        }

        while (choice < 1 || choice > 2) {
            System.out.println("Invalid choice. Please enter again.");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
            }
        }

        if (choice == 1) {
            addMovie();
        } else {
            showAdminMenu();
        }
    }

    public static void editMovie(String id) {
        System.out.println("***** Edit Movie details *****");
        System.out.println("For any part you don't want to change, press Enter.");
        System.out.println("-----------");

        Movie movie = movieService.findMovies(id);

        System.out.println("Current Title: " + movie.getTitle());
        System.out.print("Enter new title: ");
        String title = scanner.nextLine();
        if (title == null || title.isEmpty() || title.isBlank()) {
            title = movie.getTitle();
        }

        System.out.println("Current Duration: " + movie.getDuration());
        System.out.print("Enter new duration: ");
        String duration = scanner.nextLine();
        if (duration == null || duration.isEmpty() || duration.isBlank()) {
            duration = movie.getDuration();
        }

        System.out.println("Current Genre: " + movie.getGenre());
        System.out.print("Enter new genre: ");
        String genre = scanner.nextLine();
        if (genre == null || genre.isEmpty() || genre.isBlank()) {
            genre = movie.getGenre();
        }

        System.out.println("Current Subtitle: " + movie.getSubtitle());
        System.out.print("Enter new subtitle: ");
        String subtitle = scanner.nextLine();
        if (subtitle == null || subtitle.isEmpty() || subtitle.isBlank()) {
            subtitle = movie.getSubtitle();
        }

        Movie newMovie = new Movie(id, title, duration, genre, subtitle);
        movieService.updateMovie(newMovie);

        System.out.println("\n");
        System.out.println("Movie edited successfully.");
        System.out.println("\n");
        System.out.println("-----------");
        System.out.println("1. Edit another movie");
        System.out.println("2. Back to the previous menu.");
        System.out.println("\n");
        System.out.println("Please enter number to select: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
        }

        while (choice < 1 || choice > 2) {
            System.out.println("Invalid choice. Please enter again.");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
            }
        }

        if (choice == 1) {
            viewAllMovies();
        } else {
            showAdminMenu();
        }
    }

    public static void removeMovie() {
        System.out.println("***** Remove Movie *****");
        System.out.println("-----------");

        if (movieService.getMovies().isEmpty()) {
            System.out.println("No movies found.");
            System.out.println("Automatically back to the previous menu in 3 Seconds.");
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
            }
            showAdminMenu();
        } else {
            // using for-each loop for iteration over Map.entrySet()
            for (Map.Entry<String, Movie> entry : movieService.getMovies().entrySet()) {
                System.out.println("(" + entry.getKey() + ") " + entry.getValue().getTitle());
            }
            System.out.println("-----------");
            System.out.println("Enter movie ID to remove: ");
        }

        String movieId = scanner.nextLine();

        while (true) {
            if (movieId == null || movieId.isEmpty() || movieId.isBlank()) {
                System.out.println("Movie id cannot be empty. Please enter again.");
                movieId = scanner.nextLine();
            }
            if (movieService.findMovie(movieId)) {
                break;
            } else {
                if (movieId == null || movieId.isEmpty() || movieId.isBlank()) {
                    System.out.println("Movie id cannot be empty. Please enter again.");
                    movieId = scanner.nextLine();
                } else {
                    System.out.println("Movie id does not exist. Please enter again.");
                    movieId = scanner.nextLine();
                }
            }
        }

        Movie movie = movieService.findMovies(movieId);

        if (showtimeService.checkMovieInShowtime(movieId)) {
            System.out.println("Movie is used in showtime. Cannot be removed.");
        } else {
            movieService.removeMovie(movie);
            System.out.println("Movie removed successfully.");
        }

        System.out.println("\n");
        System.out.println("-----------");
        System.out.println("1. Remove another movie");
        System.out.println("2. Back to the previous menu.");
        System.out.println("\n");
        System.out.println("Please enter number to select: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
        }

        while (choice < 1 || choice > 2) {
            System.out.println("Invalid choice. Please enter again.");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
            }
        }

        if (choice == 1) {
            removeMovie();
        } else {
            showAdminMenu();
        }
    }

    public static void viewAllShowtime() {
        System.out.println("***** Showtime List *****");
        System.out.println("-----------");

        if (showtimeService.getShowtimes().isEmpty()) {
            System.out.println("No showtimes found.");
        } else {
            // using for-each loop for iteration over Map.entrySet()
            for (Map.Entry<String, Showtime> entry : showtimeService.getShowtimes().entrySet()) {
                System.out.println("(" + entry.getKey() + ") Theater " + entry.getValue().getTheater() + " [" + entry.getValue().seatsAmount() + " seat] - " + entry.getValue().getMovie().getTitle());
            }
        }

        System.out.println("-----------");
        System.out.println("99. Exit program");
        System.out.println("100. Back to the previous menu.");
        System.out.println("\n");
        System.out.println("Please enter number to select: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
        }

        while (choice < 1 || choice > 100) {
            System.out.println("Invalid choice. Please enter again.");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
            }
        }

        if (choice == 99) {
            System.exit(0);
        } else if (choice == 100) {
            showAdminMenu();
        } else {
            editShowtime(String.valueOf(choice));
        }
    }

    public static void addShowtime() {
        System.out.println("***** Add Showtime *****");
        System.out.println("-----------");

        //select movie
        if (movieService.getMovies().isEmpty()) {
            System.out.println("No movies found.");
            System.out.println("Please add a movie first.");
            System.out.println("System: Back to the previous menu automatically in 3 Seconds.");
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
            }
            System.out.println("\n");
            showAdminMenu();

        } else {
            // using for-each loop for iteration over Map.entrySet()
            for (Map.Entry<String, Movie> entry : movieService.getMovies().entrySet()) {
                System.out.println("(" + entry.getKey() + ") " + entry.getValue().getTitle());
            }
            System.out.println("\n");
        }

        System.out.print("Enter ID of the movie to select: ");
        String movieId = scanner.nextLine();

        while (true) {
            if (movieId == null || movieId.isEmpty() || movieId.isBlank()) {
                System.out.println("Movie id cannot be empty. Please enter again.");
                movieId = scanner.nextLine();
            }
            if (movieService.findMovie(movieId)) {
                break;
            } else {
                if (movieId == null || movieId.isEmpty() || movieId.isBlank()) {
                    System.out.println("Movie id cannot be empty. Please enter again.");
                    movieId = scanner.nextLine();
                } else {
                    System.out.println("Movie id does not exist. Please enter again.");
                    movieId = scanner.nextLine();
                }
            }
        }

        Movie movie = movieService.findMovies(movieId);


        System.out.print("Enter theater: ");
        String theater = scanner.nextLine();
        while (theater == null || theater.isEmpty() || theater.isBlank()) {
            System.out.println("Theater cannot be empty. Please enter again.");
            theater = scanner.nextLine();
        }

        System.out.print("Enter language: ");
        String language = scanner.nextLine();
        while (language == null || language.isEmpty() || language.isBlank()) {
            System.out.println("Language cannot be empty. Please enter again.");
            language = scanner.nextLine();
        }

        System.out.print("Enter time: ");
        String time = scanner.nextLine();
        while (time == null || time.isEmpty() || time.isBlank()) {
            System.out.println("Time cannot be empty. Please enter again.");
            time = scanner.nextLine();
        }

        System.out.print("Type the number of rows of seats: ");
        int rows = 0;
        try {
            rows = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
        }

        while (rows < 1) {
            System.out.println("Invalid number of rows. Please enter again.");
            try {
                rows = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
            }
        }

        System.out.print("Type the number of columns of seats: ");
        int columns = 0;
        try {
            columns = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
        }

        while (columns < 1) {
            System.out.println("Invalid number of columns. Please enter again.");
            try {
                columns = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
            }
        }

        //Create Map<String, Seat> seats from rows and columns, example stored values A1 A2 A3 A4 A5 B1 B2 B3 B4 B5
        Map<String, Seat> seats = new HashMap<>();
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                String seatId = String.valueOf((char) (i + 64)) + j; //(char) (1 + 64)) = A
                Seat seat = new Seat(seatId);
                seat.setRowofallseat(rows);
                seat.setColumnofallseat(columns);
                seats.put(seatId, seat);
            }
        }

        String id = showtimeService.showtimenextId();

        Showtime showtime = new Showtime(id, theater, movie, language, time, seats);
        showtimeService.addShowtime(showtime);

        System.out.println("\n");
        System.out.println("Showtime added successfully.");
        System.out.println("\n");
        System.out.println("-----------");
        System.out.println("1. Add another showtime");
        System.out.println("2. Back to the previous menu.");
        System.out.println("\n");
        System.out.println("Please enter number to select: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
        }

        while (choice < 1 || choice > 2) {
            System.out.println("Invalid choice. Please enter again.");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
            }
        }

        if (choice == 1) {
            addShowtime();
        } else {
            showAdminMenu();
        }
    }

    public static void editShowtime(String id) {
        System.out.println("***** Edit Showtime details *****");
        System.out.println("For any part you don't want to change, press Enter.");
        System.out.println("-----------");

        Showtime showtime = showtimeService.findShowtimes(id);

        System.out.print("Enter theater: ");
        String theater = scanner.nextLine();
        if (theater == null || theater.isEmpty() || theater.isBlank()) {
            theater = showtime.getTheater();
        }

        System.out.print("Enter language: ");
        String language = scanner.nextLine();
        if (language == null || language.isEmpty() || language.isBlank()) {
            language = showtime.getLanguage();
        }

        System.out.print("Enter time: ");
        String time = scanner.nextLine();
        if (time == null || time.isEmpty() || time.isBlank()) {
            time = showtime.getTime();
        }

        Showtime newShowtime = new Showtime(id, theater, showtime.getMovie(), language, time, showtime.getSeats());
        showtimeService.updateShowtime(newShowtime);

        System.out.println("\n");
        System.out.println("Showtime updated successfully.");
        System.out.println("\n");
        System.out.println("-----------");
        System.out.println("1. Edit another showtime");
        System.out.println("2. Back to the previous menu.");
        System.out.println("\n");
        System.out.println("Please enter number to select: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
        }

        while (choice < 1 || choice > 2) {
            System.out.println("Invalid choice. Please enter again.");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
            }
        }

        if (choice == 1) {
            editShowtime(id);
        } else {
            showAdminMenu();
        }
    }

    public static void removeShowtime() {
        System.out.println("***** Remove Showtime *****");
        System.out.println("-----------");
        if (showtimeService.getShowtimes().isEmpty()) {
            System.out.println("No showtimes found.");
            System.out.println("Automatically back to the previous menu in 3 Seconds.");
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
            }
            showAdminMenu();
        } else {
            // using for-each loop for iteration over Map.entrySet()
            for (Map.Entry<String, Showtime> entry : showtimeService.getShowtimes().entrySet()) {
                System.out.println("(" + entry.getKey() + ") Theater " + entry.getValue().getTheater() + " [" + entry.getValue().seatsAmount() + " seat] - " + entry.getValue().getMovie().getTitle());
            }
        }

        System.out.println("-----------");

        System.out.print("Enter showtime id to remove: ");
        String id = scanner.nextLine();
        while (id == null || id.isEmpty() || id.isBlank()) {
            System.out.println("Showtime id cannot be empty. Please enter again.");
            id = scanner.nextLine();
        }
        if (showtimeService.findShowtime(id)) {
            showtimeService.removeShowtime(showtimeService.findShowtimes(id));
            System.out.println("\n");
            System.out.println("Showtime removed successfully.");
            System.out.println("\n");
        } else {
            System.out.println("Showtime id does not exist.");
        }

        System.out.println("-----------");
        System.out.println("1. Remove another showtime");
        System.out.println("2. Back to the previous menu.");
        System.out.println("\n");
        System.out.println("Please enter number to select: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
        }

        while (choice < 1 || choice > 2) {
            System.out.println("Invalid choice. Please enter again.");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
            }
        }

        if (choice == 1) {
            removeShowtime();
        } else {
            showAdminMenu();
        }
    }

    public static void viewAllTicket() {
        System.out.println("***** All Tickets *****");
        System.out.println("-----------");

        if (ticketService.getTickets().isEmpty()) {
            System.out.println("No tickets found.");
        } else {
            // using for-each loop for iteration over Map.entrySet()
            for (Map.Entry<String, Ticket> entry : ticketService.getTickets().entrySet()) {
                System.out.println("(" + entry.getKey() + ") " + entry.getValue().getShowtime().getMovie().getTitle() + " - " + entry.getValue().getShowtime().getTime() + " - " + entry.getValue().getSeats().getSeatid());
            }
        }

        System.out.println("-----------");
        System.out.println("99. Exit program");
        System.out.println("100. Back to the previous menu.");
        System.out.println("\n");
        System.out.println("Please enter number to select: ");
    }

    // User Zone ---- User Zone ---- User Zone ---- User Zone ---- User Zone ---- User Zone ---- User Zone ---- User Zone ---- User Zone

    public static void showUserMenu() {
        //showUserMenu == Ticket Office
        System.out.println("\n");
        System.out.println("***** Ticket Office *****");
        System.out.println("--------------------------");
        if (movieService.getMovies().isEmpty()) {
            System.out.println("No movies found.");
            System.out.println("Please contact the staff.");
        } else {
            // using for-each loop for iteration over Map.entrySet()
            for (Map.Entry<String, Movie> entry : movieService.getMovies().entrySet()) {
                System.out.println("(" + entry.getKey() + ") " + entry.getValue().getTitle());
            }
            System.out.println("--------------------------");
            System.out.println("\n");
            System.out.println("Enter number the movie you want to watch: ");
        }

        String movieId = scanner.nextLine();

        while (true) {
            if (movieId == null || movieId.isEmpty() || movieId.isBlank()) {
                System.out.println("Movie id cannot be empty. Please enter again.");
                movieId = scanner.nextLine();
            }
            if (movieService.findMovie(movieId)) {
                break;
            } else {
                if (movieId == null || movieId.isEmpty() || movieId.isBlank()) {
                    System.out.println("Movie id cannot be empty. Please enter again.");
                    movieId = scanner.nextLine();
                } else {
                    System.out.println("Movie id does not exist. Please enter again.");
                    movieId = scanner.nextLine();
                }
            }
        }

        listShowtimeofMovie(movieId);
    }

    public static void listShowtimeofMovie(String movieid) {
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("detail of the movie: ");
        System.out.println(movieService.findMovies(movieid).getTitle());
        System.out.println("Genre: " + movieService.findMovies(movieid).getGenre());
        System.out.println("Duration: " + movieService.findMovies(movieid).getDuration());
        System.out.println(movieService.findMovies(movieid).getSubtitle());
        System.out.println("--------------------------");
        System.out.println("***** Showtime of the movie *****");
        System.out.println("--------------------------");

        if (showtimeService.getShowtimes().isEmpty()) {
            System.out.println("No showtimes found.");
            System.out.println("Please contact the staff.");
        } else {
            // using for-each loop for iteration over Map.entrySet()
            for (Map.Entry<String, Showtime> entry : showtimeService.getShowtimes().entrySet()) {
                if (movieService.equalsMovie(movieService.findMovies(movieid), entry.getValue().getMovie())) {
                    System.out.println("(" + entry.getKey() + ") Theater " + entry.getValue().getTheater() + " - [" + entry.getValue().getLanguage() + "] - Time: " + entry.getValue().getTime());
                }
            }
        }
        System.out.println("--------------------------");
        System.out.println("\n");
        System.out.println("Enter number the showtime you want to watch: ");
        String showtimeId = scanner.nextLine();

        while (true) {
            if (showtimeId == null || showtimeId.isEmpty() || showtimeId.isBlank()) {
                System.out.println("Showtime id cannot be empty. Please enter again.");
                showtimeId = scanner.nextLine();
            }
            if (showtimeService.findShowtime(showtimeId)) {
                if (movieService.equalsMovie(movieService.findMovies(movieid), showtimeService.findShowtimes(showtimeId).getMovie())) {
                    break;
                } else {
                    System.out.println("Showtime id is wrong. Please enter again.");
                    showtimeId = scanner.nextLine();
                }
            } else {
                if (showtimeId == null || showtimeId.isEmpty() || showtimeId.isBlank()) {
                    System.out.println("Showtime id cannot be empty. Please enter again.");
                    showtimeId = scanner.nextLine();
                } else {
                    System.out.println("Showtime id does not exist. Please enter again.");
                    showtimeId = scanner.nextLine();
                }
            }
        }

        buyTicket(showtimeId);
    }

    public static void buyTicket(String showtimeId) {
        System.out.println("\n");
        Map<String, Seat> seats = showtimeService.getSeats(showtimeId);
        int rows = showtimeService.getSeatRow(showtimeId);
        int columns = showtimeService.getSeatColumn(showtimeId);
        System.out.println("***** All seats in the theater *****");
        System.out.println("-------------------------------------");
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                String seatId = String.valueOf((char) (i + 64)) + j; //(char) (1 + 64)) = A
                if (seats.containsKey(seatId) && seats.get(seatId).isBooked()) {
                    System.out.print("XX\t");
                } else {
                    System.out.print(seatId + "\t");
                }
            }
            System.out.println();
        }
        System.out.println("-------------------------------------");
        System.out.println("Please type ID to select a seat. (Example: A1)");

        String seatId = scanner.nextLine();
        while (true) {
            if (seatId == null || seatId.isEmpty() || seatId.isBlank()) {
                System.out.println("Seat id cannot be empty. Please enter again.");
                seatId = scanner.nextLine();
            }
            if (seats.containsKey(seatId) && !seats.get(seatId).isBooked()) {
                break;
            } else {
                if (seatId == null || seatId.isEmpty() || seatId.isBlank()) {
                    System.out.println("Seat id cannot be empty. Please enter again.");
                    seatId = scanner.nextLine();
                } else {
                    System.out.println("Seat id is wrong. Please enter again.");
                    seatId = scanner.nextLine();
                }
            }
        }
        System.out.println("\n");
        System.out.println("-------------------------------------");
        System.out.println("***** Ticket Information *****");
        System.out.println(movieService.findMovies(showtimeService.findShowtimes(showtimeId).getMovie().getMovieid()).getTitle());
        System.out.println("Theater: " + showtimeService.findShowtimes(showtimeId).getTheater());
        System.out.println("Time: " + showtimeService.findShowtimes(showtimeId).getTime());
        System.out.println("Seat: " + seatId);
        System.out.println("-------------------------------------");
        System.out.println("Do you want to make a booked? (Y/N): ");
        System.out.println("\n");

        String confirm = scanner.nextLine();
        while (true) {
            if (confirm == null || confirm.isEmpty() || confirm.isBlank()) {
                System.out.println("Please enter Y or N.");
                confirm = scanner.nextLine();
            }
            if (confirm.equalsIgnoreCase("Y")) {
                Showtime showtime = showtimeService.findShowtimes(showtimeId);
                Seat seat = showtimeService.getSeat(showtimeId, seatId);

                seat.setBooked(true);
                seats.put(seatId, seat);

                Showtime updatedShowtime = new Showtime(showtimeId, showtime.getTheater(), showtime.getMovie(), showtime.getLanguage(), showtime.getTime(), seats);
                showtimeService.updateShowtime(updatedShowtime);
                ticketService.createTicket(updatedShowtime, seat);
                System.out.println("Booked successfully.");
                break;
            } else if (confirm.equalsIgnoreCase("N")) {
                System.out.println("Canceled.");
                break;
            } else {
                System.out.println("Please enter Y or N.");
                confirm = scanner.nextLine();
            }
        }

        System.out.println("System: Back to menu automatically in 3 Seconds.");
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
        }
        System.out.println("\n");
        showUserMenu();
    }
}