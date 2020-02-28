package com.example.bookmarks.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookmarksFolder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "bookmarks_id")
    private Bookmarks bookmarks;

    @OneToMany(mappedBy = "folder")
    private List<Bookmark> bookmarkList;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "folder_id")
    private BookmarksFolder folder;

    @OneToMany(mappedBy = "folder")
    private List<BookmarksFolder> bookmarksFolders;
}
