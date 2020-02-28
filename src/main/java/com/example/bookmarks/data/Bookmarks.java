package com.example.bookmarks.data;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Bookmarks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user")
    private String user;

    @OneToMany(mappedBy = "bookmarks")
    private List<BookmarksFolder> bookmarksFolders;

    @OneToMany(mappedBy = "bookmarkList")
    private List<Bookmark> bookmarks;
}
