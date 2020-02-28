package com.example.bookmarks.service;

import com.example.bookmarks.data.Bookmarks;

public interface RemoveDuplicatesService {

    void removeBookmarksDuplicatesByName(Bookmarks bookmarks, String folderName, String username);
}
