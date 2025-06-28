package service;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BookMyMovieApp {
    public static void main(String[] args) {
        BookMyMovieSystem mbs = new BookMyMovieSystem();
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter city: ");
        String city = sc.next();
        mbs.displayTheaters("Enter Theater Id and Movie Id:");
        int theaterId = sc.nextInt();
        int movieId = sc.nextInt();
        mbs.displayShows(movieId, theaterId);

        System.out.println("Enter Show id and number of seats");
        int showId = sc.nextInt();
        List<String> ss = Arrays.asList("A3","A4");
        mbs.bookTicket(1,showId, ss);
    }
}
