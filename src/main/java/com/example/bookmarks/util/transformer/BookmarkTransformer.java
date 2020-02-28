package com.example.bookmarks.util.transformer;

import com.example.bookmarks.data.Bookmark;
import com.example.bookmarks.dto.BookmarkDto;
import org.apache.commons.collections4.Transformer;

public class BookmarkTransformer implements Transformer<BookmarkDto, Bookmark> {

    @Override
    public Bookmark transform(BookmarkDto bookmarkDto) {
        Bookmark bookmark = new Bookmark();
        bookmark.setName(bookmarkDto.getName());
        bookmark.setUrl(bookmarkDto.getUrl());
        bookmark.setLogo(bookmarkDto.getLogo());
        return bookmark;
    }
}
