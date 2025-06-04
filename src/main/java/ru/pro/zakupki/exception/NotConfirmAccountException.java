package ru.pro.zakupki.exception;

public class NotConfirmAccountException extends RuntimeException {
    public NotConfirmAccountException() {
        super("Account is not confirmed");
    }
}