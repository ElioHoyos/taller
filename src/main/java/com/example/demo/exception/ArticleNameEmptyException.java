package com.example.demo.exception;

public class ArticleNameEmptyException extends RuntimeException {
    public ArticleNameEmptyException(String message) {
        super(message);
    }

    // Subclase para nombres duplicados (manejo más específico)
    public static class ArticleNameDuplicateException extends ArticleNameEmptyException {
        public ArticleNameDuplicateException(String message) {
            super(message);
        }
    }
}