package com.flexone.catchwiseserver.util;

public enum MapMarkerView {
    LAKE("lakes_map_markers"),
    WATER_ACCESS("water_accesses_map_markers");

    private String viewName;

    MapMarkerView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }
}
