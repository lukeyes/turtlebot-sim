package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Constants;
import com.mygdx.game.MyGdxGame;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		SizeCalculation sizeCalculation = GetScreenSize();
		org.lwjgl.util.Dimension screenSize = sizeCalculation.getWindowSize();
		config.width = screenSize.getWidth();
		config.height = screenSize.getHeight();

		new LwjglApplication(new MyGdxGame((int)sizeCalculation.getPixelsPerCell()), config);
	}

	private static SizeCalculation GetScreenSize() {
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

		double screenHeight = dimension.getHeight();
		double screenWidth = dimension.getWidth();

		double candidateScreenWidth = screenWidth*0.9;
		double candidateScreenHeight = screenHeight*0.9;

		double widthForMapsCombined = candidateScreenWidth-8;
		double widthForMaps = widthForMapsCombined/2;

		double pixelPerCellWidth = widthForMaps/ Constants.NUM_CELLS;
		double pixelPerCellHeight = candidateScreenHeight / Constants.NUM_CELLS;

		double pixelPerCell = Math.min(pixelPerCellHeight, pixelPerCellWidth);

		// round down to nearest multiple of 2
		double pixelDivisibleBy2 = Math.floor(pixelPerCell/2);

		pixelPerCell = pixelDivisibleBy2 * 2;

		widthForMaps = pixelPerCell * Constants.NUM_CELLS;
		widthForMapsCombined = widthForMaps*2;

		candidateScreenWidth = widthForMapsCombined+8;
		candidateScreenHeight = pixelPerCell * Constants.NUM_CELLS;

		System.out.println(candidateScreenWidth);
		System.out.println(candidateScreenHeight);

		org.lwjgl.util.Dimension newDimensions = new org.lwjgl.util.Dimension();
		newDimensions.setSize(
				(int) candidateScreenWidth,
				(int) candidateScreenHeight);

		SizeCalculation sizeCalculation = new SizeCalculation(
				pixelPerCell,
				candidateScreenWidth,
				candidateScreenHeight
		);

		return sizeCalculation;
	}
}
