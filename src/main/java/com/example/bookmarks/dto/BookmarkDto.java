package com.example.bookmarks.dto;

import lombok.Data;

import java.net.URI;

@Data
public class BookmarkDto {

    private String name;
    private URI url;
    private URI logo;
    private String folderName;
}
