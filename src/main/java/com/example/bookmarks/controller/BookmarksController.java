package com.example.bookmarks.controller;

import com.example.bookmarks.data.Bookmarks;
import com.example.bookmarks.dto.BookmarkDto;
import com.example.bookmarks.exception.BookmarksException;
import com.example.bookmarks.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookmarks", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookmarksController {

    private final CreateFolderService createFolderService;
    private final StoreBookmarkService storeBookmarkService;
    private final SortBookmarksService sortBookmarksService;
    private final BookmarksExportService bookmarksExportService;
    private final RemoveDuplicatesService removeDuplicatesService;
    private final RetrieveBookmarksService retrieveBookmarksService;


    @GetMapping
    public Bookmarks retrieve(Principal principal, @RequestParam(required = false, defaultValue = "uns") SortOrder sortOrder) {
        Bookmarks bookmarks = retrieveBookmarksService.retrieveBookmarksByUser(principal);
        return sortBookmarksService.sortBookmarks(sortOrder, bookmarks);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void upload(Principal principal, @RequestBody() BookmarkDto bookmarkDto) {
        Bookmarks bookmarks = retrieveBookmarksService.retrieveBookmarksByUser(principal);
        storeBookmarkService.storeBookmark(bookmarkDto, bookmarks, principal.getName());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/folders/{folderName}")
    public void createFolder(Principal principal, @PathVariable String folderName) {
        Bookmarks bookmarks = retrieveBookmarksService.retrieveBookmarksByUser(principal);
        List<String> folderNameChain = Arrays.asList(StringUtils.split(folderName, "."));
        checkFolderName(folderName);
        createFolderService.createFolder(bookmarks, folderNameChain, principal.getName());
    }

    @DeleteMapping(path = "/duplicates", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void removeDuplicates(Principal principal, @RequestBody BookmarkDto bookmarkDto, @RequestParam(name = "folderName") String folderName) {
        Bookmarks bookmarks = retrieveBookmarksService.retrieveBookmarksByUser(principal);
        removeDuplicatesService.removeBookmarksDuplicatesByName(bookmarks, folderName, principal.getName());
    }

    @GetMapping(path = "/export")
    public void export(Principal principal, @RequestParam(required = false, defaultValue = "uns") SortOrder sortOrder, HttpServletResponse httpServletResponse) throws IOException {
        Bookmarks bookmarks = retrieveBookmarksService.retrieveBookmarksByUser(principal);
        Bookmarks bookmarksSorted = sortBookmarksService.sortBookmarks(sortOrder, bookmarks);
        String html = bookmarksExportService.toHtml(bookmarksSorted);
        prepareHtmlToDownload(principal, httpServletResponse, html);
    }

    private void prepareHtmlToDownload(Principal principal, HttpServletResponse httpServletResponse, String html) throws IOException {
        String htmlFileName = StringUtils.join(principal.getName(), "-bookmarks.html");
        File newHtmlFile = new File(htmlFileName);
        FileUtils.writeStringToFile(newHtmlFile, html);
        httpServletResponse.setContentType(MediaType.TEXT_HTML_VALUE);
        httpServletResponse.setHeader("Content-Disposition", StringUtils.join("attachment;filename=", htmlFileName));
        IOUtils.copy(new FileInputStream(newHtmlFile), httpServletResponse.getOutputStream());
    }

    private void checkFolderName(String folderName) {
        if (StringUtils.isBlank(folderName)) {
            throw new BookmarksException("Folder name musn't be empty!");
        }
    }

}