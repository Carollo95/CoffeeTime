package com.github.carollo95;

import java.awt.*;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Main {

    private static final long MIN_IDLE_TIME = 60000L; // 1m
    private static final long MAX_IDLE_TIME = 120000L;  //2m
    private static final Random RANDOM = new Random();

    public static void main(String[] args) throws AWTException, InterruptedException {
        while (true) {
            waitSomeTime();
            jiggleMouse();
        }
    }

    private static void waitSomeTime() throws InterruptedException {
        final long waitTime = MIN_IDLE_TIME + RANDOM.longs(0, MAX_IDLE_TIME).findFirst().orElse(0L);
        Thread.sleep(waitTime);
    }

    private static void jiggleMouse() throws AWTException {
        final Point currentPosition = getCurrentMousePosition();

        final Point xMov = getNewMousePosition(currentPosition);

        final Robot robot = new Robot();
        robot.mouseMove(xMov.x, xMov.y);
    }

    private static Point getCurrentMousePosition() {
        return MouseInfo.getPointerInfo() != null ?
                MouseInfo.getPointerInfo().getLocation() :
                new Point(0, 0);
    }

    private static Point getNewMousePosition(Point currentMousePosition) {
        int xMov = RANDOM.nextBoolean() ? 1 : -1;
        xMov = clampBetween(xMov, 0, getScreenWidth());
        return new Point(currentMousePosition.x + xMov, currentMousePosition.y);
    }

    private static int clampBetween(int value, int min, int max) {
        return min(max, max(min, value));
    }

    private static int getScreenWidth() {
        if (Toolkit.getDefaultToolkit() != null && Toolkit.getDefaultToolkit().getScreenSize() != null) {
            return Toolkit.getDefaultToolkit().getScreenSize().width;
        }
        return 0;
    }

}
