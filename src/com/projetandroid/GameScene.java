package com.projetandroid;


import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.BaseGameActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;



public class GameScene extends Scene implements IOnSceneTouchListener{

	static boolean curWorld = true ; //true=affiche les walls type vert(type=0). false=rouge(type=1)
	public Bille bille; //blanc
	public Maze maze;
	public GoalZone goal; //violet
	Camera mCamera;
	float accelerometerSpeedX;
	float accelerometerSpeedY;
	static BaseActivity BAInstance;
	int nbSwitch=0, count=00; //count<=>timer
	BaseActivity activity = BaseActivity.getSharedInstance();
	Text t = new Text(30, 20, activity.mFont, "LET S START !!!",activity.getVertexBufferObjectManager());
	Text t2 = new Text(620, 20, activity.mFont, "Switch : 0",activity.getVertexBufferObjectManager());
    	
	
	public GameScene() {
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		mCamera = BaseActivity.getSharedInstance().mCamera;
		//mCamera.setChaseEntity(bille.sprite); focus la caméra sur la bille mais pas limite du terrain. marrant :)

		attachChild(t);
		attachChild(t2);

		BaseActivity.getSharedInstance().setCurrentScene(this);
		SensorManager sensorManager = (SensorManager) BaseActivity.getSharedInstance()
				.getSystemService(BaseGameActivity.SENSOR_SERVICE);
		SensorListener.getSharedInstance();
		sensorManager.registerListener(SensorListener.getSharedInstance(),
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME);

		createSpriteSpawnTimeHandler();
		setOnSceneTouchListener(this);
		registerUpdateHandler(new GameLoopUpdateHandler());
	}

	public GameScene(Maze deser) {
		this() ;
		
		//initialisation du maze (des walls)
	    maze = Maze.getSharedInstance(); 
	    maze = deser;
	    maze.InitializeWalls();
	    mainFor:
	    for(Wall element: maze.Walls){
	    	if ( element.type == 0 && curWorld )
		    	{ attachChild(element.sprite); continue mainFor; }
	    	if ( element.type == 1 && !curWorld )	
		    	{ attachChild(element.sprite); continue mainFor; }
	    	if ( element.type != 0 && element.type != 1 )
	    	attachChild(element.sprite);
	    }	
	    //initialisation de la bille et de goalZone
		bille = Bille.getSharedInstance(maze.xStart, maze.yStart);
		attachChild(bille.sprite);
	    goal = GoalZone.getSharedInstance(maze.xEnd, maze.yEnd);
	    attachChild(goal.sprite);
	}

	public void moveBille() {
        bille.moveBille(accelerometerSpeedX,accelerometerSpeedY,maze.Walls,curWorld);
		if (bille.winLOL(goal))
        {
            activity.setCurrentScene(new HighscoreScene(count, nbSwitch)); 
        }
	}


	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
	    if (pSceneTouchEvent.isActionDown())
		{
	    	//si l'on touche l'écran, basculer d'un monde à l'autre
	    	// <=> faire apparaitre(attach) les murs verts 
	    	//et disparaitre(detach) les murs rouges, ou vice versa
	    	curWorld = !curWorld;
			nbSwitch++;
			t2.setText("Switch : "+String.valueOf(nbSwitch));
			mainFor:
			for(Wall element: maze.Walls){
				if ( element.type == 0 )
					if (!curWorld )
			    	{ detachChild(element.sprite); continue mainFor; }
					else
					{attachChild(element.sprite); continue mainFor; }
				if ( element.type == 1)	
					if (curWorld)
			    	{ detachChild(element.sprite); continue mainFor; }
					else
					{attachChild(element.sprite); continue mainFor; }
			}	
		}
		return false;
	}

	private void createSpriteSpawnTimeHandler(){
		//simple fonction de timer
		float mEffectSpawnDelay = 1f;
		ITimerCallback test = new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				count++;
				t.setText("Temps : "+String.valueOf(count));  
				if(count==0){
					unregisterUpdateHandler(pTimerHandler);
				}        
				pTimerHandler.reset();
			}
		};
		TimerHandler spriteTimerHandler = new TimerHandler(mEffectSpawnDelay, true,test);

		mCamera.registerUpdateHandler(spriteTimerHandler);
	}

}