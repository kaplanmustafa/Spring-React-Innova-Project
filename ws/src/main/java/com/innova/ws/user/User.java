package com.innova.ws.user;

import com.innova.ws.note.Note;
import com.innova.ws.role.Role;
import com.innova.ws.user.validator.UniqueUsername;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Data // Auto Getter and Setter
@Entity
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "{ws.constraints.username.NotNull.message}")
    @Size(min = 4, max= 255)
    @UniqueUsername
    private String username;

    @NotNull
    @Size(min = 2, max= 255)
    private String fullName;

    @NotNull
    @Size(min = 8, max= 255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{ws.constraints.password.Pattern.message}")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Note> notes;

    @OneToOne(mappedBy="user", cascade = CascadeType.REMOVE)
    private Role role;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("Role_" + role.getName());
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}