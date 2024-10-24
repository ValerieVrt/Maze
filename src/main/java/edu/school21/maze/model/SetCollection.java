package edu.school21.maze.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetCollection {
    private List<Integer> indexList;
    private int numberOfCellsInSet;
    private int numberOfBottomWallsInSet;

    public SetCollection(int numberOfCellsInSet, int numberOfBottomWallsInSet, int size) {
        this.numberOfCellsInSet = numberOfCellsInSet;
        this.numberOfBottomWallsInSet = numberOfBottomWallsInSet;
        this.indexList = Stream.generate(() -> 0).limit(size).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Integer> getIndexList() {
        return indexList;
    }

    public int getNumberOfCellsInSet() {
        return numberOfCellsInSet;
    }

    public int getNumberOfBottomWallsInSet() {
        return numberOfBottomWallsInSet;
    }

    public void setNumberOfCellsInSet(int numberOfCellsInSet) {
        this.numberOfCellsInSet = numberOfCellsInSet;
    }

    public void setNumberOfBottomWallsInSet(int numberOfBottomWallsInSet) {
        this.numberOfBottomWallsInSet = numberOfBottomWallsInSet;
    }
}
