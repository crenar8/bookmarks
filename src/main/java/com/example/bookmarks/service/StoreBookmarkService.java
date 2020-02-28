package com.example.bookmarks.service;

import com.example.bookmarks.data.Bookmarks;
import com.example.bookmarks.dto.BookmarkDto;

public interface StoreBookmarkService {

    void storeBookmark(BookmarkDto bookmarkDto, Bookmarks bookmarks, String username);
}
