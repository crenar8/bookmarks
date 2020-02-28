package com.example.bookmarks.service.impl;

import com.example.bookmarks.data.Bookmarks;
import com.example.bookmarks.data.BookmarksFolder;
import com.example.bookmarks.data.BookmarksFolderRepository;
import com.example.bookmarks.exception.FolderNotFoundException;
import com.example.bookmarks.service.CreateFolderService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateFolderServiceImpl implements CreateFolderService {

    private final BookmarksFolderRepository bookmarksFolderRepository;

    @Override
    public void createFolder(Bookmarks bookmarks, List<String> folderNameChain, String username) {
        BookmarksFolder folderToPersist = null;
        if (folderNameChain.size() == NumberUtils.INTEGER_ONE) {
            folderToPersist = buildFolder(bookmarks, folderNameChain.stream().findFirst().orElse(null));
        } else {
            BookmarksFolder parentFolder = null;
            Iterator<String> iter = folderNameChain.iterator();
            List<BookmarksFolder> actualLevelFolders = bookmarks.getBookmarksFolders();
            while (iter.hasNext()) {
                String next = iter.next();
                if (iter.hasNext()) {
                    parentFolder = actualLevelFolders.stream().filter(folder -> StringUtils.equals(next, folder.getName())).findFirst().orElseThrow(() -> new FolderNotFoundException(username, folderNameChain));
                }
            }
            folderToPersist = buildFolder(null, folderNameChain.get(folderNameChain.size() - 1), parentFolder);
        }
        bookmarksFolderRepository.save(folderToPersist);
    }


    private BookmarksFolder buildFolder(Bookmarks bookmarks, String folderName) {
        return buildFolder(bookmarks, folderName, null);
    }

    private BookmarksFolder buildFolder(Bookmarks bookmarks, String folderName, BookmarksFolder bookmarksFolder) {
        BookmarksFolder folder = new BookmarksFolder();
        folder.setBookmarks(bookmarks);
        folder.setName(folderName);
        folder.setFolder(bookmarksFolder);
        return folder;
    }
}
