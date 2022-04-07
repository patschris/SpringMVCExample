package com.in28minutes.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="todo")
public class Todo {
    @Id
    @GeneratedValue(generator = "todo_id_seq")
    @SequenceGenerator(name="todo_id_seq", sequenceName = "todo_id_seq", allocationSize = 1)
    private Integer id;

    @Size(min = 6, message = "Enter at least 6 characters")
    private String description;

    private Date targetDate;

    private boolean isDone;

    @ManyToOne
    @JoinColumn(name = "users")
    private Users users;

    public Todo(String description, Date targetDate, boolean isDone, Users users) {
        this.description = description;
        this.targetDate = targetDate;
        this.isDone = isDone;
        this.users = users;
    }

    @Override
    public String toString() {
        String userString = users!=null ? users.toString():"{}";
        return "Todo{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", targetDate=" + targetDate +
                ", isDone=" + isDone +
                ", users=" + userString +
                '}';
    }
}