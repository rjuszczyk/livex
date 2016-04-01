package com.mygdx.livex;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;


import java.util.ArrayList;
import java.util.List;


public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	private final Runnable mPlayNegative;
	private OrthographicCamera cam;
	private SpriteBatch batch;
	private SpriteBatch batchLarge;
	private OrthographicCamera camLarge;

	public static final float ANIMATION_SPEED = 3;
	Runnable mOnQuizEnd;
	Runnable mPlayPositive;
Runnable mPlayBravo;
	public enum Sex {
		MALE,
		FEMALE
	}
	Sex mSex;
	public MyGdxGame(Runnable onQuizEnd, Runnable playPositive, Runnable playNegative,  Runnable playBravo, Sex sex) {
		mOnQuizEnd = onQuizEnd;
		mSex = sex;
		mPlayPositive = playPositive;
		mPlayNegative = playNegative;
		mPlayBravo = playBravo;
	}


	List<Answer> answers;

	List<PositionedTexture> mTextures = new ArrayList<PositionedTexture>();
	DropBox dropBox;

	void loadQuestion() {
		answers = new ArrayList<Answer>(12);
		answers.add(new Answer(15,178, new Texture("odp1.png"), false));
		answers.add(new Answer(15,299, new Texture("odp2.png"), true, 0));
		answers.add(new Answer(15,420, new Texture("odp3.png"), true, 1));
		answers.add(new Answer(15,539, new Texture("odp4.png"), false));

		answers.add(new Answer(200,677, new Texture("odp5.png"), false));
		answers.add(new Answer(434,677, new Texture("odp6.png"), false));
		answers.add(new Answer(667,677, new Texture("odp7.png"), false));
		answers.add(new Answer(895,677, new Texture("odp8.png"), false));

		answers.add(new Answer(1075,178, new Texture("odp9.png"), true,3));
		answers.add(new Answer(1075,299, new Texture("odp10.png"), false));
		answers.add(new Answer(1075,420, new Texture("odp11.png"), false));
		answers.add(new Answer(1075,539, new Texture("odp12.png"), true,2));
	}

	void onQuizEnd() {
		mOnQuizEnd.run();
	}
	int left = 0;
	int top = 0;

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);


		cam.viewportWidth = 1280;
		cam.viewportHeight = 800;
		camLarge.viewportWidth = width;
		camLarge.viewportHeight = height;
		camLarge.position.set(camLarge.viewportWidth / 2f, camLarge.viewportHeight / 2f, 0);

		cam.update();
		camLarge.update();
	}


	private boolean m_fboEnabled = true;
	private FrameBuffer m_fbo = null;
	private TextureRegion m_fboRegion = null;
	@Override
	public void render() {
		// | GL20.GL_DEPTH_BUFFER_BIT);

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		if(m_fboEnabled)      // enable or disable the supersampling
		{
			if(m_fbo == null)
			{
				// m_fboScaler increase or decrease the antialiasing quality

				m_fbo = new FrameBuffer(Pixmap.Format.RGBA8888, (int)(1280), (int)(800), false);
				m_fboRegion = new TextureRegion(m_fbo.getColorBufferTexture());
				m_fboRegion.flip(false, true);
			}

			m_fbo.begin();
			Gdx.gl.glClearColor(0,0,0,0);

			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			// | GL20.GL_DEPTH_BUFFER_BIT);
		}

		// this is the main render function
		renderToTexture();



		if(m_fbo != null)
		{
			m_fbo.end();

			Gdx.gl.glClearColor(1, 1, 1, 1);

			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			batchLarge.begin();



			float ratio = 800f/1280f;
			float height2 = width*ratio;
			float y = height-height2;
			y/=2;





			float tr = width/(float)mTextTop.getWidth();
			float fh = mTextTop.getHeight()* tr;
			
			batchLarge.draw(m_fboRegion, 0, y, width, height2);
			batchLarge.draw(mTextTop,0,(int)(height-fh), width, (int) fh);

			batchLarge.end();
		}
	}

	public void renderToTexture () {


		batch.begin();
		cam.update();
		camLarge.update();

		batch.setProjectionMatrix(cam.combined);
		batchLarge.setProjectionMatrix(camLarge.combined);

		batch.draw(white,0,0, cam.viewportWidth, cam.viewportHeight);

		for(PositionedTexture positionedTexture : mTextures) {
			positionedTexture.draw(batch);
		}

		dropBox.draw(batch);



		float deltaTime = Gdx.graphics.getDeltaTime();

		for(Answer answer : answers) {
			answer.draw(batch);
		}


		batch.end();

		dropBox.isWrong((int) (deltaTime * 1000), new Runnable() {
			@Override
			public void run() {

			}
		});
		dropBox.isGood((int) (deltaTime * 1000), new Runnable() {
			@Override
			public void run() {

				if(positiveAnswersCount == 4) {
					mPlayBravo.run();
					dropBox.setFinishhing();
					for(Answer answer : answers ) {
						answer.animateOut();
					}
				} else {
					dropBox.reset();
				}
			}
		});
		dropBox.isFinishing((int) (deltaTime * 1000), new Runnable() {
			@Override
			public void run() {
				onQuizEnd();
			}
		});

		for(Answer answer : answers) {
			answer.animate(deltaTime);
		}
	}
	Texture mTextTop;
	Texture white;
	@Override
	public void create () {
		batch = new SpriteBatch();
		batchLarge = new SpriteBatch();
		PositionedTexture.screenHeight = 800;
		mTextTop = new Texture("pytanie_bg_top.png");
		mTextTop.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		loadQuestion();
		white = new Texture("white.jpg");

		if(mSex==Sex.MALE) {
			dropBox = new DropBox(207, 152, new Texture("center_box.png"), new Texture("center_box_bad.png"), new Texture("center_box_good.png"), new Texture("center_box_finish_he.png"));
		} else {
			dropBox = new DropBox(207, 152, new Texture("center_box.png"), new Texture("center_box_bad.png"), new Texture("center_box_good.png"), new Texture("center_box_finish_she.png"));
		}
		Gdx.input.setInputProcessor(this);

		cam = new OrthographicCamera(1280, 800);
		camLarge = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		camLarge.position.set(camLarge.viewportWidth / 2f, camLarge.viewportHeight / 2f, 0);
		cam.update();
		camLarge.update();
	}




	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Gdx.app.log("input", "touchDown");
		if(dropBox.isWrong()) {
			return false;
		}

