package com.mygdx.game.desktop;

import org.lwjgl.util.Dimension;

public class SizeCalculation {
    private double pixelsPerCell;
    private org.lwjgl.util.Dimension windowSize;

    public SizeCalculation(double pixelsPerCell, double width, double height) {
        this.pixelsPerCell = pixelsPerCell;
        windowSize = new Dimension(
                (int) width,
                (int) height
        );
    }

    public org.lwjgl.util.Dimension getWindowSize() {
        return windowSize;
    }

    public double getPixelsPerCell() {
        return pixelsPerCell;
    }
}
