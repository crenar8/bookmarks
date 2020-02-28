package com.example.bookmarks.service;

import com.example.bookmarks.data.Bookmarks;

import java.util.List;

public interface CreateFolderService {


    void createFolder(Bookmarks bookmarks, List<String> folderNameChain, String username);
}
