package o083to;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserInputValidator {

    private static final int MIN_SIZE = 5;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 20;
    private static final int MIN_SNAKE_LENGTH = 3;
    private static final int MIN_FROGS_COUNT = 1;
    private static final int DEFAULT_FROGS_COUNT = 4;
    private static final int MIN_SNAKE_SPEED = 1;
    private static final int DEFAULT_SNAKE_SPEED = 4;
    private static final int MAX_SNAKE_SPEED = 7;

    private static final Map<Integer, Integer> SPEED_TO_DELAY = new HashMap<Integer, Integer>(7);
    static {
        SPEED_TO_DELAY.put(1, 2000);
        SPEED_TO_DELAY.put(2, 1500);
        SPEED_TO_DELAY.put(3, 1200);
        SPEED_TO_DELAY.put(4, 1000);
        SPEED_TO_DELAY.put(5, 800);
        SPEED_TO_DELAY.put(6, 500);
        SPEED_TO_DELAY.put(7, 300);
    }

    private static final String N_KEY = "-n";
    private static final String M_KEY = "-m";
    private static final String LENGTH_KEY = "-l";
    private static final String FROGS_KEY = "-f";
    private static final String SPEED_KEY = "-s";

    private static final String GAME_WITH_DEFAULT_PARAMS_MSG = "Starting game with default parameters";
    private static final String KEY_N_USAGE_MSG = "Use key -n to set width of board";
    private static final String KEY_M_USAGE_MSG = "Use key -m to set height of board";
    private static final String SIDE_SIZE_LIMITS_MSG = "min = " + MIN_SIZE + ", max = " + MAX_SIZE;
    private static final String KEY_L_USAGE_MSG = "Use key -l to set snake's length.\nmin = " + MIN_SNAKE_LENGTH;
    private static final String KEY_F_USAGE_MSG = "Use key -f to set count of frogs.\nmin = " + MIN_FROGS_COUNT;
    private static final String KEY_S_USAGE_MSG = "Use key -s to set snake's speed.";
    private static final String SPEED_COUNT_LIMITS_MSG = "min = " + MIN_SNAKE_SPEED + ", max = " + MAX_SNAKE_SPEED;
    private static final String DEFAULT_N_MSG = "Starting game with default parameter N: " + DEFAULT_SIZE;
    private static final String DEFAULT_M_MSG = "Starting game with default parameter M: " + DEFAULT_SIZE;
    private static final String DEFAULT_SNAKE_LENGTH_MSG = "Starting game with default snake's length: " + MIN_SNAKE_LENGTH;
    private static final String DEFAULT_FROGS_COUNT_MSG = "Starting game with default count of frogs: " +DEFAULT_FROGS_COUNT;
    private static final String DEFAULT_SPEED_MSG = "Starting game with default snake's speed: " + DEFAULT_SNAKE_SPEED;

    private static final Map<String, String> DEFAULT_PARAM_MSG = new LinkedHashMap<String, String>(5);
    static {
        DEFAULT_PARAM_MSG.put(N_KEY, DEFAULT_N_MSG);
        DEFAULT_PARAM_MSG.put(M_KEY, DEFAULT_M_MSG);
        DEFAULT_PARAM_MSG.put(LENGTH_KEY, DEFAULT_SNAKE_LENGTH_MSG);
        DEFAULT_PARAM_MSG.put(FROGS_KEY, DEFAULT_FROGS_COUNT_MSG);
        DEFAULT_PARAM_MSG.put(SPEED_KEY, DEFAULT_SPEED_MSG);
    }

    private int n = DEFAULT_SIZE;
    private int m = DEFAULT_SIZE;
    private int snakeLength = MIN_SNAKE_LENGTH;
    private int frogsCount = DEFAULT_FROGS_COUNT;
    private int snakeDelay = SPEED_TO_DELAY.get(DEFAULT_SNAKE_SPEED);

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public int getSnakeLength() {
        return snakeLength;
    }

    public int getFrogsCount() {
        return frogsCount;
    }

    public int getSnakeDelay() {
        return snakeDelay;
    }

    public void validate(String[] consoleArgs) {
        if (consoleArgs.length < 2) {
            printHelp();
        } else {
            Map<String, Integer> inputParams = parseInput(consoleArgs);
            for (String key : DEFAULT_PARAM_MSG.keySet()) {
                checkAndSetField(inputParams, key);
            }
        }
    }

    private void checkAndSetField(Map<String, Integer> inputParams, String key) {
        if (inputParams.containsKey(key)) {
            if (N_KEY.equals(key)) {
                checkAndSetN(inputParams.get(key));
            } else if (M_KEY.equals(key)) {
                checkAndSetM(inputParams.get(key));
            } else if (LENGTH_KEY.equals(key)) {
                checkAndSetSnakeLength(inputParams.get(key));
            } else if (FROGS_KEY.equals(key)) {
                checkAndSetFrogsCount(inputParams.get(key));
            } else {
                checkAndSetSpeed(inputParams.get(key));
            }
        } else {
            System.out.println(DEFAULT_PARAM_MSG.get(key));
        }
    }

    private void checkAndSetN(int n) {
        this.n = checkSideSize("N", n);
    }

    private void checkAndSetM(int m) {
        this.m = checkSideSize("M", m);
    }

    private void checkAndSetSnakeLength(int length) {
        if (length >= MIN_SNAKE_LENGTH && length <= m - 1) {
            snakeLength = length;
        } else {
            System.out.println("Invalid snake's length: " + length);
            System.out.println(String.format("Length should be more then %d and less then height of board = %d",
                    MIN_SNAKE_LENGTH - 1, m));
            System.out.println(DEFAULT_FROGS_COUNT_MSG);
        }
    }

    private void checkAndSetFrogsCount(int count) {
        if (count >= MIN_FROGS_COUNT && count <= n * m / 4) {
            frogsCount = count;
        } else {
            System.out.println("Invalid count of frogs: " + count);
            System.out.println(String.format("Count should be more then %d and not more then N * M / 4 = %d",
                    MIN_FROGS_COUNT - 1, n * m / 4));
            System.out.println(DEFAULT_FROGS_COUNT);
        }
    }

    private void checkAndSetSpeed(int speed) {
        if (speed >= MIN_SNAKE_SPEED && speed <= MAX_SNAKE_SPEED) {
            snakeDelay = SPEED_TO_DELAY.get(speed);
        } else {
            System.out.println("Invalid snake's speed: " + speed);
            System.out.println(String.format("Speed should be from %d to %d", MIN_SNAKE_SPEED, MAX_SNAKE_SPEED));
            System.out.println(DEFAULT_SPEED_MSG);
        }
    }

    private static int checkSideSize(String paramName, int value) {
        if (value >= MIN_SIZE && value <= MAX_SIZE) {
            return value;
        } else {
            System.out.println(String.format("Invalid %s value: %d", paramName, value));
            System.out.println(SIDE_SIZE_LIMITS_MSG);
            System.out.println("N".equals(paramName) ? DEFAULT_N_MSG : DEFAULT_M_MSG);
            return DEFAULT_SIZE;
        }
    }

    private static void printHelp() {
        System.out.println(GAME_WITH_DEFAULT_PARAMS_MSG);

        System.out.println(DEFAULT_N_MSG);
        System.out.println(KEY_N_USAGE_MSG);
        System.out.println(SIDE_SIZE_LIMITS_MSG);

        System.out.println(DEFAULT_M_MSG);
        System.out.println(KEY_M_USAGE_MSG);
        System.out.println(SIDE_SIZE_LIMITS_MSG);

        System.out.println(DEFAULT_SNAKE_LENGTH_MSG);
        System.out.println(KEY_L_USAGE_MSG);

        System.out.println(DEFAULT_FROGS_COUNT_MSG);
        System.out.println(KEY_F_USAGE_MSG);

        System.out.println(DEFAULT_SPEED_MSG);
        System.out.println(KEY_S_USAGE_MSG);
        System.out.println(SPEED_COUNT_LIMITS_MSG);
    }

    private Map<String, Integer> parseInput(String[] consoleArgs) {
        Map<String, Integer> inputParams = new HashMap<String, Integer>();
        for (int i = 0; i < consoleArgs.length; i+=2) {
            if (i + 1 != consoleArgs.length) {
                try {
                    String key = consoleArgs[i];
                    int value = Integer.parseInt(consoleArgs[i+1]);
                    inputParams.put(key, value);
                } catch (NumberFormatException e) {
                    // do nothing
                }
            }
        }
        return inputParams;
    }
}
