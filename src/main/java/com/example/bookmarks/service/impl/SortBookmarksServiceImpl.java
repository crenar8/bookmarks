package com.example.bookmarks.service.impl;

import com.example.bookmarks.data.Bookmark;
import com.example.bookmarks.data.Bookmarks;
import com.example.bookmarks.data.BookmarksFolder;
import com.example.bookmarks.service.SortBookmarksService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;

@Service
public class SortBookmarksServiceImpl implements SortBookmarksService {

    @Override
    public Bookmarks sortBookmarks(SortOrder sortOrder, Bookmarks bookmarks) {
        if (sortOrder != SortOrder.UNSORTED) {
            Comparator<Bookmark> comparator = Comparator.comparing(Bookmark::getName);
            if (sortOrder == SortOrder.DESCENDING) {
                comparator = comparator.reversed();
            }
            bookmarks.getBookmarks().sort(comparator);
            List<BookmarksFolder> bookmarksFolders = bookmarks.getBookmarksFolders();
            sortInFolders(comparator, bookmarksFolders);
        }
        return bookmarks;
    }

    private void sortInFolders(Comparator<Bookmark> comparator, List<BookmarksFolder> bookmarksFolders) {
        while (CollectionUtils.isEmpty(bookmarksFolders)) {
            bookmarksFolders.forEach(folder -> {
                folder.getBookmarkList().sort(comparator);
                sortInFolders(comparator, folder.getBookmarksFolders());
            });
        }
    }
}
