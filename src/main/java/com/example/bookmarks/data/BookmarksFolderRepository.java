package com.example.bookmarks.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarksFolderRepository extends JpaRepository<BookmarksFolder, Integer> {
}
