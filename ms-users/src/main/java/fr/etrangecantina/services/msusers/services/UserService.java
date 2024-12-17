package fr.etrangecantina.services.msusers.services;

import fr.etrangecantina.services.msusers.DTO.RegistrationRequest;
import fr.etrangecantina.services.msusers.model.User;
import fr.etrangecantina.services.msusers.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User edit(UUID id,User updatedUser) {
        Optional<User> userCheck = userRepository.findById(id);

        if (userCheck.isEmpty()) { throw new IllegalArgumentException("User not found"); }

        User user = userCheck.get();
        boolean updated = false;

        if (updatedUser.getIdDiscord() != null){
            user.setIdDiscord(updatedUser.getIdDiscord());
            updated = true;
        }
        if (updatedUser.getSteamid() != null){
            user.setSteamid(updatedUser.getSteamid());
            updated = true;
        }
        if (updatedUser.getUsername() != null){
            user.setUsername(updatedUser.getUsername());
            updated = true;
        }
        if (updatedUser.getPassword() != null && !passwordEncoder.matches(updatedUser.getPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            updated = true;
        }
        if (updatedUser.getBirthDate() != null){
            user.setBirthDate(updatedUser.getBirthDate());
            updated = true;
        }
        if (updatedUser.getRoles() != null){
            user.setRoles(updatedUser.getRoles());
            updated = true;
        }
        if (updated) {
            user.setUpdatedAt(LocalDateTime.now());
        }
        return userRepository.save(user);
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    public int getAgeByBirthdate(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent() && user.get().getBirthDate() != null) {
            return Period.between(user.get().getBirthDate(), LocalDate.now()).getYears();
        } else throw new RuntimeException("User not found");
    }
}
