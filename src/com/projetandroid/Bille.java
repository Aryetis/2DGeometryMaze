package com.projetandroid;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;

import java.util.ArrayList;



public class Bille {

	public Rectangle sprite;
	public static Bille instance;
					/*
					*  Tout comme BaseActivity, en théorie il n'existe qu'une seule bille a chaque fois
					*  on va donc se servir de la propriété static de cette variable pour pouvoir 
					*  récuperer "l'instance unique" de bille à chaque création de bille,
					*  en faisant New Bille B = B.getSharedInstance
					*/
	Camera mCamera;
	boolean moveable;

	public static Bille getSharedInstance() {
		if (instance == null)
			instance = new Bille();
		return instance;
	}
	public static Bille getSharedInstance(float xSpawn, float ySpawn) {
		if (instance == null)
			instance = new Bille(xSpawn, ySpawn);
		return instance;
	}

	private Bille() {
		moveable = true;
		sprite = new Rectangle(0, 0, 15, 15, BaseActivity.getSharedInstance()
				.getVertexBufferObjectManager());

		mCamera = BaseActivity.getSharedInstance().mCamera;
		sprite.setPosition(mCamera.getWidth() / 2 - sprite.getWidth() / 2,
				mCamera.getHeight() /2 - sprite.getHeight() /2);
	}
	private Bille(float xSpawn, float ySpawn) {
		moveable = true;
		sprite = new Rectangle(xSpawn, ySpawn, 15, 15, BaseActivity.getSharedInstance()
				.getVertexBufferObjectManager());

		mCamera = BaseActivity.getSharedInstance().mCamera;
	}
	public void finishHim()
	// pas de fonction finalize, car le garbageCollector nous joue des tours sans ca
	// et supprime l'objet parfois de façon prématurée
    {
		sprite.detachSelf();
    }
	
