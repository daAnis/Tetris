package ru.ssau.fiit.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import sun.nio.cs.ext.GB18030;

import static ru.ssau.fiit.tetris.Constants.CELL_SIZE;
import static ru.ssau.fiit.tetris.Constants.STAGE_HEIGHT;
import static ru.ssau.fiit.tetris.Constants.STAGE_WIDTH;
import static ru.ssau.fiit.tetris.GameStage.NUM_COLUMNS;
import static ru.ssau.fiit.tetris.GameStage.NUM_ROWS;

public class Tetris extends ApplicationAdapter {
	private static final int STAGE_START_X = 25;
	private static final int STAGE_START_Y = 90;
	private static final int NEXT_TETROIMINO_SIZE = 80;
	private static final int MIN_HORIZONTAL_MOVE_INTERVAL_MILLIS = 100;
	private static final int MIN_FALL_INTERVAL_MILLIS = 50;
	private static final int MIN_ROTATE_INTERVAL_MILLIS = 150;
	private static final int[] SCORES = new int[] {0, 10, 30, 80, 150};

	private boolean isGameGoing = true;
	private long lastRotateMillis;
	private long lastHorizontalMoveMillis;
	private long lastFallMillis;
	private float fallingSpeed;
	private Tetromino currentTetromino;
	private Tetromino nextTetromino;
	private Stage stage;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer renderer;
	private BitmapFont gameoverFont;
	private BitmapFont scoreFont;
	private GameStage gameStage;
	private int score;
	private SoftKey lastPressedSoftKey;
	private String string;

	private IActivityRequestHandler myRequestHandler;

	public Tetris (IActivityRequestHandler handler) {
		myRequestHandler = handler;
	}

