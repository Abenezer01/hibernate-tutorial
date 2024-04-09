package com.example.demo5;

import com.example.demo5.controller.UserController;
import com.example.demo5.models.User;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.hibernate.Transaction;

@Path("/hello-world")
public class HelloResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
    public static void main(String[] args) {
        UserController userDAO = new UserController();

        // Create a new user
        User newUser = new User();
        newUser.setName("John Doe");
        newUser.setEmail("john@example.com");

        // Begin a transaction
        Transaction transaction = null;
        try {
            transaction = userDAO.sessionFactory.getCurrentSession().beginTransaction();

            // Save the user
            userDAO.addUser(newUser);

            // Commit the transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        // Retrieve the user
        User retrievedUser = userDAO.getUser(newUser.getId());
        System.out.println("Retrieved User: " + retrievedUser);

        // Update the user
        if (retrievedUser != null) {
            retrievedUser.setEmail("john.doe@example.com");

            // Begin a new transaction
            transaction = null;
            try {
                transaction = userDAO.sessionFactory.getCurrentSession().beginTransaction();

                // Update the user
                userDAO.updateUser(retrievedUser);
                System.out.println("updated User: " + retrievedUser.getEmail());

                // Commit the transaction
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }

        // Delete the user
        if (retrievedUser != null) {
            // Begin a new transaction
            transaction = null;
            try {
                transaction = userDAO.sessionFactory.getCurrentSession().beginTransaction();

                // Delete the user
//                userDAO.deleteUser(retrievedUser.getId());

                // Commit the transaction
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        }

        userDAO.closeSessionFactory();
    }
}