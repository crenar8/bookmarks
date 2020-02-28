package com.example.bookmarks.service;

import com.example.bookmarks.data.Bookmarks;

import java.security.Principal;

public interface RetrieveBookmarksService {
    Bookmarks retrieveBookmarksByUser(Principal principal);
}
