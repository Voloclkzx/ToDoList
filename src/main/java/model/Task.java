package model;
import java.time.LocalDateTime;

public class Task {
    private int id;
    private String name;
    private String description;
    private LocalDateTime dateTime; //when to do the task
    private TaskStatus status;

    private static int idCount = 1;

    public Task(String name, String description, LocalDateTime dateTime) {
        this.id = idCount++;
        this.name = name;
        this.dateTime = dateTime;
        this.description = description;
        this.status = TaskStatus.TODO;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public TaskStatus getStatus() {
        return status;
    }

    public void changeName(String name) {
        this.name = name;
    }
    public void changeDescription(String description) {
        this.description = description;
    }
    public void changeDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    public void changeStatus(TaskStatus status) {
        this.status = status;
    }


    public String toString() {
        if (this.dateTime != null) {
            return this.name + "\n" + this.dateTime + "\n" + this.description + "\n" + "Status: " + this.status + "\n-----------------------------------";

        } else {
            return this.name + "\n" + this.description + "\n" + "Status: " + this.status + "\n-----------------------------------";
        }

    }

    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Task)) return false;
        Task otherTask = (Task) other;
        return id == otherTask.id;
    }



}
