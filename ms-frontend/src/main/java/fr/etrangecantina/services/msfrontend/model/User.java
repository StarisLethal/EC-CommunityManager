package fr.etrangecantina.services.msfrontend.model;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class User {
    private String id;
    private String username;
    private String email;
}
