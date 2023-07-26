package com.flexone.catchwiseserver.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class UserEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "email", unique = true, nullable = false)
        private String email;

        @JsonIgnore
        @Column(name = "password_digest", nullable = false)
        private String password;

        @JsonIgnore
        @Column(name = "session_token", unique = true)
        private String sessionToken;

        @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
        @JoinTable(name = "users_roles",
                joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name= "role_id", referencedColumnName = "id"))
        private List<Role> roles = new ArrayList<>();
}


