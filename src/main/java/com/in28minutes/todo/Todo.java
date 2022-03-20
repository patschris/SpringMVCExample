package com.in28minutes.todo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    private int id;

    private String user;

    @Size(min = 6, message = "Enter at least 6 characters")
    private String desc;

    private Date targetDate;

    private boolean isDone;
}
