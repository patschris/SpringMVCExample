package com.in28minutes.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.List;
import java.util.Objects;

/**
 * The User class.
 * Defines the credentials of the app's users.
 *
 * @author Christos Patsouras
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Users {
    /**
     * The user's username.
     */
    @Id
    private String username;

    /**
     * The user's encrypted password.
     */
    private String password;

    /**
     * Indicates whether the user is enabled to use the app or not.
     */
    private Boolean enabled;

    /**
     * The one-to-many relationship with the todo table.
     */
    @JsonBackReference
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Todo> todos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return username.equals(users.username) && password.equals(users.password) && enabled.equals(users.enabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, enabled);
    }

    @Override
    public String toString() {
        return "Users{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled='" + enabled + '\'' +
                '}';
    }
}