	public void moveBille(float accelerometerSpeedX, float accelerometerSpeedY, ArrayList<Wall> yo,boolean curWorld) {
		/*
		 * Fonction qui actualise la position de la bille selon les inputs de l'accelerometre
		 * Detecte les collisions, et est capable d'éjecter la bille du mur si jamais celle ci
		 * se retrouve coincée dans le mur pour une quelconque raison (mauvais endroit d'apparition de la bille)
		 */
		if (!moveable)
			return;
		if (accelerometerSpeedX != 0 || accelerometerSpeedY != 0) {

			int lL = 0;
			int rL = (int) (mCamera.getWidth() - (int) sprite.getWidth());
			int rL2 =(int) (mCamera.getHeight() - (int) sprite.getHeight());
			float newX;
			float newY;
			float newXb=0;
			float newYb=0;
			boolean y=false,x=false;



			// On calcule les nouvelles coordonnées dans les limites de k'ecran
			if (sprite.getX() >= lL){
				newX = sprite.getX() + accelerometerSpeedX;
			}else{
				newX = lL;
			}

			if(sprite.getY() >= lL){
				newY = sprite.getY() + accelerometerSpeedY;
			}else{
				newY = lL;
			}

			if (newX <= rL)
				newX = sprite.getX() + accelerometerSpeedX;
			else
				newX = rL;

			if(newY <= rL2)
				newY = sprite.getY() + accelerometerSpeedY;
			else
				newY = rL2;

			if(newY <lL)
				newY = lL;
			else if(newY > rL2)
				newY = rL2;

			// Double Check That New X,Y Coordinates are within Limits
			if (newX < lL)
				newX = lL;
			else if (newX > rL)
				newX = rL;

			//add la boucle d'arrayliste de rectangles
			for(Wall w:yo){
				float a = w.sprite.getX();
				float b = w.sprite.getY();
				float c = w.sprite.getX() + w.sprite.getWidth();
				float d = w.sprite.getY() + w.sprite.getHeight();
				float aX = accelerometerSpeedX;
				float aY = accelerometerSpeedY;
				if(curWorld && w.type!=1){
					//types verts et noirs
					if(((sprite.getX()>=a && sprite.getX()<=c) || (sprite.getX()+sprite.getWidth()>=a && sprite.getX()+sprite.getWidth()<=c))
							&& ((sprite.getY()>=b && sprite.getY()<=d) || (sprite.getY()+sprite.getHeight()>=b && sprite.getY()+sprite.getHeight()<=d))){
						if((sprite.getX()>=a && sprite.getX()<=c)&&(sprite.getX()+sprite.getWidth()>=a && sprite.getX()+sprite.getWidth()<=c)){
							if((sprite.getY()>=b+((d-b)/2) && sprite.getY()<=d)||(sprite.getY()+sprite.getHeight()>=b+((d-b)/2) && sprite.getY()+sprite.getHeight()<=d)){
								newYb=(float) (d+0.001);
								sprite.setPosition(newX, newYb);
								y= true;
							}else{
								newYb=(float) (b-sprite.getHeight()-0.001);
								sprite.setPosition(newX, newYb);
								y= true;
							}
						}
						//cas ou la bille rentre entierement dans le mur a l'horizontale
						if((sprite.getY()>=b && sprite.getY()<=d)&&(sprite.getY()+sprite.getHeight()>=b && sprite.getY()+sprite.getHeight()<=d)){
							if(sprite.getX()>=a+((c-a)/2) && sprite.getX()<=c||(sprite.getX()+sprite.getWidth()>=a+((c-a)/2) && sprite.getX()+sprite.getWidth()<=c)){
								newXb=(float) (c+0.001);
								sprite.setPosition(newXb, newY);
								x= true;
							}else{
								newXb=(float) (a-sprite.getWidth()-0.001);
								sprite.setPosition(newXb, newY);
								x= true;
							}
						}
						//cas ou la bille rentre partiellement dans le mur a la verticale
						if(sprite.getX()>=a && sprite.getX()<=c && !y && !x){
							float m=Math.max(c-sprite.getX(),d-sprite.getY());
							if(sprite.getY()>=b && sprite.getY()<=d){
								if(m!=d-sprite.getX()){
									newXb=(float) (c+0.001);
									sprite.setPosition(newXb, newY);
									x= true;
								}else{
									newYb=(float) (d+0.001);
									sprite.setPosition(newX, newYb);
									y= true;
								}
							}else{
								if(Math.abs(aX)<=Math.abs(aY)){
									newYb=(float) (b-0.001-sprite.getHeight());
									sprite.setPosition(newX, newYb);
									y= true;
								}else{
									newXb=(float) (c+0.001);
									sprite.setPosition(newXb, newY);
									x= true;}
							}
						}
						if(sprite.getX()+sprite.getWidth()>=a && sprite.getX()+sprite.getWidth()<=c && !y && !x){
							float m2=Math.max(sprite.getX()+sprite.getWidth()-a,d-sprite.getY());
							if(sprite.getY()>=b && sprite.getY()<=d){
								if(m2!=d-sprite.getY()){
									newYb=(float) (d+0.001);
									sprite.setPosition(newX, newYb);
									y= true;
								}else{
									newXb=(float) (a-0.001-sprite.getWidth());
									sprite.setPosition(newXb, newY);
									x= true;
								}
							}else{
								if((aX)<=(aY)){
									newYb=(float) (b-0.001-sprite.getHeight());
									sprite.setPosition(newX, newYb);
									y= true;
								}else{
									newXb=(float) (a-0.001-sprite.getWidth());
									sprite.setPosition(newXb, newY);
									x= true;}
							}
						}
					}

				}else if(!curWorld && w.type != 0){
					//types rouges et noirs
					if(((sprite.getX()>=a && sprite.getX()<=c) || (sprite.getX()+sprite.getWidth()>=a && sprite.getX()+sprite.getWidth()<=c))
							&& ((sprite.getY()>=b && sprite.getY()<=d) || (sprite.getY()+sprite.getHeight()>=b && sprite.getY()+sprite.getHeight()<=d))){
						if((sprite.getX()>=a && sprite.getX()<=c)&&(sprite.getX()+sprite.getWidth()>=a && sprite.getX()+sprite.getWidth()<=c)){
							if((sprite.getY()>=b+((d-b)/2) && sprite.getY()<=d)||(sprite.getY()+sprite.getHeight()>=b+((d-b)/2) && sprite.getY()+sprite.getHeight()<=d)){
								newYb=(float) (d+0.001);
								sprite.setPosition(newX, newYb);
								y= true;
							}else{
								newYb=(float) (b-sprite.getHeight()-0.001);
								sprite.setPosition(newX, newYb);
								y= true;
							}
						}
						//cas ou la bille rentre entierement dans le mur a l'horizontale
						if((sprite.getY()>=b && sprite.getY()<=d)&&(sprite.getY()+sprite.getHeight()>=b && sprite.getY()+sprite.getHeight()<=d)){
							if(sprite.getX()>=a+((c-a)/2) && sprite.getX()<=c||(sprite.getX()+sprite.getWidth()>=a+((c-a)/2) && sprite.getX()+sprite.getWidth()<=c)){
								newXb=(float) (c+0.001);
								sprite.setPosition(newXb, newY);
								x= true;
							}else{
								newXb=(float) (a-sprite.getWidth()-0.001);
								sprite.setPosition(newXb, newY);
								x= true;
							}
						}
						//cas ou la bille rentre partiellement dans le mur a la verticale
						if(sprite.getX()>=a && sprite.getX()<=c && !y && !x){
							float m=Math.max(c-sprite.getX(),d-sprite.getY());
							if(sprite.getY()>=b && sprite.getY()<=d){
								if(m!=d-sprite.getX()){
									newXb=(float) (c+0.001);
									sprite.setPosition(newXb, newY);
									x= true;
								}else{
									newYb=(float) (d+0.001);
									sprite.setPosition(newX, newYb);
									y= true;
								}
							}else{
								if(Math.abs(aX)<=Math.abs(aY)){
									newYb=(float) (b-0.001-sprite.getHeight());
									sprite.setPosition(newX, newYb);
									y= true;
								}else{
									newXb=(float) (c+0.001);
									sprite.setPosition(newXb, newY);
									x= true;}
							}
						}
						if(sprite.getX()+sprite.getWidth()>=a && sprite.getX()+sprite.getWidth()<=c && !y && !x){
							float m2=Math.max(sprite.getX()+sprite.getWidth()-a,d-sprite.getY());
							if(sprite.getY()>=b && sprite.getY()<=d){
								if(m2!=d-sprite.getY()){
									newYb=(float) (d+0.001);
									sprite.setPosition(newX, newYb);
									y= true;
								}else{
									newXb=(float) (a-0.001-sprite.getWidth());
									sprite.setPosition(newXb, newY);
									x= true;
								}
							}else{
								if((aX)<=(aY)){
									newYb=(float) (b-0.001-sprite.getHeight());
									sprite.setPosition(newX, newYb);
									y= true;
								}else{
									newXb=(float) (a-0.001-sprite.getWidth());
									sprite.setPosition(newXb, newY);
									x= true;
								}
							}
						}
					}
				}
			}
			
			if(!y && !x){
				sprite.setPosition(newX, newY);
			}
		}
	}

