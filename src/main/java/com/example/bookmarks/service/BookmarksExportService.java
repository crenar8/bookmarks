package com.example.bookmarks.service;

import com.example.bookmarks.data.Bookmarks;

public interface BookmarksExportService {

    String toHtml(Bookmarks bookmarks);
}
