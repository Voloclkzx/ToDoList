package model;

import java.util.Date;

public class Task {
    public String name;
    public String description;
    public Date date; //when to do the task
    public States state;

    public Task(String name) {
        this.name = name;
        this.state = States.TODO;
    }
    public Task(String name, Date date) {
        this(name);
        this.date = date;
    }
    public Task(String name, String description) {
        this(name);
        this.description = description;
    }
    public Task(String name, Date date, String description) {
        this(name, date);
        this.description = description;
    }
    public Task(String name, String description, Date date) {
        this(name, date);
        this.description = description;
    }


    public String toString() {
        if (this.date != null) {
            return this.name + "\n" + this.date + "\n" + this.description + "\n" + "Status: " + this.state + "\n-----------------------------------";

        } else {
            return this.name + "\n" + this.description + "\n" + "Status: " + this.state + "\n-----------------------------------";
        }

    }
}
