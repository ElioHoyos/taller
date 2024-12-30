package com.example.demo.exception;

public class CategoryNameEmptyException extends RuntimeException{

    public CategoryNameEmptyException(String message) {
        super(message);
    }
    public class CategoryNameDuplicateException extends RuntimeException {
        public CategoryNameDuplicateException(String message) {
            super(message);
        }
    }

}
