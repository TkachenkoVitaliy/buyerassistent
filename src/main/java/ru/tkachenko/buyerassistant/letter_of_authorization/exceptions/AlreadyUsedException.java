package ru.tkachenko.buyerassistant.letter_of_authorization.exceptions;

public class AlreadyUsedException extends RuntimeException{
    public AlreadyUsedException(String message) {
        super(message);
    }
}
