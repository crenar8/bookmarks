package com.example.bookmarks.service;

import com.example.bookmarks.data.Bookmarks;

import javax.swing.*;

public interface SortBookmarksService {
    Bookmarks sortBookmarks(SortOrder sortOrder, Bookmarks bookmarks);
}