	public boolean coLOL(float newX, float newY,float a,float b,float c,float d,float aX,float aY){
		boolean x=false,y=false;
		float newXb=0;
		float newYb=0;
		if(((sprite.getX()>=a && sprite.getX()<=c) || (sprite.getX()+sprite.getWidth()>=a && sprite.getX()+sprite.getWidth()<=c))
				&& ((sprite.getY()>=b && sprite.getY()<=d) || (sprite.getY()+sprite.getHeight()>=b && sprite.getY()+sprite.getHeight()<=d))){
			if((sprite.getX()>=a && sprite.getX()<=c)&&(sprite.getX()+sprite.getWidth()>=a && sprite.getX()+sprite.getWidth()<=c)){
				if((sprite.getY()>=b+((d-b)/2) && sprite.getY()<=d)||(sprite.getY()+sprite.getHeight()>=b+((d-b)/2) && sprite.getY()+sprite.getHeight()<=d)){
					newYb=(float) (d+0.001);
					sprite.setPosition(newX, newYb);
					return true;
				}else{
					newYb=(float) (b-sprite.getHeight()-0.001);
					sprite.setPosition(newX, newYb);
					return true;
				}
			}
			//cas ou la bille rentre entierement dans le mur a l'horizontale
			if((sprite.getY()>=b && sprite.getY()<=d)&&(sprite.getY()+sprite.getHeight()>=b && sprite.getY()+sprite.getHeight()<=d)){
				if(sprite.getX()>=a+((c-a)/2) && sprite.getX()<=c||(sprite.getX()+sprite.getWidth()>=a+((c-a)/2) && sprite.getX()+sprite.getWidth()<=c)){
					newXb=(float) (c+0.001);
					sprite.setPosition(newXb, newY);
					return true;
				}else{
					newXb=(float) (a-sprite.getWidth()-0.001);
					sprite.setPosition(newXb, newY);
					return true;
				}
			}
			//cas ou la bille rentre partiellement dans le mur a la verticale
			if(sprite.getX()>=a && sprite.getX()<=c && !y && !x){
				float m=Math.max(c-sprite.getX(),d-sprite.getY());
				if(sprite.getY()>=b && sprite.getY()<=d){
					if(m!=d-sprite.getX()){
						newXb=(float) (c+0.001);
						sprite.setPosition(newXb, newY);
						return true;
					}else{
						newYb=(float) (d+0.001);
						sprite.setPosition(newX, newYb);
						return true;
					}
				}else{
					if(Math.abs(aX)<=Math.abs(aY)){
						newYb=(float) (b-0.001-sprite.getHeight());
						sprite.setPosition(newX, newYb);
						return true;
					}else{
						newXb=(float) (c+0.001);
						sprite.setPosition(newXb, newY);
						return true;}
				}
			}
			if(sprite.getX()+sprite.getWidth()>=a && sprite.getX()+sprite.getWidth()<=c && !y && !x){
				float m2=Math.max(sprite.getX()+sprite.getWidth()-a,d-sprite.getY());
				if(sprite.getY()>=b && sprite.getY()<=d){
					if(m2!=d-sprite.getY()){
						newYb=(float) (d+0.001);
						sprite.setPosition(newX, newYb);
						return true;
					}else{
						newXb=(float) (a-0.001-sprite.getWidth());
						sprite.setPosition(newXb, newY);
						return true;
					}
				}else{
					if((aX)<=(aY)){
						newYb=(float) (b-0.001-sprite.getHeight());
						sprite.setPosition(newX, newYb);
						return true;
					}else{
						newXb=(float) (a-0.001-sprite.getWidth());
						sprite.setPosition(newXb, newY);
						return true;}
				}
			}
		}

		sprite.setPosition(newX, newY);
		return false;//(x||y);
	}

	public boolean winLOL(GoalZone goal) {
		if(((sprite.getX()>=goal.sprite.getX() && sprite.getX()<=goal.sprite.getX()+goal.sprite.getWidth())
				|| (sprite.getX()+sprite.getWidth()>=goal.sprite.getX() && sprite.getX()+sprite.getWidth()<=goal.sprite.getX()+goal.sprite.getWidth()))
				&& ((sprite.getY()>=goal.sprite.getY() && sprite.getY()<=goal.sprite.getY()+goal.sprite.getHeight())
						|| (sprite.getY()+sprite.getHeight()>=goal.sprite.getY() && sprite.getY()+sprite.getHeight()<=goal.sprite.getY()+goal.sprite.getHeight()))){
			return true;
		}
		return false;
	}
}
