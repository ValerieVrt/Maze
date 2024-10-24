package edu.school21.maze.model;

import java.util.ArrayList;
import java.util.List;

public class LineOfSets {
    private List<Integer> line;
    private List<Integer> indexList;

    public LineOfSets() {
        this.line = new ArrayList<>();
        this.indexList = new ArrayList<>();
    }

    public List<Integer> getLine() {
        return line;
    }

    public List<Integer> getIndexList() {
        return indexList;
    }
}
