package com.revature.repos;

import com.revature.models.User;
import com.revature.utils.ConnectionManager;
import com.revature.utils.CrudDaoInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepo implements CrudDaoInterface<User> {

    public Connection conn;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepo.class);

    public UserRepo(){

        // Note: certain methods throw errors because there are different instances of what can go wrong
        // in order to handle those errors in a way that saves the application from crashing
        // we wrap those methods or blocks of code in a "try{}catch(){}"block
        try {
            // this is the code that can throw an error
            conn = ConnectionManager.getConnection();

        }catch(SQLException sqlException){

            LOGGER.error(sqlException.getMessage());
        }
    }


    @Override
    public int create(User user) {

        //although it says create we are inserting into a table that is already created
        //however we are still creating a new row...
        if(user.getFirstName()==null||user.getLastName()==null||user.getEmail()==null||user.getPasscode()==null||user.getZodiac()==null) {
            return -3;
        }else {
            try {
                String sql = "INSERT INTO users (id, first_name, last_name, email, passcode,zodiac) VALUES (default,?,?,?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                pstmt.setString(1, user.getFirstName());
                pstmt.setString(2, user.getLastName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getPasscode());
                pstmt.setString(5, user.getZodiac());

                // this executes the sql statement above
                pstmt.executeUpdate();

                // this gives us a result set of the row that was just created
                ResultSet rs = pstmt.getGeneratedKeys();

                // a challenge for associates -> how can we return a user that has an id? instead of just the id?

                // the cursor is right in front of the first (or only) element in our result set
                // calling rs.next() iterates us into the first row
                rs.next();
                return rs.getInt("id");

            } catch (SQLException sqlException) {
                LOGGER.error(sqlException.getMessage());
            }
        }
        return 0;
    }

    @Override
    public List<User> getAll() {

        List<User> users = new ArrayList<User>();

        try {

            String sql = "SELECT * FROM users";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                User user = new User();
                user.setUserid(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPasscode(rs.getString("passcode"));

                users.add(user);

            }

            return users;

        } catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }


        return null;
    }

    @Override
    public User getByID(int id) {

        try {

            String sql = "SELECT * FROM users WHERE id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);

            ResultSet rs = pstmt.executeQuery();

            // we are returning a user
            // therefore we have to create a new instance of a user from the database

            User user = new User();

            while(rs.next()){
                user.setUserid(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPasscode(rs.getString("passcode"));
                user.setZodiac(rs.getString("zodiac"));
            }

            return user;

        } catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }

        return null;
    }

    @Override
    public User update(User user) {

        try {

            String sql = "UPDATE users SET email = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getEmail());
            pstmt.setInt(2,user.getUserid());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            while (rs.next()){
                user.setEmail(rs.getString("email"));
            }

            return user;

        } catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(User user) {
        //Delete from tablename where id = ?

        try {

            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, user.getUserid());

            //pstmt.execute() returns a boolean
            // it returns false if the executed statement returns void

            return pstmt.execute();
        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return true;
    }

    public boolean deleteById(int id) {
        //Delete from tablename where id = ?

        try {

            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            //pstmt.execute() returns a boolean
            // it returns false if the executed statement returns void

            pstmt.execute();
            return true;
        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return false;
    }

    public User loginUser(User user){
        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getEmail());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next() && rs.getString("pass_word").equals(user.getPasscode())){
                return new User(rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("passcode"),
                        rs.getString("zodiac"));
            }
        }catch(Exception e){
            System.out.println("This is the userDAO: " + e.getMessage());
        }
        return null;
    }
    public int login(User user) {
        User userInfoFromDatabase = this.getUserByEmail(user.getEmail());
        System.out.println(userInfoFromDatabase.toString());
        //this means there was a user with that username
        if (userInfoFromDatabase != null) {
            if (userInfoFromDatabase.getPasscode().equals(user.getPasscode())) {
                //passcode match the user passcode form the registered user in the database
                return userInfoFromDatabase.getUserid();
            }
            // passcode does not match
            return -1;
        }
        // user not found
        return 0;
    }

    public User getUserByEmail(String email) {
        try {
            System.out.println(email);
            String sql = "SELECT * FROM users WHERE email = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,email);

            ResultSet rs = pstmt.executeQuery();

            // we are returning a user
            // therefore we have to create a new instance of a user from the database

            User user=null;

            while(rs.next()){
                user = new User();
                user.setUserid(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPasscode(rs.getString("passcode"));
                user.setZodiac(rs.getString("zodiac"));
            }
            return user;
        } catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
            return null;
        }
    }
}