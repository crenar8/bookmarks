package com.example.bookmarks.service;

import com.example.bookmarks.data.Bookmarks;
import com.example.bookmarks.data.BookmarksFolder;

public interface RetrieveBookmarksFolderService {
    BookmarksFolder getBookmarksFolderByName(String userName, Bookmarks bookmarks, String folderName);
}
