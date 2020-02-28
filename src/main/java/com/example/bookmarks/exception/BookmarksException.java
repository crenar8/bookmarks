package com.example.bookmarks.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookmarksException extends RuntimeException{

    private final String message;

    @Override
    public String getMessage() {
        return message;
    }
}
