package ru.kata.spring.rest;

import ru.kata.spring.rest.configuration.MyConfig;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.kata.spring.rest.model.User;


public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Communication communication = context.getBean("communication", Communication.class);

        // вывод всех пользователей
        System.out.println(communication.getUsers());

        //добавление пользователя
        User user = new User(3L, "James", "Brown", (byte) 29);
        System.out.print(communication.saveUser(user));

        // изменение пользователя
        User updateUser = new User(3L, "Thomas", "Shelby", (byte) 29);
        System.out.print(communication.updateUser(updateUser));

        // Удаление пользователя
        System.out.print(communication.deleteUser(3L));
    }
}
