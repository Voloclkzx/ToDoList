package storage;

import exceptions.StorageException;
import model.Task;

public class InMemoryTaskStorage implements TaskStorage{
    private Task[] taskArray = new Task[101]; //максимум 100 задач
    private int count = 0;

    @Override
    public void add(Task task) throws StorageException {
        if (count < 100) {
            taskArray[count] = task;
            count++;
        } else {
            throw new StorageException("Storage is full");
        }

    }
    @Override
    public void remove(Task task) {
        boolean flag = false;
        for (int i = 0; i < count; i++) {
            if (taskArray[i].equals(task) || flag) {
                taskArray[i] = taskArray[i+1];
                flag = true;
            }
        }
        taskArray[count-1] = null;
        count--;
    }

    public Task[] showAllTasks() {
        return taskArray;
    }


}
