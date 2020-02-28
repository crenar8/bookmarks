package com.example.bookmarks.converter;

import org.springframework.core.convert.converter.Converter;

import javax.swing.*;

public class SortOrderConverter implements Converter<String, SortOrder> {
    @Override
    public SortOrder convert(String s) {
        SortOrder sortOrder;
        switch (s) {
            case "asc":
                sortOrder = SortOrder.ASCENDING;
                break;
            case "desc":
                sortOrder = SortOrder.DESCENDING;
                break;
            default:
                sortOrder = SortOrder.UNSORTED;
        }
        return sortOrder;
    }
}
