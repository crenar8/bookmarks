package com.example.bookmarks.service.impl;

import com.example.bookmarks.data.Bookmarks;
import com.example.bookmarks.data.BookmarksFolder;
import com.example.bookmarks.exception.FolderNotFoundException;
import com.example.bookmarks.service.RetrieveBookmarksFolderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class RetrieveBookmarksFolderServiceImpl implements RetrieveBookmarksFolderService {

    @Override
    public BookmarksFolder getBookmarksFolderByName(String userName, Bookmarks bookmarks, String folderName) {
        List<BookmarksFolder> bookmarksFolders = bookmarks.getBookmarksFolders();
        List<String> folderNameChain = Arrays.asList(StringUtils.split(folderName, "."));
        Iterator<String> iter = folderNameChain.iterator();
        BookmarksFolder bookmarksFolder = null;
        while (iter.hasNext()) {
            String folderNameSearched = iter.next();
            bookmarksFolder = bookmarksFolders.stream().filter(folder -> StringUtils.equals(folderNameSearched, folder.getName())).findFirst().orElseThrow(() -> new FolderNotFoundException(userName, folderNameChain));
            bookmarksFolders = bookmarksFolder.getBookmarksFolders();
        }
        return bookmarksFolder;
    }
}
