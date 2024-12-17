package fr.etrangecantina.services.msfrontend.controller;

import fr.etrangecantina.services.msfrontend.model.User;
import fr.etrangecantina.services.msfrontend.client.UserClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class UserController {

    private final UserClient userClient;

    public UserController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        List<User> users = userClient.getAllUsers();
        model.addAttribute("users", users);
        return "user-list"; // Correspond au fichier user-list.html
    }

    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable String id, Model model) {
        User user = userClient.getUserById(id);
        model.addAttribute("user", user);
        return "user-detail"; // Correspond au fichier user-detail.html
    }

    @GetMapping("/users/edit")
    public String showEditForm(@RequestParam("id") String id, Model model) {
        User user = userClient.getUserById(id); // Récupère les données utilisateur à modifier
        model.addAttribute("user", user);
        return "user-edit"; // Page HTML pour modifier l'utilisateur
    }

    @PostMapping("/users/edit")
    public String editUser(@RequestParam("id") String id, User user, Model model) {
        User updatedUser = userClient.editUser(id, user); // Appelle l'API backend via Feign
        model.addAttribute("user", updatedUser);
        model.addAttribute("message", "Utilisateur mis à jour avec succès !");
        return "user-detail"; // Redirige vers la page de détails
    }
}