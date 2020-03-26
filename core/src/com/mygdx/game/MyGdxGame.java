package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	ShapeRenderer shapeRenderer;

	int robotX;
	int robotY;
	int robotWidth;

	Map<Float, Float> sonarReadings;

	Map<Float, Float> lidarReadings;

	static final float kStopThreshold = 2.0f;
	static final float kWarningThreshold = 4.0f;

	int maxReading;

	long timeLastUpdate;

	public MyGdxGame(int pixelsPerCell) {

		robotWidth = pixelsPerCell;
		maxReading = Math.min(Constants.NUM_CELLS/2, 12);

		int numReadings = 8;

		float degreesPerReading = 360.0f/numReadings;

		sonarReadings = new HashMap<>();

		for(int i = 0; i < numReadings; i++) {
			float currentDegree = i * degreesPerReading;
			float currentReading = maxReading;

			sonarReadings.put(currentDegree, currentReading);
		}

		lidarReadings = new HashMap<>();

		int numLidarReadings = 360;
		for(int i = 0; i < numLidarReadings; i++) {
			lidarReadings.put((float) i, (float) maxReading);
		}

		timeLastUpdate = TimeUtils.millis();
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		shapeRenderer = new ShapeRenderer();

		robotX = maxReading*robotWidth;
		robotY = maxReading*robotWidth;
	}

	@Override
	public void render () {
		draw();

		long currentTime = TimeUtils.millis();

		if(currentTime > timeLastUpdate + 1000) {
			timeLastUpdate = currentTime;

			// randomize sonar readings
			randomizeSonar();
			randomizeLidar();
		}
	}

	private void randomizeSonar() {
		for(Map.Entry<Float, Float> entry : sonarReadings.entrySet()) {

			double reading = Math.random()*maxReading;

			entry.setValue((float) reading);
		}
	}

	private void randomizeLidar() {
		for(Map.Entry<Float, Float> entry : lidarReadings.entrySet()) {
			double reading = Math.random() * maxReading;
			entry.setValue((float) reading);
		}
	}

	private void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



		drawReadings();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(0,0,1,1);
		shapeRenderer.circle(robotX, robotY, robotWidth/2,8);
		shapeRenderer.end();
	}

	private void drawReadings() {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

		for(Map.Entry<Float, Float> entry : sonarReadings.entrySet()) {
			float currentAngle = entry.getKey();
			float currentReading = entry.getValue();

			setColorForReading(currentReading);
			drawReading(currentAngle, currentReading);
		}

		for(Map.Entry<Float, Float> entry : lidarReadings.entrySet()) {
			float currentAngle = entry.getKey();
			float currentReading = entry.getValue();

			setColorForReading(currentReading);
			drawReading(currentAngle, currentReading);
		}

		shapeRenderer.end();
	}

	private void drawReading(float currentAngle, float currentReading) {
		double originalEndpointX = Math.cos(Math.toRadians(currentAngle)) * currentReading;
		double originalEndpointY = Math.sin(Math.toRadians(currentAngle)) * currentReading;

		double renderEndpointX = originalEndpointX * robotWidth + robotX;
		double renderEndpointY = originalEndpointY * robotWidth + robotY;

		shapeRenderer.line(robotX, robotY, (float) renderEndpointX, (float) renderEndpointY);
	}

	private void setColorForReading(float reading) {
		if(reading < kStopThreshold) {
			shapeRenderer.setColor(1, 0, 0, 1);
		} else if (reading < kWarningThreshold) {
			shapeRenderer.setColor(1,1,0,1);
		} else {
			shapeRenderer.setColor(0,1,0,1);
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
