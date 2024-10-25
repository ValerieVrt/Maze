package edu.school21.maze.generation;

import edu.school21.maze.model.LineOfSets;
import edu.school21.maze.model.Maze;
import edu.school21.maze.model.SetCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MazeGenerator {
    private Maze maze;
    private int setFillCounter;
    private List<Integer> decisionArray;
    private LineOfSets lineOfSets;
    private List<SetCollection> setCollectionList;
    private int indexDecisionArray;


    public MazeGenerator(Maze maze) {
        this.maze = maze;
        decisionArray = new ArrayList<>();
        setCollectionList = new ArrayList<>();
        setFillCounter = 1;
        indexDecisionArray = 0;
        lineOfSets = new LineOfSets(maze.getNumberOfCols());
    }

    /**
     * The method generates a random number 0 or 1.
     */
    public void generateRandomNumber() {
        decisionArray = IntStream.generate(() -> ThreadLocalRandom.current().nextInt(2))
                .limit(maze.getNumberOfCols() * maze.getNumberOfRows() * 3L)
                .boxed()
                .collect(Collectors.toList());
    }

    public void mazeGeneration() {
        for (int i = 0; i <= maze.getNumberOfCols()* maze.getNumberOfCols(); i++) {
            setCollectionList.add(new SetCollection(0, 0));
        }
        generateRandomNumber();
        System.out.println(decisionArray);
        for (int i = 0; i < maze.getNumberOfRows(); i++) {
            assignSetsToArrayCells(lineOfSets);
            placeWallOnTheRight(lineOfSets);
            if (i == maze.getNumberOfRows() - 1){
                lastLineProcessing(lineOfSets);
            }else{
                placeWallOnTheBottom(lineOfSets);
            }
            resetSetCollectionList();
            resetCellsWithBottomWalls(lineOfSets);
        }

    }

    private void lastLineProcessing(LineOfSets lineOfSets) {
        for (int i = 0; i < lineOfSets.getLine().size(); i++){
            maze.putBottomWall(1);
            if((i != lineOfSets.getLine().size() - 1) && (!lineOfSets.getLine().get(i).equals(lineOfSets.getLine().get(i + 1)))){
                maze.getRightWall().set((maze.getNumberOfRows() - 1) * maze.getNumberOfCols() + i, 0);
                lineOfSets.getLine().set(i + 1, lineOfSets.getLine().get(i));
            }
        }
    }

    private void resetSetCollectionList() {
        for (Integer index: lineOfSets.getLine()) {
           setCollectionList.get(index).setNumberOfCellsInSet(0);
           setCollectionList.get(index).setNumberOfBottomWallsInSet(0);
        }
    }

    private void resetCellsWithBottomWalls(LineOfSets lineOfSets) {
        for (Integer index : lineOfSets.getIndexSetWithBottomWall()){
            lineOfSets.getLine().set(index, 0);
        }
    }

    private void placeWallOnTheRight(LineOfSets lineOfSets) {
        for (int i = 0; i < lineOfSets.getLine().size(); i++) {
            if (decisionArray.get(indexDecisionArray++) == 0) {
                if ((i != lineOfSets.getLine().size() - 1) && (Objects.equals(lineOfSets.getLine().get(i), lineOfSets.getLine().get(i + 1)))) {
                    maze.putRightWall(1);
                } else {
                    if (i < lineOfSets.getLine().size() - 1) {
                        maze.putRightWall(0);
                        Integer rightCell = lineOfSets.getLine().get(i+1);
                        lineOfSets.getLine().set(i+1, lineOfSets.getLine().get(i));
                        setCollectionList.get(lineOfSets.getLine().get(i)).setNumberOfCellsInSet(setCollectionList.get(lineOfSets.getLine().get(i)).getNumberOfCellsInSet() + 1);
                        setCollectionList.get(rightCell).setNumberOfCellsInSet(setCollectionList.get(rightCell).getNumberOfCellsInSet() - 1);
                        for (int j = i + 1; j < lineOfSets.getLine().size(); j++) {
                            if (lineOfSets.getLine().get(j).equals(rightCell)) {
                                lineOfSets.getLine().set(j, lineOfSets.getLine().get(i));
                                setCollectionList.get(lineOfSets.getLine().get(i)).setNumberOfCellsInSet(setCollectionList.get(lineOfSets.getLine().get(i)).getNumberOfCellsInSet() + 1);
                                setCollectionList.get(rightCell).setNumberOfCellsInSet(setCollectionList.get(rightCell).getNumberOfCellsInSet() - 1);
                            }
                        }
                    }else {
                        maze.putRightWall(1);
                    }
                }
            } else {
                maze.putRightWall(1);
            }
        }
    }

    private void placeWallOnTheBottom(LineOfSets lineOfSets) {
        for (int i = 0; i < lineOfSets.getLine().size(); i++) {
            if (decisionArray.get(indexDecisionArray++) == 1) {
                if(setCollectionList.get(lineOfSets.getLine().get(i)).getNumberOfCellsInSet() - setCollectionList.get(lineOfSets.getLine().get(i)).getNumberOfBottomWallsInSet() > 1){
                    maze.putBottomWall(1);
                    setCollectionList.get(lineOfSets.getLine().get(i)).setNumberOfBottomWallsInSet(setCollectionList.get(lineOfSets.getLine().get(i)).getNumberOfBottomWallsInSet() + 1);
                    lineOfSets.getIndexSetWithBottomWall().add(i);
                }else{
                    maze.putBottomWall(0);
                }
            } else {
                maze.putBottomWall(0);
            }
        }
    }

    private void assignSetsToArrayCells(LineOfSets lineOfSets) {
        for (int i = 0; i < maze.getNumberOfCols(); i++) {
            if (lineOfSets.getLine().get(i) == 0) {
                lineOfSets.getLine().set(i, setFillCounter++);
            }
        }
        incrementCellCount(lineOfSets);
    }

    private void incrementCellCount(LineOfSets lineOfSets) {
        for (Integer set : lineOfSets.getLine()){
            setCollectionList.get(set).setNumberOfCellsInSet(setCollectionList.get(set).getNumberOfCellsInSet() + 1);
        }
    }
}
