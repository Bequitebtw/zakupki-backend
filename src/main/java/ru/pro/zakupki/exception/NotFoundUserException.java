package ru.pro.zakupki.exception;

public class NotFoundUserException extends RuntimeException{

    private String name;

    public NotFoundUserException( String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return "Пользователь с email: " + name + "не найден";
    }
}
