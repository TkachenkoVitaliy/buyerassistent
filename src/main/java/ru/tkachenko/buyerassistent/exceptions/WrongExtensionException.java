package ru.tkachenko.buyerassistent.exceptions;

public class WrongExtensionException extends IllegalArgumentException{
    public WrongExtensionException(String message) {
        super(message);
    }
}
