package com.mygdx.livex;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import java.util.ArrayList;
import java.util.List;


public class PodiumGame extends ApplicationAdapter {
	private OrthographicCamera cam;
	private SpriteBatch batch;
	private SpriteBatch batchLarge;
	private OrthographicCamera camLarge;


	Runnable mOnQuizEnd;
	private Texture white;

	public PodiumGame( ) {

	}

	com.mygdx.livex.AnimatablePositionedTexture korona;
	com.mygdx.livex.AnimatablePositionedTexture napisBoczny;
	List<PositionedTexture> mTextures = new ArrayList<PositionedTexture>();
//198,245

	void loadQuestion() {

	}


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
			Gdx.gl.glClearColor(1, 1, 1, 1);

			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			Gdx.gl.glClearColor(1, 1, 1, 1);
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






			batchLarge.draw(m_fboRegion, 0, y, width, height2);
//
//			float tr = width/(float)mTextTop.getWidth();
//			float fh = mTextTop.getHeight()* tr;
//			batchLarge.draw(mTextTop,0,height-fh, width, fh);
//
//			tr = width/(float)mTextBottom.getWidth();
//			 fh = mTextBottom.getHeight()* tr;
//
//			batchLarge.draw(mTextBottom,0,0, width, fh);

			batchLarge.end();
		}
	}

	public void renderToTexture () {




		batch.begin();
		cam.update();
		camLarge.update();



		batch.setProjectionMatrix(cam.combined);

		batchLarge.setProjectionMatrix(camLarge.combined);


		batch.draw(white, 0, 0, cam.viewportWidth, cam.viewportHeight);
		for(PositionedTexture positionedTexture : mTextures) {
			positionedTexture.draw(batch);
		}

		if(drawKorona)
		korona.draw(batch);
		if(drawNapis)
		napisBoczny.draw(batch);
		float deltaTime = Gdx.graphics.getDeltaTime();


		if(co < 100000) {
			co += deltaTime;

			if (co > 0.5f) {
				drawKorona = true;
				korona.startAnimationIn2();
				co = 100001;
			}
		}
		if(co2 < 100000) {
			co2 += deltaTime;

			if (co2 > 1.5f) {

				drawNapis = true;
				napisBoczny.isVisible2 = true;
				napisBoczny.startAnimationFromLeft();
				co2 = 100001;
			}
		}



		batch.end();

		korona.animate(deltaTime);
		napisBoczny.animate(deltaTime);


	}

	boolean drawNapis = false;
	boolean drawKorona = false;
	float co = 0;
	float co2 = 0;
	@Override
	public void create () {
		batch = new SpriteBatch();
		batchLarge = new SpriteBatch();
		PositionedTexture.screenHeight = 800;
		PositionedTexture.screenWidth = 1280;
		loadQuestion();

		white = new Texture("white.jpg");

		mTextures.add(new PositionedTexture("podium.png", 210, 45));

		korona = new com.mygdx.livex.AnimatablePositionedTexture("napis_left.jpg",221,135);
		napisBoczny = new com.mygdx.livex.AnimatablePositionedTexture("logo_right.jpg", 508,275);

		korona.isVisible2 = false;
		napisBoczny.isVisible2 = false;

		cam = new OrthographicCamera(1280, 800);
		camLarge = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		camLarge.position.set(camLarge.viewportWidth / 2f, camLarge.viewportHeight / 2f, 0);
		cam.update();
		camLarge.update();


	}
}
