package com.example.demo.exception;

public class ArticleCodeEmptyException extends RuntimeException {
  public ArticleCodeEmptyException(String message) {
    super(message);
  }

  public static class ArticleCodeDuplicateException extends ArticleCodeEmptyException {
    public ArticleCodeDuplicateException(String message) {
      super(message);
    }
  }
}