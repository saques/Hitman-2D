package com.mygdx.game.view.screens;

import java.io.IOException;

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

import serialization.GameSerializer;

public class LevelScreen implements Screen{

	private HitmanGame game;
	
	
	private OrthographicCamera camera = new OrthographicCamera();
	private Viewport gameport;

	
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	ControlProcessor input;
	
	FPSLogger fps_logger =new FPSLogger();
	ShapeRenderer shapeRenderer;
	GameManager gameManager;
	 
	public LevelScreen(HitmanGame game){
		Gdx.input.getInputProcessor();
		
		this.game = game;
		gameport = new FitViewport(864, 864,camera);
		gameManager = new GameManager(864,864,32,20);
//		try {
//			gameManager = GameSerializer.create("prueba") ;
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		renderer = new OrthogonalTiledMapRenderer(gameManager.getTiledMap());
		camera.position.set(gameport.getWorldWidth()/2,gameport.getWorldHeight()/2,0);
		shapeRenderer = new ShapeRenderer();
		
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
		
		
		gameManager.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		
		camera.update();
        renderer.setView(camera);
        renderer.render();
       
        gameManager.update();
		
		
	}

	@Override
	public void resize(int width, int height) {
		gameport.update(width, height);
		
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