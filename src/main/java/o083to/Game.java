package o083to;

import o083to.controller.game.MouseClickController;
import o083to.controller.game.PauseGameController;
import o083to.controller.game.StartGameController;
import o083to.controller.game.StopGameController;
import o083to.model.Cell;
import o083to.model.Direction;
import o083to.model.frog.Frog;
import o083to.model.frog.GreenFrog;
import o083to.model.snake.Snake;
import o083to.view.GUIGameView;
import o083to.view.GameView;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {

    private static final int N = 10;
    private static final int M = 10;
    private static final int LENGTH = 6;
    private static final int SNAKE_DELAY = 1000;
    private static final int DELAY = 150;
    private static final int FROG_DELAY_MULTIPLIER = 2;
    private static final int FROGS_COUNT = 8;

    private int score;
    private GameView view;
    private final Board board;
    private final Snake snake;
    private final List<Frog> frogs;
    private boolean isStarted;
    private ExecutorService executor;

    public static void main(String[] args) {
        Game game = new Game();
        game.setView();
        game.setControllers();
    }

    Game() {
        board = new Board(N, M);
        snake = new Snake(this, LENGTH, SNAKE_DELAY);
        board.markCellsAsBusy(snake.getBody());
        frogs = createFrogs(FROGS_COUNT, SNAKE_DELAY * FROG_DELAY_MULTIPLIER);
    }

    public Snake getSnake() {
        return snake;
    }

    public List<Frog> getFrogs() {
        return frogs;
    }

    public Board getBoard() {
        return board;
    }

    public void startPlayers() {
        if (isStarted) {
            resume();
        } else {
            start();
        }
    }

    private void resume() {
        snake.start();
        for (Frog frog : frogs) {
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frog.start();
        }
    }

    private void start() {
        executor = Executors.newFixedThreadPool(FROGS_COUNT + 1);
        executor.submit(snake);
        for (Frog frog : frogs) {
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executor.submit(frog);
        }
        isStarted = true;
    }

    public void stopPlayers() {
        snake.stay();
        for (Frog frog : frogs) {
            frog.stay();
        }
    }

    public void stopGame() {
        view.gameOver();
        snake.die();
        for (Frog frog : frogs) {
            frog.die();
        }
    }

    public void turnSnake(Direction direction) {
        snake.turn(direction);
    }

    public void onSnakeCaughtFrog(Frog frog) {
        frogs.remove(frog);
        // todo: создать новую дичь
    }

    public void checkMove(Cell cell) {
        for (Frog frog : frogs) {
            if (cell.equals(frog.getPosition())) {
                if (frog.isPoisonous()) {
                    stopGame();
                } else {
                    if (frog.getLengthFactor() > 0) {
                        snake.grow();
                    } else {
                        snake.reduce();
                    }
                    score += frog.getScoreFactor();
                    view.updateScore(score);
                }
                frog.catchFrog();
                break;
            }
        }
    }

    private List<Frog> createFrogs(int count, int delay) {
        List<Frog> result = new CopyOnWriteArrayList<Frog>();
        for (int i = 0; i < count; i++) {
            //todo: дичь должна быть разных видов
            Frog frog = new GreenFrog(this, board.getFreeCell(), delay);
            result.add(frog);
        }
        return result;
    }

    private void setView() {
        view = new GUIGameView(N, M);
        view.setGame(this);
        snake.addListener(view);
        for (Frog frog : frogs) {
            frog.addListener(view);
        }
    }

    private void setControllers() {
        view.addMouseClickController(new MouseClickController(this));
        view.addStartGameController(new StartGameController(this));
        view.addPauseGameController(new PauseGameController(this));
        view.addStopGameController(new StopGameController(this));
    }

    public class Board {

        private final Set<Cell> busyCells = new HashSet<Cell>();

        private final int maxX;
        private final int maxY;
        private final Random random = new Random();

        Board(int width, int height) {
            maxX = width - 1;
            maxY = height - 1;
        }

        public void releaseCell(Cell cell) {
            busyCells.remove(cell);
        }

        public synchronized void markCellAsBusy(Cell cell) {
            busyCells.add(cell);
        }

        public synchronized void markCellsAsBusy(List<Cell> cells) {
            for (Cell cell : cells) {
                busyCells.add(cell);
            }
        }

        public synchronized Cell getNextCellForFrog(Cell oldCell) {
            Direction direction = Direction.fromInteger(random.nextInt(4));
            for (int i = 0; i < 4; i++) {
                Cell newCell = oldCell;
                // todo: ну что это???
                switch (direction) {
                    case UP:
                        newCell = upCell(oldCell);
                        break;
                    case DOWN:
                        newCell = downCell(oldCell);
                        break;
                    case LEFT:
                        newCell = leftCell(oldCell);
                        break;
                    case RIGHT:
                        newCell = rightCell(oldCell);
                }
                if (busyCells.contains(newCell)) {
                    direction = direction.turnLeft();
                } else {
                    busyCells.remove(oldCell);
                    busyCells.add(newCell);
                    return newCell;
                }
            }
            return oldCell;
        }

        public synchronized Cell getFreeCell() {
            Cell cell;
            do {
                int x = random.nextInt(maxX + 1);
                int y = random.nextInt(maxY + 1);
                cell = Cell.valueOf(x, y);
            } while (busyCells.contains(cell));
            busyCells.add(cell);
            return cell;
        }

        public synchronized Cell snakeGoUp(Cell cell) {
            Cell result = upCell(cell);
            busyCells.add(result);
            checkMove(result);
            return result;
        }

        public synchronized Cell snakeGoDown(Cell cell) {
            Cell result = downCell(cell);
            busyCells.add(result);
            checkMove(result);
            return result;
        }

        public synchronized Cell snakeGoLeft(Cell cell) {
            Cell result = leftCell(cell);
            busyCells.add(result);
            checkMove(result);
            return result;
        }

        public synchronized Cell snakeGoRight(Cell cell) {
            Cell result = rightCell(cell);
            busyCells.add(result);
            checkMove(result);
            return result;
        }

        private Cell upCell(Cell cell) {
            int oldY = cell.getY();
            return Cell.valueOf(cell.getX(), oldY == 0 ? maxY : oldY - 1);
        }

        private Cell downCell(Cell cell) {
            int oldY = cell.getY();
            return Cell.valueOf(cell.getX(), oldY == maxY ? 0 : oldY + 1);
        }

        private Cell leftCell(Cell cell) {
            int oldX = cell.getX();
            return Cell.valueOf(oldX == 0 ? maxX : oldX - 1, cell.getY());
        }

        private Cell rightCell(Cell cell) {
            int oldX = cell.getX();
            return Cell.valueOf(oldX == maxX ? 0 : oldX + 1, cell.getY());
        }
    }
}