//		int x = (int) (((float)screenX/(float)Gdx.graphics.getWidth())* cam.viewportWidth);
//		int y = (int) ((1- ((float)screenY/(float)Gdx.graphics.getHeight()))* cam.viewportHeight);

		int x=screenX;
		int y=800-screenY;

		for(Answer answer : answers) {
			if (answer.contains(x,y)) {
				answer.startDrag(x,y);
				return false;
			}
		}
		return false;
	}

	int positiveAnswersCount = 0;

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Gdx.app.log("input", "touchUp");
//		int x = (int) (((float)screenX/(float)Gdx.graphics.getWidth())* cam.viewportWidth);
//		int y = (int) ((1- ((float)screenY/(float)Gdx.graphics.getHeight()))* cam.viewportHeight);

		int x=screenX;
		int y=800-screenY;

		for(final Answer answer : answers) {
			answer.onEndDrag(x,y, new Answer.EndDragCallback() {
				@Override
				public void onEndDrag(int endX, int endY) {
					if (dropBox.contains(endX, endY)) {
						if (answer.isCorrect()) {
							answer.startAnimation(answer.getPosition(), dropBox.getNextFreePosition(answer.targetIndex), 5f);

							positiveAnswersCount++;
							answer.showGood();
							dropBox.setGood();
							mPlayPositive.run();
						} else {
							answer.startAnimation(answer.getPosition(), answer.getStartPosition(), 4f);
							dropBox.setWrong();
							mPlayNegative.run();
						}


					} else {
						answer.startAnimation(answer.getPosition(), answer.getStartPosition(), 1.2f);
					}
				}
			});
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
//		int x = (int) (((float)screenX/(float)Gdx.graphics.getWidth())* cam.viewportWidth);
//		int y = (int) ((1- ((float)screenY/(float)Gdx.graphics.getHeight()))* cam.viewportHeight);
		int x=screenX;
		int y=800-screenY;

		for(Answer answer : answers) {
			answer.onDrag(x,y);
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
