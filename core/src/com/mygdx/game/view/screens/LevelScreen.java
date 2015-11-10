package com.mygdx.game.view.screens;

import java.io.File;
import java.io.IOException;
import serialization.Level;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *  Screen del nivel principal donde se llevara a cabo el juego. Posee la información completa
 *  del juego y se encarga del renderizado.
 *  
 *  @author jcaracciolo
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.controller.ControlProcessor;
import com.mygdx.game.model.GameManager;
import com.mygdx.game.model.HitmanGame;
import com.mygdx.game.model.IllegalPositionException;

import serialization.GameSerializer;

public class LevelScreen implements Screen{

	private HitmanGame game;
	
	
	private OrthographicCamera camera;
	private Viewport gameport;

	private OrthogonalTiledMapRenderer renderer;
	
	ControlProcessor input;
	
	FPSLogger fps_logger =new FPSLogger();
	GameManager gameManager;
	 
	public LevelScreen(HitmanGame game) throws JAXBException{
		/**
		 * Carga el nivel desde archivo
		 */
		JAXBContext context = JAXBContext.newInstance(Level.class) ;
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Level l = (Level)unmarshaller.unmarshal(new File("assets/Level1.xml")) ;
		
		this.game = game;
		
		
		
		camera = new OrthographicCamera();
		gameport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),camera);
		try {
			gameManager = new GameManager(864,864,32,gameport,l);
		} catch (IllegalPositionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		renderer = new OrthogonalTiledMapRenderer(gameManager.getTiledMap());
		
		
	}
	
	public void update(float dt){
		
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		fps_logger.log();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gameManager.update();
		game.batch.setProjectionMatrix(camera.combined);
        renderer.setView(camera);
        renderer.render();
        gameManager.update();
		
		
	}

	@Override
	public void resize(int width, int height) {
		gameport.update(width, height, true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}