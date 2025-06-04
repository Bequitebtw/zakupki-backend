package ru.pro.zakupki.exception;

public class NotFoundProductException extends RuntimeException {
    private Long id;

    public NotFoundProductException(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Товар с id: " + id + "не найден";
    }
}
