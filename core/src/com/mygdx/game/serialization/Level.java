package com.mygdx.game.serialization;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.badlogic.gdx.math.Vector2;

/**
 * Clase usada por el JAXBContext para marshalling y unmarshalling de los datos
 * necesarios a un archivo XML
 * 
 * @author masaques
 * @see Position
 * @see com.mygdx.game.controller.GameManager
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "level")
public class Level {
	@XmlElement(name = "mapPath", required = true)
	private String mapPath;
	@XmlElementWrapper(name = "goonPositions", required = true)
	@XmlElement(name = "position", required = true)
	private List<Position> goonPositions = new ArrayList<Position>();
	@XmlElementWrapper(name = "civilPositions", required = true)
	@XmlElement(name = "position", required = true)
	private List<Position> civilPositions = new ArrayList<Position>();
	@XmlElementWrapper(name = "safePositions", required = true)
	@XmlElement(name = "position", required = true)
	private List<Position> safePositions = new ArrayList<Position>();
	@XmlElement(name = "playerPosition", required = true)
	private Position playerPosition;
	@XmlElement(name = "targetPosition", required = true)
	private Position targetPosition;
	public Level(String map, List<Position> goonPositions, List<Position> civilPositions,
			List<Position> safePositions, Position playerPosition,Position targetPosition) {
		this.mapPath = map;
		this.goonPositions = goonPositions;
		this.playerPosition = playerPosition;
		this.civilPositions = civilPositions;
		this.safePositions = safePositions;
		this.targetPosition = targetPosition ;
	}

	public Level() {
	}

	public String getPath() {
		return mapPath;
	}

	public List<Vector2> goonPositions() {
		return Position.positionToVector(goonPositions);
	}

	public List<Vector2> civilPositions() {
		return Position.positionToVector(civilPositions);
	}

	public Vector2 getPlayer() {
		return new Vector2(playerPosition.getX(), playerPosition.getY());
	}
	
	public Vector2 getTarget() {
		return new Vector2(targetPosition.getX(),targetPosition.getY()) ;
	}
	
	public List<Vector2> safePositions() {
		return Position.positionToVector(safePositions) ;
	}

}
