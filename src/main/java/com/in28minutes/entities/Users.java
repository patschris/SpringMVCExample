package com.in28minutes.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.List;

/**
 * The User class.
 * Defines the credentials of the app's users.
 *
 * @author Christos Patsouras
 * @version 1
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
     * The one-to-many relationship with the todo table.
     */
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Todo> todos;

    @Override
    public String toString() {
        return "Users{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}