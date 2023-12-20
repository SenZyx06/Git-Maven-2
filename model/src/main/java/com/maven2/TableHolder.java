package com.maven2;

import java.util.Map;

public class TableHolder {
    private Map<String, Map<String, String>> table;

    public TableHolder(Map<String, Map<String, String>> table) {
        this.table = table;
    }

    public Map<String, Map<String, String>> getTable() {
        return this.table;
    }

    public void setTable(Map<String, Map<String, String>> table) {
        this.table = table;
    }
}
