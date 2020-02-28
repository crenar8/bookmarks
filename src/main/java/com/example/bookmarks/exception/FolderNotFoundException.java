package com.example.bookmarks.exception;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@ResponseStatus(HttpStatus.NOT_FOUND)
public class FolderNotFoundException extends RuntimeException {

    private final String userName;
    private final List<String> folderNameChain;

    @Override
    public String getMessage() {
        Collections.reverse(folderNameChain);
        Iterator<String> iter = folderNameChain.iterator();
        String message = StringUtils.join("Folder with name ", iter.next());
        while (iter.hasNext()) {
            message = StringUtils.join(message, ", inside the folder with name ", iter.next());
        }
        return StringUtils.join(message, " not found for user ", userName);
    }
}
