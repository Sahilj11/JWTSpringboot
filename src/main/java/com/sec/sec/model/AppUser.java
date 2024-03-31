package com.sec.sec.model;

import java.util.List;

import org.hibernate.type.TrueFalseConverter;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * AppUser
 */
@NoArgsConstructor
@Getter
@Entity
@Table(name = "user_table")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
     
    private String name;
    @Column(unique = true)
    private String username;
    private String password;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> role;


    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(List<Role> role) {
        this.role = role;
    }

}
