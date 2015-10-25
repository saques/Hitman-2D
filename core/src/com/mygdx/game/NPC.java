/*
 *@author Tomas Raies
 *@date   13 de oct. de 2015
 */

package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Context;
import com.mygdx.game.LevelMap;
import com.mygdx.game.Path;
import com.mygdx.game.PathFinder;
import com.mygdx.game.Step;
import com.mygdx.game.Strategy;

/*
 * Personajes no controlados por el jugador. Debem implementar comportamientos diferentes
 * segun el contexto del juego. Ademas, deben ser capaces de encontrar el camino entre dos 
 * puntos en el mapa.
 */
public abstract class NPC extends Character implements NoiseListener, VisionListener {
	private static final float VISUAL_RANGE = 9000f ;
	private static final float VISUAL_ANGLE = 100f ;
	protected static final float EPSILON = 2f;
	protected  Path currentPath;
	protected Step finalStep;
	protected Step currentStep = null;
	private PathFinder aStarPathFinder;
	private PathFinder linearPathFinder;
	private long shootTimer;
	private StateMachine stateMachine;
	private Inbox<Noise> noiseInbox;
	private Inbox<Vector2> visualInbox;
 	
	
	public NPC (Rectangle hitBox, LevelMap map){
		super(hitBox, map);
		noiseInbox = new Inbox<Noise>();
		visualInbox = new Inbox<Vector2> ();
		shootTimer = System.currentTimeMillis();
	}
	public void setStateMachine(StateMachine stateMachine){
		this.stateMachine = stateMachine;
	}
	public void setAStarPathFinder(PathFinder pathFinder){
		this.aStarPathFinder = pathFinder;
	}
	public void setLinearPathFinder(PathFinder pathFinder) {
		this.linearPathFinder = pathFinder;
	}
	//...hasta ac�
	
	/*
	 * Setea el camino para llegar a un punto en el mapa, si es posible.
	 * @param position
	 */
	public boolean moveTo(Vector2 position, boolean linear) {
		if (finalStep != null && position.epsilonEquals(finalStep.getPosition(), EPSILON )){
			return false;
		}
		Vector2 currPosition = new Vector2();
		currPosition = hitBox.getPosition(currPosition);
		PathFinder pathFinder;
		if (linear) {
			pathFinder = linearPathFinder;
		}
		else {
			pathFinder = aStarPathFinder;
		}
		Path auxPath = pathFinder.findPath(this, currPosition, position);
		if (auxPath != null && auxPath.hasNextStep()){
			currentPath = auxPath;
			currentStep = currentPath.nextStep();
			move(currentStep.getPosition().sub(getPosition()));
			isMoving = true;
			return true;
		}
		return false;
	}
	
	/*
	 * Actualiza el estado del NPC
	 */
	@Override
	public void update() {
		Context context = new Context(noiseInbox.get(),visualInbox.get(), isMoving, shootTimer);
		ActionRequest actionRequest = stateMachine.updateMachine(context);
		processActionRequest(actionRequest);
		updatePosition();
		super.update();	
	}
	
	private void processActionRequest(ActionRequest actionRequest){
		switch(actionRequest.getRequest()){
		case ActionRequest.REQUEST_NOTHING:
			break;
		case ActionRequest.REQUEST_MOVETO:
			moveTo(actionRequest.getPosition(), actionRequest.getLinear());
			break;
		case ActionRequest.REQUEST_SHOOT:
			shoot(actionRequest.getPosition());
			break;
		default:
			break;
		}
	}
	private void updatePosition() {
		if (!isMoving || currentPath == null){
			isMoving = false;
			currentPath = null;
			currentStep = null;
			return;
		}
	
		if (currentStep == null || currentStep.getPosition().epsilonEquals(getPosition(), EPSILON)){
			if (currentPath.hasNextStep()){
				currentStep = currentPath.nextStep();
			}
			else {
				currentPath = null;
				isMoving = false;
			}
		}
		if (isMoving){
			/*
			 * Ineficiente? Probablemente, pero me soluciona la vida por ahora. (Tiene que hacer una raiz cuadrada por frame) 
			 */
			move(currentStep.getPosition().sub(getPosition()));
		}
	}
	
	
	/**
	 * Metodo para calcular si el jugador esta en el campo visual de este NPC
	 * Utilizado por VisionHandler
	 * @param playerPosition
	 * @return true si el jugador es visible, false si no
	 */
	
	public boolean canSee(Vector2 playerPosition) {
		Vector2 goonPosition = new Vector2((float)this.hitBox.x,(float)this.hitBox.y) ;
		Vector2 goonDirection = this.direction ;
		if (playerPosition.dst2(goonPosition)> VISUAL_RANGE){
			return false ;
		}
		if (!map.isValid(goonPosition, playerPosition)) {
			return false ;
		}
		Vector2 relativeDirection = playerPosition.sub(goonPosition).nor() ;
		if (Math.abs(relativeDirection.angle(goonDirection))<=VISUAL_ANGLE/2) {
			return true ;
		}
		return false ;
	}
	
	/*
	 * Anade un mensaje al contexto. TODO en realidad, el NPC deberia mandar cualquier mensaje,
	 * sin importar si es un Noise o de cualquier otro tipo. El contexto deberia saber manejar
	 * distintos tipos de mensajes. Por eso, deberia haber un unico metodo addToContext(Message m).
	 */
//	public void addNoisetoContext(Noise n){
//		context.add(n);
//	}
//	
	public void addPlayer(Vector2 playerPosition) {
		visualInbox.add(playerPosition);
	}
	
	public void addNoise(Noise n){
		noiseInbox.add(n);
	}
	
	public float visualRange() {
		return VISUAL_RANGE ;
	}
	public float visualAngle() {
		return VISUAL_ANGLE ;
	}
	public void shoot(Vector2 playerPosition) {
		move(playerPosition);
		isMoving = false;
		shootTimer = System.currentTimeMillis() + 1000l;
		System.out.println("BANG!!!");
	}
}
