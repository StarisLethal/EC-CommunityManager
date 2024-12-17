package fr.etrangecantina.services.msusers.controllers;

import fr.etrangecantina.services.msusers.model.User;
import fr.etrangecantina.services.msusers.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public ResponseEntity<Iterable<User>> getUsers() {
        try {
            logger.info("Getting all users");
            Iterable<User> users = userService.findAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") UUID id) {
        try {
            logger.info("Getting user with id {}", id);
            Optional<User> user = userService.findById(id);
            return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        try {
            logger.info("Saving user {}", user);
            userService.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<User> editUser(@RequestParam("id") UUID id, @RequestBody User user) {
        try {
            logger.info("Editing user with id {}", id);
            return new ResponseEntity<>(userService.edit(id, user), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);}
    }

    @DeleteMapping("/delete")
    public ResponseEntity<User> deleteUser(@RequestParam("id") UUID id) {
        try {
            logger.info("Deleting user with id {}", id);
            userService.delete(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/age")
    public ResponseEntity<Integer> getUserAge(@RequestParam UUID id) {
        try {
            logger.info("Getting age of user with id {}", id);
            userService.getAgeByBirthdate(id);
            return new ResponseEntity<>(userService.getAgeByBirthdate(id), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

