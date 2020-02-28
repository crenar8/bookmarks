package com.example.bookmarks.service.impl;

import com.example.bookmarks.data.Bookmark;
import com.example.bookmarks.data.BookmarkRepository;
import com.example.bookmarks.data.Bookmarks;
import com.example.bookmarks.data.BookmarksFolder;
import com.example.bookmarks.dto.BookmarkDto;
import com.example.bookmarks.service.RetrieveBookmarksFolderService;
import com.example.bookmarks.service.RetrieveBookmarksService;
import com.example.bookmarks.service.StoreBookmarkService;
import com.example.bookmarks.util.transformer.BookmarkTransformer;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Transformer;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class StoreBookmarkServiceImpl implements StoreBookmarkService {

    private static final Transformer<BookmarkDto, Bookmark> BOOKMARK_TRANSFORMER = new BookmarkTransformer();

    private final BookmarkRepository bookmarkRepository;
    private final RetrieveBookmarksFolderService retrieveBookmarksFolderService;

    @Override
    public void storeBookmark(BookmarkDto bookmarkDto, Bookmarks bookmarks, String username) {
        String folderName = bookmarkDto.getFolderName();
        Bookmark bookmarkToPersist = BOOKMARK_TRANSFORMER.transform(bookmarkDto);
        if (folderName != null) {
            BookmarksFolder bookmarksFolder = retrieveBookmarksFolderService.getBookmarksFolderByName(username, bookmarks, folderName);
            bookmarkToPersist.setFolder(bookmarksFolder);
        } else {
            bookmarkToPersist.setBookmarkList(bookmarks);
        }
        bookmarkRepository.save(bookmarkToPersist);
    }
}