	public static void renderBlock(ShapeRenderer renderer, int column, int row) {
		renderer.rect(STAGE_START_X + column * CELL_SIZE, STAGE_START_Y + row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
	}

	@Override
	public void create() {
		stage = new Stage(new FitViewport(Constants.STAGE_WIDTH, Constants.STAGE_HEIGHT));
		Gdx.input.setInputProcessor(stage);
		camera = new OrthographicCamera();
		//устанавливаем размер игрового поля
		camera.setToOrtho(false, Constants.STAGE_WIDTH, Constants.STAGE_HEIGHT);
		batch = new SpriteBatch();
		gameoverFont = new BitmapFont();
		gameoverFont.setColor(Color.BLACK);
		scoreFont = new BitmapFont();
		scoreFont.setColor(Color.BLACK);
		renderer = new ShapeRenderer();
		fallingSpeed = 4.5f; // блоков в секунду
		gameStage = new GameStage();
		currentTetromino = Tetromino.getInstance();
		nextTetromino = Tetromino.getInstance();

		gameStage.setPosition(STAGE_START_X, STAGE_START_Y);
		stage.addActor(gameStage);
		Group controlGroup = new Group();
		controlGroup.setPosition(80, 5);
		Texture leftArrowTexture = new Texture(Gdx.files.internal("arrow-pointing-left.png"));
		Image leftArrow = new Image(leftArrowTexture);
		Texture rightArrowTexture = new Texture(Gdx.files.internal("right-chevron.png"));
		Image rightArrow = new Image(rightArrowTexture);
		Texture circleTexture = new Texture(Gdx.files.internal("circular-refreshment-arrow.png"));
		Image circle = new Image(circleTexture);
		Texture downArrowTexture = new Texture(Gdx.files.internal("down-arrow.png"));
		Image downArrow = new Image(downArrowTexture);
		Texture pauseTexture = new Texture(Gdx.files.internal("pause.png"));
		Image pause = new Image(pauseTexture);
		Texture stopTexture = new Texture(Gdx.files.internal("stop.png"));
		Image stop = new Image(stopTexture);
		registerSoftKeyPressEvent(leftArrow, SoftKey.LEFT);
		registerSoftKeyPressEvent(rightArrow, SoftKey.RIGHT);
		registerSoftKeyPressEvent(circle, SoftKey.ROTATE);
		registerSoftKeyPressEvent(downArrow, SoftKey.DOWN);
		registerSoftKeyPressEvent(pause, SoftKey.PAUSE);
		registerSoftKeyPressEvent(stop, SoftKey.STOP);
		leftArrow.scaleBy(1.1f);
		rightArrow.scaleBy(1.1f);
		circle.scaleBy(1.1f);
		downArrow.scaleBy(1.1f);
		pause.scaleBy(1.1f);
		stop.scaleBy(1.1f);
		leftArrow.setPosition(0, 0);
		downArrow.setPosition(90, 0);
		rightArrow.setPosition(180, 0);
		circle.setPosition(270, 0);
		pause.setPosition(305, 270);
		stop.setPosition(305, 180);
		controlGroup.addActor(rightArrow);
		controlGroup.addActor(leftArrow);
		controlGroup.addActor(circle);
		controlGroup.addActor(downArrow);
		controlGroup.addActor(pause);
		controlGroup.addActor(stop);
		stage.addActor(controlGroup);
	}

	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void render() {
		float r = 255f / 255f, g = 237f / 255f, b = 247f /  255f;
		Gdx.gl.glClearColor(r,g,b,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (!isGameGoing) {
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			gameoverFont.draw(batch, string,
					(STAGE_WIDTH - (string.length() / 2 * gameoverFont.getLineHeight())) / 2,
					STAGE_HEIGHT / 2 - gameoverFont.getLineHeight() / 2);
			batch.end();
			if (Gdx.input.isTouched()) {
				if (string.compareTo("Pause") == 0)
					isGameGoing = true;
				else if (string.compareTo("You lose") == 0)
					myRequestHandler.closeGame(score);
				else if (string.compareTo("Game stopped") == 0)
					myRequestHandler.closeGame(score);
			}
			return;
		}

		if (!isGameGoing) {
			myRequestHandler.closeGame(score);
			return;
		}

		if (TimeUtils.millis() - lastFallMillis > (1 / fallingSpeed) * 1000) {
			lastFallMillis = TimeUtils.millis();
			currentTetromino.fall();
		} else if (lastPressedSoftKey == SoftKey.ROTATE && TimeUtils.millis() - lastRotateMillis > MIN_ROTATE_INTERVAL_MILLIS) {
			currentTetromino.rotate(gameStage);
			lastRotateMillis = TimeUtils.millis();
		}

		if (gameStage.isOnGround(currentTetromino.getBlocks())) {
			int numDeletedRows = gameStage.setBlocks(currentTetromino.getBlocks());
			score += SCORES[numDeletedRows];
			currentTetromino = nextTetromino;
			currentTetromino.initPosition();
			nextTetromino = Tetromino.getInstance();
			if (gameStage.isOnGround(currentTetromino.getBlocks())) {
				isGameGoing = false;
				string = "You lose";
				return;
			}
		} else if (lastPressedSoftKey == SoftKey.LEFT && TimeUtils.millis() - lastHorizontalMoveMillis > MIN_HORIZONTAL_MOVE_INTERVAL_MILLIS) {
			currentTetromino.moveToLeft(gameStage);
			lastHorizontalMoveMillis = TimeUtils.millis();
		} else if (lastPressedSoftKey == SoftKey.RIGHT && TimeUtils.millis() - lastHorizontalMoveMillis > MIN_HORIZONTAL_MOVE_INTERVAL_MILLIS) {
			currentTetromino.moveToRight(gameStage);
			lastHorizontalMoveMillis = TimeUtils.millis();
		} else if (lastPressedSoftKey == SoftKey.DOWN && TimeUtils.millis() - lastFallMillis > MIN_FALL_INTERVAL_MILLIS) {
			lastFallMillis = TimeUtils.millis();
			currentTetromino.fall();
		} else if (lastPressedSoftKey == SoftKey.PAUSE) {
			isGameGoing = false;
			string = "Pause";
			return;
		} else if (lastPressedSoftKey == SoftKey.STOP) {
			isGameGoing = false;
			string = "Game stopped";
			return;
		}

		//пересчет матрицы проекции экрана
		camera.update();

		renderStage();
	}

	//закрытие игры
	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
		renderer.dispose();
		gameoverFont.dispose();
		scoreFont.dispose();
	}

	//отвечает за отрисовку экрана игры в данный момент
	private void renderStage() {
		//обновляет сцену//время между последним и текущим кадром в секундах
		stage.act(Gdx.graphics.getDeltaTime());
		//отрисовывает сцену
		stage.draw();

		renderer.setProjectionMatrix(camera.combined);
		renderer.begin(ShapeRenderer.ShapeType.Line);
		//отображение следующей фигуры
		int nextTetriminoBoxX = CELL_SIZE * NUM_COLUMNS + 2 * STAGE_START_X;
		int nextTetriminoBoxY = STAGE_START_Y + CELL_SIZE * NUM_ROWS - NEXT_TETROIMINO_SIZE;
		renderer.rect(nextTetriminoBoxX - 1, nextTetriminoBoxY - 1, NEXT_TETROIMINO_SIZE + 2, NEXT_TETROIMINO_SIZE + 2);
		renderer.end();

		renderer.begin(ShapeRenderer.ShapeType.Filled);
		currentTetromino.render(renderer);
		nextTetromino.render(renderer, nextTetriminoBoxX, nextTetriminoBoxY, NEXT_TETROIMINO_SIZE / 4);
		renderer.end();

		batch.setProjectionMatrix(camera.combined);
		//отображение очков
		batch.begin();
		scoreFont.draw(batch, String.format("Score: %d", score), nextTetriminoBoxX, nextTetriminoBoxY - 30);
		batch.end();
	}

	private void registerSoftKeyPressEvent(Actor softKey, final SoftKey key) {
		softKey.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				lastPressedSoftKey = key;
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				lastPressedSoftKey = null;
			}
		});
	}

	private enum SoftKey {
		DOWN, LEFT, RIGHT, ROTATE, PAUSE, STOP;
	}
}
