package com.example.game.gamecomponents.mazecreator;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * mazeGenerator code was inspired by https://www.youtube.com/watch?v=kiG1BUa34lc.
 * Uses recursive backtracking method to generate random mazes.
 */

public class MazeGenerator {


    private Cell[][] cells;
    private static final int COLS = 5, ROWS = 6;
    private Rect finishLine;
    private boolean firstDraw = true;
    private final ArrayList<Rect> walls;
    private final Random random;

    public MazeGenerator() {
        cells = new Cell[COLS][ROWS];
        walls = new ArrayList<>();
        random = new Random();
    }

    //Empties the array containing the maze walls to make room for the walls of a new maze.
    public void resetGenerator() {
        this.walls.clear();
    }

    public ArrayList<Rect> getWalls() {
        return this.walls;
    }

    public Rect getFinishLine() {
        return this.finishLine;
    }

    /**
     * Calculates the size of each cell of the maze and generates the walls of the maze in an array.
     *
     * @param canvas the graphics context which in which to draw the maze. Used for getting the
     *               size of the display.
     */
    public void setupMaze(Canvas canvas) {
        int width = canvas.getWidth();
        int cellSize = width / (COLS + 1);
        if (firstDraw) {
            mazeGenerator();
            firstDraw = false;
        }
        createMazeCells(cellSize);
    }

    //firstDraw is used so that the maze generator only creates a new maze once for each level.
    public void resetGeneratorFirstDraw() {
        this.firstDraw = true;
    }

    /**
     * Checks each cell of the maze and creates walls (represented as Rectangles) on each side of the
     * square cell according to whether topWall, botWall, leftWall, and rightWall are true or false.
     * Rectangles are used instead of lines because they have an intersect method, which makes
     * collision detection much simpler.
     *
     * @param cellSize the size of each cell of the maze, which is used to get the size of the walls.
     */
    private void createMazeCells(int cellSize) {
        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                if (x == COLS - 1 && y == ROWS - 1) {
                    finishLine = new Rect(x * cellSize + 140, y * cellSize + 520, (x + 1) * cellSize + 140, (y + 1) * cellSize + 520);
                }
                if (cells[x][y].topWall) {
                    Rect r = new Rect(x * cellSize + 140, y * cellSize + 520, (x + 1) * cellSize + 140, y * cellSize + 520 + 9);
                    walls.add(r);
                }
                if (cells[x][y].botWall) {
                    Rect r = new Rect(x * cellSize + 140, (y + 1) * cellSize + 520, (x + 1) * cellSize + 140, (y + 1) * cellSize + 520 + 9);
                    walls.add(r);
                }
                if (cells[x][y].rightWall) {
                    Rect r = new Rect((x + 1) * cellSize + 140, y * cellSize + 520, (x + 1) * cellSize + 140 + 9, (y + 1) * cellSize + 520);
                    walls.add(r);
                }
                if (cells[x][y].leftWall) {
                    Rect r = new Rect(x * cellSize + 140, y * cellSize + 520, x * cellSize + 140 + 9, (y + 1) * cellSize + 520);
                    walls.add(r);
                }

            }
        }
    }

    /*
     * Starts at the cell at the top left corner, finds a random neighbouring cell, if there exists
     * one. In this case, a neighbouring cell represents a cell that shares a wall with the current
     * cell. A path is created using these two walls, and the process is repeated with the selected
     * neighbour cell. This process goes on until there are no neighbours left.
     *
     * For a clearer explanation, here is a video explaining the algorithm:
     * https://youtu.be/elMXlO28Q1U?t=52
     *
     */
    private void mazeGenerator() {
        Stack<Cell> stack = new Stack<>();
        Cell current, next;

        cells = new Cell[COLS][ROWS];

        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }

        current = cells[0][0];
        current.visited = true;
        do {
            next = getNeighbour(current);
            if (next != null) {
                removeWall(current, next);
                stack.push(current);
                current = next;
                current.visited = true;
            } else {
                current = stack.pop();
            }
        } while (!stack.isEmpty());
    }

    /**
     * Chooses a random neighboring cell of the current cell, if there exists one.
     *
     * @param cell the current cell
     * @return return a random neighbour of the cell that hasn't been visited by the maze generator
     * yet.
     */
    private Cell getNeighbour(Cell cell) {

        ArrayList<Cell> neighbours = new ArrayList<>();

        //left neighbour
        if (cell.col > 0) {
            if (!cells[cell.col - 1][cell.row].visited) {
                neighbours.add(cells[cell.col - 1][cell.row]);
            }
        }
        //right neighbour
        if (cell.col < COLS - 1) {
            if (!cells[cell.col + 1][cell.row].visited) {
                neighbours.add(cells[cell.col + 1][cell.row]);
            }
        }

        //top neighbour
        if (cell.row > 0) {
            if (!cells[cell.col][cell.row - 1].visited) {
                neighbours.add(cells[cell.col][cell.row - 1]);
            }
        }

        //bottom neighbour
        if (cell.row < ROWS - 1) {
            if (!cells[cell.col][cell.row + 1].visited) {
                neighbours.add(cells[cell.col][cell.row + 1]);
            }
        }
        if (neighbours.size() > 0) {
            int index = random.nextInt(neighbours.size());
            System.out.println(index);
            return neighbours.get(index);
        }
        return null;
    }

    /**
     * Checks the position of the next wall in relation to the current wall (above, below, left,
     * or right), and removes the wall in between the two cells. This is used to create a path
     * from the start to the finish line.
     *
     * @param current the current cell of the maze
     * @param next    the next cell of the maze, which is a neighbour of the current cell.
     */
    private void removeWall(Cell current, Cell next) {
        if (current.col == next.col && current.row == next.row + 1) {
            current.topWall = false;
            next.botWall = false;
        }
        if (current.col == next.col && current.row == next.row - 1) {
            current.botWall = false;
            next.topWall = false;
        }
        if (current.col == next.col + 1 && current.row == next.row) {
            current.leftWall = false;
            next.rightWall = false;
        }
        if (current.col == next.col - 1 && current.row == next.row) {
            current.rightWall = false;
            next.leftWall = false;
        }
    }

    /*
     * The Cell class is used for each cell of the maze. The wall boolean values are used by the
     * maze generator to determine which walls should be drawn.
     */
    private class Cell {
        boolean topWall = true, leftWall = true, botWall = true, rightWall = true, visited = false;
        final int col, row;


        Cell(int col, int row) {
            this.col = col;
            this.row = row;
        }
    }
}
