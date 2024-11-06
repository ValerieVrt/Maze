package edu.school21.maze.waveAlgoritm;


import edu.school21.maze.model.Maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WaveAlgorithm {
    private final Maze maze;
    private int step;
    private List<List<Integer>> mazeSolution;

    public WaveAlgorithm(Maze maze) {
        this.maze = maze;
        this.step = 0;
        this.mazeSolution = initializeSolutionArray();
    }

    private static final List<Point> clockwiseWalk = new ArrayList<>(Arrays.asList(
            new Point(-1, 0),
            new Point(0, 1),
            new Point(1, 0),
            new Point(0, -1)
    ));

    public static List<Point> getClockwiseWalk() {
        return clockwiseWalk;
    }

    public void findPath(int startX, int startY, int finishX, int finishY) {
        startWave(startX, startY, finishX, finishY);
        getPath(startX, startY, finishX, finishY);
        for (var value: mazeSolution) {
            System.out.println(value);
        }
    }

    public void startWave(int startX, int startY, int finishX, int finishY) {
        List<Point> wave = new ArrayList<>();
        List<Point> oldWave = new ArrayList<>();
        oldWave.add(new Point(startX, startY));
        while (!oldWave.isEmpty()) {
            step++;
            wave.clear();
            for (Point cell : oldWave) {
                rightStepCheck(cell, wave, finishX, finishY);
                leftStepCheck(cell, wave, finishX, finishY);
                upStepCheck(cell, wave, finishX, finishY);
                downStepCheck(cell, wave, finishX, finishY);
//                    int newX = cell.getX() + clockwiseWalk.get(i).getX();
//                    int newY = cell.getY() + clockwiseWalk.get(i).getY();
//                    if ((newX < maze.getNumberOfCols()) &&
//                            (newY < maze.getNumberOfRows()) &&
//                            (newX >= 0) &&
//                            (newY >= 0) &&
//                            ((copyGameField.get(newX).get(newY) == emptyChar || copyGameField.get(newX).get(newY) == playerChar))) {
//                        copyGameField.get(newX).set(newY, step);
//                        wave.add(new Point(newX, newY));
//                    }
            }
            oldWave = new ArrayList<>(wave);
        }
    }

    private void downStepCheck(Point cell, List<Point> wave, int finishX, int finishY) {
        int newX = cell.getX() + clockwiseWalk.get(1).getX();
        int newY = cell.getY() + clockwiseWalk.get(1).getY();
        if (boundaryCheck(newX, newY) && (checkingForHorizontalWall(cell.getY(), cell.getX()) || gotToTheEnd(newX, newY, finishX, finishY))) {
            mazeSolution.get(newY).set(newX, step);
            wave.add(new Point(newX, newY));
        }
    }


    private void upStepCheck(Point cell, List<Point> wave, int finishX, int finishY) {
        int newX = cell.getX() + clockwiseWalk.get(3).getX();
        int newY = cell.getY() + clockwiseWalk.get(3).getY();
        if (boundaryCheck(newX, newY) && (checkingForHorizontalWall(newY, newX) || gotToTheEnd(newX, newY, finishX, finishY))) {
            mazeSolution.get(newY).set(newX, step);
            wave.add(new Point(newX, newY));
        }
    }

    private void leftStepCheck(Point cell, List<Point> wave, int finishX, int finishY) {
        int newX = cell.getX() + clockwiseWalk.get(0).getX();
        int newY = cell.getY() + clockwiseWalk.get(0).getY();
        if (boundaryCheck(newX, newY) && checkingForVerticalWall(newY, newX) || gotToTheEnd(newX, newY, finishX, finishY)) {
            mazeSolution.get(newY).set(newX, step);
            wave.add(new Point(newX, newY));
        }
    }


    private void rightStepCheck(Point cell, List<Point> wave, int finishX, int finishY) {
        int newX = cell.getX() + clockwiseWalk.get(2).getX();
        int newY = cell.getY() + clockwiseWalk.get(2).getY();
        if (boundaryCheck(newX, newY) && checkingForVerticalWall(cell.getY(), cell.getX()) || gotToTheEnd(newX, newY, finishX, finishY)) {
            mazeSolution.get(newY).set(newX, step);
            wave.add(new Point(newX, newY));
        }
    }

    private boolean gotToTheEnd(int newX, int newY, int finishX, int finishY) {
        return (newX == finishX) && (newY == finishY);
    }

    private boolean checkingForHorizontalWall(int y, int x) {
        return maze.getHorizontalWall().get(y * maze.getNumberOfCols() + x) == 0;
    }

    private boolean checkingForVerticalWall(int y, int x) {
        return maze.getVerticalWall().get(y * maze.getNumberOfCols() + x) == 0;
    }

    private boolean boundaryCheck(int newX, int newY) {
        return (newX < maze.getNumberOfCols()) && (newY < maze.getNumberOfRows()) && (newX >= 0) && (newY >= 0);
    }

    public void getPath(int startX, int startY, int finishX, int finishY) {
        Point currentWave = new Point(finishX, finishY);
        Point goal = new Point(startX, startY);
        while (!currentWave.equals(goal)) {
            for (int i = 0, flag = 0; i < 4 && flag == 0; i++) {
                int newX = currentWave.getX() + clockwiseWalk.get(i).getX();
                int newY = currentWave.getY() + clockwiseWalk.get(i).getY();
                if (boundaryCheck(newX, newY) && (mazeSolution.get(currentWave.getY()).get(currentWave.getX()) - 1 == mazeSolution.get(newY).get(newX))) {
                    mazeSolution.get(currentWave.getY()).set(currentWave.getX(), 0);
                    currentWave.setX(newX);
                    currentWave.setY(newY);
                    flag = 1;
                }
            }
        }
    }

    private List<List<Integer>> initializeSolutionArray() {
        List<List<Integer>> mazeSolution = new ArrayList<>();
        for (int i = 0; i < maze.getNumberOfRows(); i++) {
            mazeSolution.add(new ArrayList<>());
            for (int j = 0; j < maze.getNumberOfCols(); j++) {
                mazeSolution.get(i).add(0);
            }
        }
        return mazeSolution;
    }

}
