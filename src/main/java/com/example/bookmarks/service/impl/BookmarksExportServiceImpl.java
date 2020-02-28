package com.example.bookmarks.service.impl;

import com.example.bookmarks.data.Bookmark;
import com.example.bookmarks.data.Bookmarks;
import com.example.bookmarks.data.BookmarksFolder;
import com.example.bookmarks.service.BookmarksExportService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
public class BookmarksExportServiceImpl implements BookmarksExportService {


    @Override
    public String toHtml(Bookmarks bookmarks) {
        return StringUtils.join("<!DOCTYPE NETSCAPE-Bookmark-file-1>\n" +
                        "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">\n" +
                        "<TITLE>Bookmarks</TITLE>\n" +
                        "<H1>Menu segnalibri</H1>\n" +
                        "\n" +
                        "<DL><p>\n",
                foldersToHtml(bookmarks.getBookmarksFolders()), "\n",
                bookmarksToHtml(bookmarks.getBookmarks()), "\n" +
                        "</DL>");
    }

    private String foldersToHtml(List<BookmarksFolder> bookmarksFolders) {
        String result = StringUtils.EMPTY;
        if (CollectionUtils.isNotEmpty(bookmarksFolders)) {
            for (BookmarksFolder folder : bookmarksFolders) {
                result = StringUtils.join(result, folderToHtml(folder), "\n");
            }
        }
        return result;
    }

    private String folderToHtml(BookmarksFolder folder) {
        return StringUtils.join("<DT><H3 PERSONAL_TOOLBAR_FOLDER=\"true\">", folder.getName(), "</H3>", "\n",
                "<DL><p>", foldersToHtml(folder.getBookmarksFolders()), bookmarksToHtml(folder.getBookmarkList()), "\n" +
                        "</DL><p>");
    }

    private String bookmarksToHtml(List<Bookmark> bookmarks) {
        String result = StringUtils.EMPTY;
        if (CollectionUtils.isNotEmpty(bookmarks)) {
            for (Bookmark bookmark : bookmarks) {
                result = bookmarkToHtml(bookmark);
            }
        }
        return result;
    }

    private String bookmarkToHtml(Bookmark bookmark) {
        return StringUtils.join("<DT><A", getHRef(bookmark.getUrl()), getIcon(bookmark.getLogo()), ">", bookmark.getName(), "</A>", "\n");
    }

    private String getHRef(URI url) {
        String result = StringUtils.EMPTY;
        if (url != null) {
            result = StringUtils.join(" HREF=\"", url, "\"");
        }
        return result;
    }

    private String getIcon(URI logo) {
        String result = StringUtils.EMPTY;
        if (logo != null) {
            result = StringUtils.join(" ICON_URI=\"", logo, "\"");
        }
        return result;
    }
}
