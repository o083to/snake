package ru.o083to.model;

import java.util.LinkedList;

public class Snake {

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    private int length;
    private int direction = DOWN;

    private Board board;

    private LinkedList<Segment> segments = new LinkedList<Segment>();

    public Snake(int length, Board board) {
        this.board = board;
        this.length = length;
        segments.add(new Segment(0, length - 1, Segment.HEAD));
        for (int i = 1; i < length - 1; i++) {
            segments.add(new Segment(0, i, Segment.BODY));
        }
        segments.add(new Segment(0, 0, Segment.TAIL));
    }

    public void changeDirection(int direction) {
        this.direction = direction;
    }

    public void move() {
        Segment last = segments.removeLast();
        segments.getLast().setType(Segment.TAIL);
        segments.getFirst().setType(Segment.BODY);
        last.setType(Segment.HEAD);
        Segment first = segments.getFirst();
        int firstX = first.getX();
        int firstY = first.getY();
        switch (direction) {
            case UP:
                last.setY(firstY == 0 ? board.getHeight() - 1 : firstY - 1);
                break;
            case DOWN:
                last.setY(firstY == board.getHeight() - 1 ? 0 : firstY + 1);
                break;
            case LEFT:
                last.setX(firstX == 0 ? board.getWidth() - 1 : firstX - 1);
                break;
            case RIGHT:
                last.setX(firstX == board.getWidth() - 1 ? 0 : firstX + 1);
                break;
        }
        segments.addFirst(last);
    }

    public static class Segment {
        public static final int HEAD = 0;
        public static final int BODY = 1;
        public static final int TAIL = 2;

        private int x;
        private int y;
        private int type;

        public Segment(int x, int y, int type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
