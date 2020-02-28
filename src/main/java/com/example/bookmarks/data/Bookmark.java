package com.example.bookmarks.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.net.URI;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    private String name;

    @Column(name = "url")
    private URI url;

    @Column(name = "logo")
    private URI logo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "folder_id")
    private BookmarksFolder folder;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "bookmarks_id")
    private Bookmarks bookmarkList;

}
