package ru.pro.zakupki.exception;

public class ExistEmailException extends RuntimeException {
    private String email;

    public ExistEmailException(String email) {
        this.email = email;
    }

    @Override
    public String getMessage() {
        return String.format("Email %s уже занят", email);
    }

}
