package com.example.bookmarks.service.impl;

import com.example.bookmarks.data.Bookmark;
import com.example.bookmarks.data.BookmarkRepository;
import com.example.bookmarks.data.Bookmarks;
import com.example.bookmarks.data.BookmarksFolder;
import com.example.bookmarks.service.RemoveDuplicatesService;
import com.example.bookmarks.service.RetrieveBookmarksFolderService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class RemoveDuplicatesServiceImpl implements RemoveDuplicatesService {

    private final BookmarkRepository bookmarkRepository;
    private final RetrieveBookmarksFolderService retrieveBookmarksFolderService;

    @Override
    public void removeBookmarksDuplicatesByName(Bookmarks bookmarks, String folderName, String username) {
        Set<Bookmark> bookmarksToRemove = new HashSet<>();
        if (StringUtils.isBlank(folderName)) {
            findBookmarksToRemove(bookmarks.getBookmarks(), bookmarksToRemove);
        } else {
            BookmarksFolder folder = retrieveBookmarksFolderService.getBookmarksFolderByName(username, bookmarks, folderName);
            findBookmarksToRemove(folder.getBookmarkList(), bookmarksToRemove);
        }
        if (CollectionUtils.isNotEmpty(bookmarksToRemove)) {
            bookmarkRepository.deleteAll(bookmarksToRemove);
        }
    }

    private void findBookmarksToRemove(List<Bookmark> bookmarkList, Set<Bookmark> bookmarksToRemove) {
        Map<String, List<Bookmark>> bookmarksByNameMap = bookmarkList.stream().collect(groupingBy(Bookmark::getName));
        Map<String, List<Bookmark>> duplicatesMap = bookmarksByNameMap.entrySet().stream().filter(stringListEntry -> stringListEntry.getValue().size() > NumberUtils.INTEGER_ONE).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        duplicatesMap.forEach((key1, bookmarksDuplicated) -> {
            bookmarksDuplicated.remove(bookmarksDuplicated.stream().min(Comparator.comparing(Bookmark::getId)).orElse(null));
            bookmarksToRemove.addAll(bookmarksDuplicated);
        });
    }

}
