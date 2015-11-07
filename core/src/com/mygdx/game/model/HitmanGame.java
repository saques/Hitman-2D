package com.mygdx.game.model;


import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.view.screens.menu.view.MenuUI;

/**
 * <p> Clase del juego, {@link Game} es un {link ApplicationListener} (clase que se encarga del renderizado
 * y ejecucion de libgdx)  que delega el trabajo a una {@link Screen} mediante el metodo <i>setScreen</i>.
 * </p>
 * 
 * @author jcaracciolo
 * 
 * @see com.badlogic.gdx.Game
 * @see com.badlogic.gdx.ApplicationListener
 * @see com.badlogic.gdx.Screen
 *
 */

public class HitmanGame extends Game{

	public SpriteBatch batch;
	public MenuUI menuui;
	
	public HitmanGame(){
		super();
		menuui = new MenuUI();
	}
	@Override
	public void create() {
		batch = new SpriteBatch();
		setScreen(menuui);
	}
	
	public void render () {
		batch.begin();
		Texture background = new Texture(Gdx.files.internal("assets/background.png"));
		TextureRegion backgroundRegion = new TextureRegion(background, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(backgroundRegion, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		
		menuui.render(1f);
		
		batch.end();
	}


		
}