package com.mygdx.game.model.message;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.character.Team;

/**
 * Interface que implementan objetos dañables.
 * 
 * @author masaques
 *
 * @see BulletManager
 */
public interface BulletListener extends Listener {
	public Vector2 getPosition();

	public Rectangle getHitBox();

	public Vector2 getMoveDirection();

	/**
	 * <p>
	 * Método que aplica daño al objeto
	 * </p>
	 * 
	 * @param dmg
	 *            - Cantidad de daño a recibir
	 */
	public void dealDamage(float dmg);

	public Team getTeam();
}
