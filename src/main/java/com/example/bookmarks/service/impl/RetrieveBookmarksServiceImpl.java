package com.example.bookmarks.service.impl;

import com.example.bookmarks.data.Bookmarks;
import com.example.bookmarks.data.BookmarksRepository;
import com.example.bookmarks.exception.BookmarksNotFoundException;
import com.example.bookmarks.service.RetrieveBookmarksService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class RetrieveBookmarksServiceImpl implements RetrieveBookmarksService {

    private final BookmarksRepository bookmarksRepository;

    @Override
    public Bookmarks retrieveBookmarksByUser(Principal principal) {
        String userName = principal.getName();
        return bookmarksRepository.findOne(Example.of(bookmarksOfUser(userName))).orElseThrow(() -> new BookmarksNotFoundException(userName));
    }

    private Bookmarks bookmarksOfUser(String name) {
        Bookmarks bookmarks = new Bookmarks();
        bookmarks.setUser(name);
        return bookmarks;
    }
}
