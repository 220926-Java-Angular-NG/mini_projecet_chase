package com.revature.controllers;

import com.revature.models.User;
import com.revature.services.UserService;
import io.javalin.http.Handler;

public class UserController {

    private UserService userService;

    public UserController() {
        userService = new UserService();
    }

    public UserController(UserService userService) {
        this.userService = userService;
    }


    public Handler createNewUser = context -> {
        User user = context.bodyAsClass(User.class);// change json from postman to an object
        // the passed in user must contain a username and passcode
        //System.out.println(user.getEmail() != null && user.getPasscode() == null);
        if (user.getEmail() == null || user.getPasscode() == null) {
            if (user.getEmail() != null && user.getPasscode() == null)
                context.result("You must enter a passcode").status(400);
            if (user.getEmail() == null && user.getPasscode() != null)
                context.result("You must enter a username").status(400);
        } else {
            //checks to see if the @user already exist
            User userFromDataBase = userService.getUserByEmail(user.getEmail());

            if (userFromDataBase != null) {
                context.result("User already exist").status(401);
            } else {
                //try's to create user
                int idOfCreatedUser = userService.createUser(user);
                // -2 is returned when an unaccounted for sql error occurs
                if (idOfCreatedUser == -2) {
                    context.result("User not created").status(401);
                } if (idOfCreatedUser == -3) {
                    context.result("You are missing elements for creating a user!").status(401);
                } else {
                    //@user was made successfully and returns info from database indexed by @idOfCreatedUser
                    context.json(userService.getUserByEmail(user.getEmail())).status(201);
                }
            }
        }
    };
    public Handler logUserIn = context -> {
        User user = context.bodyAsClass(User.class);// change json from postman to an object
        // the passed in user must contain a username and passcode to log in
        if (user.getEmail() == null || user.getPasscode() == null) {
            if (user.getEmail() != null && user.getPasscode() == null)
                context.result("You must enter a passcode").status(400);
            if (user.getEmail() == null && user.getPasscode() != null)
                context.result("You must enter a username").status(400);
        }else{
            int loginCode = userService.login(user);
            System.out.println("login code: " + loginCode);
            if (loginCode <= 0) {
                switch (loginCode) {
                    case -1:
                        context.result("Your passcode does not match!").status(400);
                        break;
                    case 0:
                        context.result("User does not exist, please register user").status(401);
                        break;
                }
            }else if(loginCode>0){
                context.result("Successful login!").status(201);

            }
        }
    };
}