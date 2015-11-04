/*
 *@author Tomas Raies
 *@date   13 de oct. de 2015
 */

package com.mygdx.game.model.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.LevelMap;
import com.mygdx.game.model.message.Bullet;
import com.mygdx.game.model.message.BulletManager;
import com.mygdx.game.model.message.Noise;
import com.mygdx.game.model.message.NoiseManager;
import com.mygdx.game.model.message.Vision;
import com.mygdx.game.model.message.VisionManager;
import com.mygdx.game.model.message.VisionSender;

import serialization.CharacterInformation;

/**
 * El personaje jugable. Extiende de {@link Character}.
 */
public class Player extends Character implements VisionSender {
	private static final double RUNNING_NOISE_RANGE = 200;
	private static final float NORMAL_SPEED = 60f;
	private static final float RUNNING_SPEED = 100f;
	
	public Player(Rectangle hitBox, LevelMap map) {
		super(hitBox, map);
		BulletManager.getInstance().addListener(this);
	}
	/**
	 * Constructor alternativo usado al cargar la informacion desde un archivo
	 * @param data
	 * @param map
	 * @see Character
	 */
	public Player(CharacterInformation data,LevelMap map) {
		super(data,map);
		BulletManager.getInstance().addListener(this);
	}
	@Override
	public void update() {
		sendPosition() ;
		super.update();
	}
	
	public void stopMoving() {
		this.isMoving = false;
	}
	@Override
	public void sendPosition() {
		VisionManager.getInstance().dispatchMessage(new Vision(this,map));
		if (isMoving() && isRunning()) {
			NoiseManager.getInstance().dispatchMessage(new Noise(this.getPosition(), RUNNING_NOISE_RANGE, false));
		}
	}
	/**
	 * El parametro to aca deberia ser el input del mouse
	 */
	
	public void shoot() {
		BulletManager.getInstance().dispatchMessage(new Bullet(this,this.getCenter(),getLookDirection()));
		NoiseManager.getInstance().dispatchMessage(new Noise(this.getPosition(),100,true));
	}
	@Override
	protected void moveAlong(){
		float speed;
		if (isRunning()){
			speed = RUNNING_SPEED;
		}
		else {
			speed = NORMAL_SPEED;
		}
		Rectangle currHitBox = getDirectionalHitBox(getMoveDirection(), speed);
		Vector2 moveDirection = getMoveDirection();
		if (!map.isValid(currHitBox)) {
			currHitBox = getDirectionalHitBox(new Vector2(moveDirection.x,0).nor(), speed);
			if (!map.isValid(currHitBox) || moveDirection.x == 0f){
				currHitBox = getDirectionalHitBox(new Vector2(0,moveDirection.y).nor(), speed);
				if (!map.isValid(currHitBox) || moveDirection.y == 0f){
				    isMoving = false;
					return;
				}
			}
		}
		getHitBox().set(currHitBox);
		return;
	}
	
	/**
	 * Metodo privado que calcula un Rectangle segun una direccion determinada, una 
	 * posicion inicial y una velocidad. Usado por el metodo moveAlong.
	 * 
	 * @param direction - Direción del rectangulo
	 * @param speed - velocidad del objeto
	 */
	
	private Rectangle getDirectionalHitBox(Vector2 direction, float speed) {
		Vector2 position = getHitBox().getCenter(new Vector2());
		Vector2 velocity = new Vector2(direction).scl(speed);
		Vector2 movement = velocity.scl(Gdx.graphics.getDeltaTime());
		position.add(movement);
		Rectangle currHitBox = new Rectangle(getHitBox());
		currHitBox.setCenter(position); 
		return currHitBox;
	}
	
	@Override
	public void die() {
		BulletManager.getInstance().removeListener(this);
	}
}
