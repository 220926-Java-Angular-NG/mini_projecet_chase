package com.revature;

import com.revature.controllers.UserController;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
        }).start(8080);
        UserController userController = new UserController();
        app.post("/registerUser", userController.createNewUser);
        app.post("/userLogin", userController.logUserIn);
    }
}