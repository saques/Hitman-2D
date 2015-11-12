package com.mygdx.game.test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.model.LevelMap;
import com.mygdx.game.model.character.AStarPathFinder;
import com.mygdx.game.model.character.Goon;
import com.mygdx.game.model.character.LinearPathFinder;
import com.mygdx.game.model.character.NPCState;
import com.mygdx.game.model.message.BulletManager;
import com.mygdx.game.model.message.Noise;
import com.mygdx.game.model.message.NoiseManager;
import com.mygdx.game.model.message.NoiseType;
import com.mygdx.game.model.message.VisionManager;
import com.mygdx.game.model.util.RandList;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class GoonTest {

	private static List<Vector2> randArray = new RandList<Vector2>();
	private static LevelMap map;
	private static Goon goon;
	private static AStarPathFinder aStarPathFinder;
	private static LinearPathFinder linearPathFinder;

	@BeforeClass
	public static void init() {
		
		randArray.add(new Vector2(200, 150));
		randArray.add(new Vector2(700, 700));
		randArray.add(new Vector2(73, 792));
		randArray.add(new Vector2(817, 48));
		
		try {
			ObjectInputStream file = new ObjectInputStream(
					 new BufferedInputStream(
					 new FileInputStream("logicmap1")));
			map = (LevelMap)file.readObject();
			
			file.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		goon = new Goon(new Rectangle(5, 5, 18, 13), map, randArray);
		aStarPathFinder = new AStarPathFinder(map, 100);
		linearPathFinder = new LinearPathFinder(map);
		goon.setAStarPathFinder(aStarPathFinder);
		goon.setLinearPathFinder(linearPathFinder);
		NoiseManager.getInstance().addListener(goon);
		VisionManager.getInstance().addListener(goon);
		BulletManager.getInstance().addListener(goon);
	}
	
	
	@Test
	public void hearSound() {
		NoiseManager.getInstance().dispatchMessage(new Noise(goon.getCenter(), 100, NoiseType.RUN));
		NoiseManager.getInstance().update();
		Assert.assertTrue(goon. == NPCState.SUSPICIOUS);
	}
	
	
//	@Test
//	public void noHearSound() {
//		Goon goon3 = new Goon(new Rectangle(100, 100, 18, 13), map, randArray);
//		goon3.addNoise(new Noise(goon3.getPosition(), 2, NoiseType.SHOOT));
//
//		Assert.assertTrue(goon.getState() == NPCState.CALM);
//
//	}
	

	//Da�ado si le disparan
	@Test
	public void damageByBullet(){
		Goon goon4= new Goon(new Rectangle(10, 10, 18, 13), map, randArray);
		//ok, aca como se que el goon4 lo esta viendo para saber si le dispara??
		goon4.shoot();
		goon.dealDamage(1f);
		Assert.assertTrue(goon.isHurt()==true);
	}
	
	@Test
	public void noDamage(){
		Goon goon5 = new Goon(new Rectangle(10, 10, 18, 13), map, randArray);
		goon5.move(goon.getPosition().add(new Vector2(500,500)));
		//este goon tendria que estar disparando en otra direccion! Este "goon5" no 
		//tendria que ver a "goon"!
		goon5.shoot();
	
		Assert.assertTrue(goon.isHurt()==false);
	}
	
	@Test
	public void Damage(){
		Goon goon5 = new Goon(new Rectangle(10, 10, 18, 13), map, randArray);
		goon5.move(goon.getPosition());
		//este goon tendria que estar disparando en otra direccion! Este "goon5" no 
		//tendria que ver a "goon"!
		goon5.shoot();
	
		Assert.assertTrue(goon.isHurt()==true);
	}
	
	@Test
	public void isDead(){
		Goon goon7 = new Goon(new Rectangle(6, 12, 18, 13), map, randArray);
		goon7.dealDamage(100f);
		Assert.assertTrue(goon7.isDead() == true);
		//descontarle todas las vidas y preguntar si esta muerto
	}
	
	@Test
	public void isNotDead(){
		Goon goon8 = new Goon(new Rectangle(6, 12, 18, 13), map, randArray);
		goon8.dealDamage(10f); //supero la cantidad de puntos que puede tener...
		Assert.assertFalse(goon8.isDead()==true);
		//creo que es asi... tiene que dar true porque estoy negando que goon8
		//este vivo. Cualquier cosa cambiar a 
		//assertTrue y poner   ==false
	}
	
	@Test
	public void dealExtremeDamage(){
		Goon goon9 = new Goon(new Rectangle(6, 12, 18, 13), map, randArray);
		goon9.dealDamage(150f); //supero la cantidad de puntos que puede tener...
		Assert.assertTrue(goon9.isDead() == true);
		//tendria que estar muerto, sin tirar ninguna excepcion
	}

	


}
