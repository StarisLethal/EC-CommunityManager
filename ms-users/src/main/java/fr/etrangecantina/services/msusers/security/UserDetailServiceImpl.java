package fr.etrangecantina.services.msusers.security;

import fr.etrangecantina.services.msusers.DTO.UserDetailsDTO;
import fr.etrangecantina.services.msusers.model.Role;
import fr.etrangecantina.services.msusers.model.User;
import fr.etrangecantina.services.msusers.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + email));
        return new UserDetailsDTO(
                user.getEmail(),
                user.getPassword(),
                user.getRoles(),
                user.isEnabled(),
                !user.isAccountLocked()
        );
    }
}
