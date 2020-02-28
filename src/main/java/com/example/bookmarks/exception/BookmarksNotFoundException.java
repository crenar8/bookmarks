package com.example.bookmarks.exception;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookmarksNotFoundException extends RuntimeException {

    private final String userName;

    @Override
    public String getMessage() {
        return StringUtils.join("Bookmarks not found for user ", userName);
    }
}
