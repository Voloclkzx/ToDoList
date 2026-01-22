import Exceptions.NotFoundTaskException;
import model.States;
import model.Task;
import model.TaskArray;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;
    public static void main(String[] args) {
        startProgram();
        while (true) {
            createMenu();
            int selection = scanner.nextInt();
            switch (selection) {
                case 1:
                    createTask(); break;
                case 2:
                    deleteTask(); break;
                case 3:
                    editTask(); break;
                case 4:
                    TaskArray.showTasks(); break;
            }
//            if (selection == 1) {
//                createTask();
//            } else if (selection == 2) {
//                deleteTask();
//            } else if (selection == 3) {
//                editTask();
//            } else if (selection == 4) {
//                TaskArray.showTasks();
//            }

        }
    }
    public static void startProgram() {
        scanner = new Scanner(System.in);
        System.out.println("TO DO LIST is starting to work");
    }
    public static void createMenu() {
        System.out.println("Select an action: ");
        System.out.println("1) Create a task\n" +
                "2) Delete a task\n" +
                "3) Edit a task\n" +
                "4) Show tasks");
    }

    public static void createTask() {
        scanner.nextLine();
        System.out.println("Enter the task name: ");
        String name = scanner.nextLine();
        System.out.println("Enter the task description: ");
        String description = scanner.nextLine();
        System.out.println("Enter the task date in format: \n\"YYYY MM DD HH MM\"\nor enter \"now\"");
        String strDate = scanner.nextLine();
        if (strDate.equals("now")) {
            TaskArray.addTask(new Task(name, description, new Date()));
        } else {
            while (true) {
                try {
                    int[] date = parseDate(strDate);
                    TaskArray.addTask(new Task(name, description, new Date(date[0], date[1], date[2], date[3], date[4])));
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                    strDate = scanner.nextLine();
                }
            }

        }
    }
    public static void deleteTask() {
        System.out.println("Select a task to delete");
        TaskArray.showTasks();
        while (true) {
            try {
                String selectTask = scanner.next();
                if (selectTask.equals("exit")) {
                    break;
                }
                TaskArray.removeTask(TaskArray.findTask(selectTask));
                break;
            } catch (NotFoundTaskException e) {
                System.out.println("Task is not found");
            }
        }
    }
    public static void editTask() {
        Task changeTask = null;
        System.out.println("Select a task to edit: ");
        TaskArray.showTasks();
        while (true) {
            try {
                String selectTask = scanner.next();
                if (selectTask.equals("exit")) {
                    break;
                }
                changeTask = TaskArray.findTask(selectTask);
                break;
            } catch (NotFoundTaskException e) {
                System.out.println("Task is not found");
            }
        }
        if (changeTask != null) {
            System.out.println("Select a task element to edit: \n" +
                    "1) name\n" +
                    "2) description\n" +
                    "3) date\n" +
                    "4) state");
            int selectChange;
            while (true) {
                try {
                    selectChange = scanner.nextInt();
                    if (selectChange < 1 || selectChange > 4) {
                        System.out.println("Enter a number from 1 to 4");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Enter a number from 1 to 4");
                    scanner.nextLine();
                }
            }
            if (selectChange == 1) {
                scanner.nextLine();
                System.out.println("Enter new Task name");
                String newTaskName = scanner.nextLine();
                TaskArray.toChangeTaskName(changeTask, newTaskName);
                System.out.println("Name is changed successfully");
            } else if (selectChange == 2) {
                System.out.println("Enter new Task description");
                String newTaskDescription = scanner.nextLine();
                TaskArray.toChangeTaskDescription(changeTask, newTaskDescription);
                System.out.println("Description is changed successfully");
            } else if (selectChange == 3) {
                scanner.nextLine();
                System.out.println("Enter new Task date");
                String changeDate = scanner.nextLine();
                while (true) {
                    try {
                        int[] date = parseDate(changeDate);
                        changeTask.date = new Date(date[0], date[1], date[2], date[3], date[4]);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                        changeDate = scanner.nextLine();
                    }
                }
                int[] intChangeDate = parseDate(changeDate);
                Date newTaskDate = new Date(intChangeDate[0], intChangeDate[1], intChangeDate[2], intChangeDate[3], intChangeDate[4]);
                TaskArray.toChangeTaskDate(changeTask, newTaskDate);
                System.out.println("Date is changed successfully");
            } else if (selectChange == 4) {
                System.out.println("Enter new Task state: TODO, INPROGRESS, COMPLETE");
                String changeState = scanner.next();
                States newTaskState = States.valueOf(changeState.toUpperCase());
                TaskArray.toChangeTaskState(changeTask, newTaskState);
                System.out.println("State is changed successfully");
            }
        }
    }

    public static int[] parseDate(String YYYY_MM_DD_HH_MM) {
        // Разбиваем по одному или нескольким пробелам
        String[] stringDate = YYYY_MM_DD_HH_MM.trim().split("\\s+");

        // Проверяем количество элементов
        if (stringDate.length != 5) {
            throw new IllegalArgumentException(
                    "Invalid date format. Expected: YYYY MM DD HH MM (5 numbers separated by spaces)"
            );
        }

        int[] intDate = new int[5];

        // Преобразуем строки в числа
        for (int i = 0; i < 5; i++) {
            try {
                intDate[i] = Integer.parseInt(stringDate[i]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("All elements must be integers. Found: '" + stringDate[i] + "'");
            }
        }

        // Валидация значений
        if (intDate[0] < 1900 || intDate[0] > 2100) {  // Год
            throw new IllegalArgumentException("Year must be between 1900 and 2100");
        }
        if (intDate[1] < 1 || intDate[1] > 12) {       // Месяц
            throw new IllegalArgumentException("Month must be 1-12");
        }
        if (intDate[2] < 1 || intDate[2] > 31) {       // День
            throw new IllegalArgumentException("Day must be 1-31");
        }
        if (intDate[3] < 0 || intDate[3] > 23) {       // Часы
            throw new IllegalArgumentException("Hour must be 0-23");
        }
        if (intDate[4] < 0 || intDate[4] > 59) {       // Минуты
            throw new IllegalArgumentException("Minute must be 0-59");
        }

        intDate[0] -= 1900;  // Вычитаем 1900 из года
        intDate[1] -= 1;     // Уменьшаем месяц на 1 (январь = 0)

        return intDate;
    }
}
