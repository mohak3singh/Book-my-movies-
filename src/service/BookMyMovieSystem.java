package service;

import config.DataBaseConfig;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class BookMyMovieSystem {

    Scanner scn = new Scanner(System.in);

    //display movies
    public void dispayMovies(){
        try{
            Connection con = DataBaseConfig.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from movies");
            System.out.println("--------- Available movies -------------");
            while(rs.next()){
                System.out.println(rs.getInt("movies_id")+". "+rs.getString("title")
                +"( "+rs.getString("genre")+")");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    //show theater in cities
    public void displayTheaters(String city){
        try{
            Connection con = DataBaseConfig.getConnection();
            PreparedStatement stmt = con.prepareStatement("select * from theaters where city=?");
            stmt.setString(1,city);
            ResultSet rs = stmt.executeQuery();
            System.out.println("--------- Theaters in "+city+" -------------");
            while(rs.next()){
                System.out.println(rs.getInt("theater_id")+". "+rs.getString("name")
                        +"( "+rs.getString("city")+")");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void displayShows(int movieId, int theaterId){
        try{
            Connection con = DataBaseConfig.getConnection();
            PreparedStatement stmt = con.prepareStatement("select * from shows where movie_id=? AND theater_id=?");
            stmt.setInt(1,movieId);
            stmt.setInt(2,theaterId);
            ResultSet rs = stmt.executeQuery();
            System.out.println("--------- Available Shows in Theaters -------------");
            while(rs.next()){
                System.out.println(rs.getInt("show_id")+". "+rs.getString("timing")
                        +"- Seats Available: "+rs.getInt("available_seats")+")");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Book ticket
    public void bookTicket(int userId, int showId, List<String> selectedSeats){
        try{
            Connection con = DataBaseConfig.getConnection();
            con.setAutoCommit(false);

            //check if seats are available
            boolean alreadyBooked = false;
            for(String seat:selectedSeats){
                PreparedStatement stmt = con.prepareStatement("select * from seat where seat_number=? and show_id=?");
                stmt.setString(1,seat);
                stmt.setInt(2,showId);
                ResultSet rs = stmt.executeQuery();
                if(rs.next() && rs.getBoolean("isbooked")){
                    alreadyBooked = true;
                    System.out.println("seat: "+seat +" is already booked. Choose another seat");
                }
            }
            if(alreadyBooked){
                System.out.println("Booking failed! Some seats are booked");
                con.rollback();
                return;
            }
            for(String seat:selectedSeats){
                PreparedStatement stmt = con.prepareStatement("UPDATE seat set is_booked=TRUE where seat_number=? AND show_id=?");
                stmt.setString(1,seat);
                stmt.setInt(2,showId);
                stmt.executeUpdate();
            }
            double seatPrice=200.0;
            double tp = selectedSeats.size()*seatPrice;

            PreparedStatement stmt =  con.prepareStatement("insert into bookings (user_id, show_id, seats_booked, total_price) values(?,?,?,?)");
            stmt.setInt(1, userId);
            stmt.setInt(2, showId);
            stmt.setString(3, String.join(",",selectedSeats));
            stmt.setDouble(4, tp);
            stmt.executeUpdate();
            con.commit();
            System.out.println("Booking successful, Seats: "+selectedSeats+ " | Total Price: "+tp);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
