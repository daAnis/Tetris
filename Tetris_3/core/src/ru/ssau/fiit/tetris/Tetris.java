package ru.ssau.fiit.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

import java.util.concurrent.TimeUnit;

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
	private long time;
	private SoftKey lastPressedSoftKey;
	private String string;

	private IActivityRequestHandler myRequestHandler;
	private double pointsUp;
	private double speedUp;
	private boolean nextFigureShow;
	private byte resultDisplay;

	public Tetris (IActivityRequestHandler handler,
				   int columns, int rows, double pointsUp, double speedUp,
				   boolean nextFigureShow, byte resultDisplay) {
		myRequestHandler = handler;

		NUM_COLUMNS = columns;
		NUM_ROWS = rows;
		CELL_SIZE = (STAGE_WIDTH / columns) * 32 / 48;

		this.pointsUp = pointsUp;
		this.speedUp = speedUp;
		this.nextFigureShow = nextFigureShow;
		this.resultDisplay = resultDisplay;
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
		//устанавливаем скорость падения фигур
		//fallingSpeed = 4.5f; // блоков в секунду
		fallingSpeed = (float) speedUp;
		gameStage = new GameStage();

		//получение первой и второй фигуры
		currentTetromino = Tetromino.getInstance();
		nextTetromino = Tetromino.getInstance();

		//создание игрового поля
		gameStage.setPosition(STAGE_START_X, STAGE_START_Y);
		stage.addActor(gameStage);

		//добавление кнопок управления
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
		pause.setPosition(CELL_SIZE * NUM_COLUMNS, 270);
		stop.setPosition(CELL_SIZE * NUM_COLUMNS, 180);
		controlGroup.addActor(rightArrow);
		controlGroup.addActor(leftArrow);
		controlGroup.addActor(circle);
		controlGroup.addActor(downArrow);
		controlGroup.addActor(pause);
		controlGroup.addActor(stop);
		stage.addActor(controlGroup);

		//начальная инициализация результата
		score = 0;
		time = TimeUtils.millis();
	}

	//изменение размеров игрового поля при изменении размеров окна
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	//отрисовка игры
	@Override
	public void render() {
		float r = 255f / 255f, g = 237f / 255f, b = 247f /  255f;
		Gdx.gl.glClearColor(r,g,b,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//игра остановлена
		if (!isGameGoing) {
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			gameoverFont.draw(batch, string,
					(STAGE_WIDTH - (string.length() / 2 * gameoverFont.getLineHeight())) / 2,
					STAGE_HEIGHT / 2 - gameoverFont.getLineHeight() / 2);
			batch.end();
			//отслеживаем нажатие на экран
			if (Gdx.input.isTouched()) {
				//если до этого была выбрана пауза, то игра возобновится
				if (string.compareTo("Pause") == 0) {
					isGameGoing = true;
					myRequestHandler.onGamePaused();
				} else {
					myRequestHandler.onGameClosed(score, TimeUtils.timeSinceMillis(time));
				}
			}
			return;
		}

		//движение фигуры сверху вниз
		if (TimeUtils.millis() - lastFallMillis > (1 / fallingSpeed) * 1000) {
			lastFallMillis = TimeUtils.millis();
			currentTetromino.fall();
		} else if (lastPressedSoftKey == SoftKey.ROTATE && TimeUtils.millis() - lastRotateMillis > MIN_ROTATE_INTERVAL_MILLIS) {
			//поворот фигуры
			currentTetromino.rotate(gameStage);
			lastRotateMillis = TimeUtils.millis();
		}

		//фигура достигла дна, необходимо проверить на собранную строку
		if (gameStage.isOnGround(currentTetromino.getBlocks())) {
			int numDeletedRows = gameStage.setBlocks(currentTetromino.getBlocks());

			//score += SCORES[numDeletedRows];
			score += pointsUp * numDeletedRows * NUM_COLUMNS;

			//получение следующей фигуры
			currentTetromino = nextTetromino;
			currentTetromino.initPosition();
			nextTetromino = Tetromino.getInstance();
			//если следующая фигура сразу упирается в дно, то проигрыш
			if (gameStage.isOnGround(currentTetromino.getBlocks())) {
				isGameGoing = false;
				string = "You lose";
				return;
			}
		} else if (lastPressedSoftKey == SoftKey.LEFT && TimeUtils.millis() - lastHorizontalMoveMillis > MIN_HORIZONTAL_MOVE_INTERVAL_MILLIS) {
			//поворот фигуры налево
			currentTetromino.moveToLeft(gameStage);
			lastHorizontalMoveMillis = TimeUtils.millis();
		} else if (lastPressedSoftKey == SoftKey.RIGHT && TimeUtils.millis() - lastHorizontalMoveMillis > MIN_HORIZONTAL_MOVE_INTERVAL_MILLIS) {
			//поворот фигуры направо
			currentTetromino.moveToRight(gameStage);
			lastHorizontalMoveMillis = TimeUtils.millis();
		} else if (lastPressedSoftKey == SoftKey.DOWN && TimeUtils.millis() - lastFallMillis > MIN_FALL_INTERVAL_MILLIS) {
			//движение фигуры вниз
			lastFallMillis = TimeUtils.millis();
			currentTetromino.fall();
		} else if (lastPressedSoftKey == SoftKey.PAUSE) {
			//постановка игры на паузу
			isGameGoing = false;
			string = "Pause";
			return;
		} else if (lastPressedSoftKey == SoftKey.STOP) {
			//завершение игры игроком
			isGameGoing = false;
			string = "Game stopped";
			return;
		}

		//перерисовка
		camera.update();
		renderStage();
	}

	//освобождение ресурсов
	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
		renderer.dispose();
		gameoverFont.dispose();
		scoreFont.dispose();
	}

	//отвечает игрового поля
	@SuppressWarnings("DefaultLocale")
	private void renderStage() {
		//обновляет сцену//время между последним и текущим кадром в секундах
		stage.act(Gdx.graphics.getDeltaTime());
		//отрисовывает сцену
		stage.draw();

		int nextTetriminoBoxX = CELL_SIZE * NUM_COLUMNS + 2 * STAGE_START_X;
		int nextTetriminoBoxY = STAGE_START_Y + CELL_SIZE * NUM_ROWS - NEXT_TETROIMINO_SIZE;
		renderer.setProjectionMatrix(camera.combined);

		//отображение следующей фигуры
		if (nextFigureShow) {
			renderer.begin(ShapeRenderer.ShapeType.Line);
			renderer.rect(nextTetriminoBoxX - 1, nextTetriminoBoxY - 1, NEXT_TETROIMINO_SIZE + 2, NEXT_TETROIMINO_SIZE + 2);
			renderer.end();
		}

		//отображение движения текущей фигуры по полю
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		currentTetromino.render(renderer);
		if (nextFigureShow)
			nextTetromino.render(renderer, nextTetriminoBoxX, nextTetriminoBoxY, NEXT_TETROIMINO_SIZE / 4);
		renderer.end();


		//отображение результатов
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		if (resultDisplay == 0)
			//если выбран подсчет результатов на очки
			scoreFont.draw(batch, String.format("Score: %d", score), nextTetriminoBoxX, nextTetriminoBoxY - 30);
		else if (resultDisplay == 1)
			//если выбран подсчет результатов на время
			scoreFont.draw(batch, String.format("Time: %02d : %02d ",
					TimeUnit.MILLISECONDS.toMinutes(TimeUtils.timeSinceMillis(time)),
					TimeUnit.MILLISECONDS.toSeconds(TimeUtils.timeSinceMillis(time)) -
							TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(TimeUtils.timeSinceMillis(time)))),
					nextTetriminoBoxX, nextTetriminoBoxY - 30);
		batch.end();
	}

	//отслеживает события нажатия на игровые кнопки
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

	//игровые кнопки
	private enum SoftKey {
		DOWN, LEFT, RIGHT, ROTATE, PAUSE, STOP;
	}
}
