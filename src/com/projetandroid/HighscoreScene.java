package com.projetandroid;

import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.text.Text;

import com.projetandroid.R;


public class HighscoreScene extends MenuScene implements IOnMenuItemClickListener {

	static BaseActivity activity;
	public Maze maze;
	int curScorePosition;


	public HighscoreScene(){
		super(BaseActivity.getSharedInstance().mCamera);
		setBackground(new Background(0.4f, 0.2f, 0.1f));
		mCamera = BaseActivity.getSharedInstance().mCamera;

		//initialisation des variables
		activity = BaseActivity.getSharedInstance();
		maze = Maze.getSharedInstance();
		
		//ajout du bouton retour menu principal
		IMenuItem backMainMenu = new TextMenuItem(0, activity.mFont, activity.getString(R.string.backToMainMenu), activity.getVertexBufferObjectManager());
		backMainMenu.setPosition(mCamera.getWidth()/2 - backMainMenu.getWidth()/2, 7*(mCamera.getHeight()/8) - backMainMenu.getHeight()/2);
		addMenuItem(backMainMenu);
		
		setOnMenuItemClickListener(this);
	}
	public HighscoreScene(int count, int nbSwitch){
		this();
		curScorePosition = maze.addHighScore(count, nbSwitch)+1;//penser a sérializer le fichier pour enregistrer le score
															    //addHighScore renvoit la position du score ajoutée en index
																//(rajouter +1 pour les humains)
		
		// création des éléments (score courant, top3 des scores, bouton de retour au menu principal,
		// et affichages de ceux ci sur la scène
		int scoreNbr = maze.highscores.size();
		HighScore HighScore1 = maze.highscores.get(0);		
		
		Text NHS = new Text(0, 0,
							activity.mFont, "your score is ranked "+curScorePosition+" with \n temps="+count+" nbSwitch="+nbSwitch ,
							activity.getVertexBufferObjectManager());
		NHS.setPosition(mCamera.getWidth()/2 - NHS.getWidth()/2,  (mCamera.getHeight()/8) - NHS.getHeight()/2 );
		Text HS1 = new Text(0, 0,
							activity.mFont, "1st score, temps="+HighScore1.timer+" nbSwitch="+HighScore1.nbSwitch,
							activity.getVertexBufferObjectManager());
		HS1.setPosition(mCamera.getWidth()/2 - NHS.getWidth()/2,  3*(mCamera.getHeight()/8) - NHS.getHeight()/2 );
		if ( scoreNbr >= 2 )
		{
			HighScore HighScore2 = maze.highscores.get(1);
			Text HS2 = new Text(0, 0,
								activity.mFont, "2nd score, temps="+HighScore2.timer+" nbSwitch="+HighScore2.nbSwitch,
								activity.getVertexBufferObjectManager());
			HS2.setPosition(mCamera.getWidth()/2 - NHS.getWidth()/2,  4*(mCamera.getHeight()/8) - NHS.getHeight()/2 );
			attachChild(HS2);
		}
		if ( scoreNbr >= 3 )
		{
			HighScore HighScore3 = maze.highscores.get(2);
			Text HS3 = new Text(0, 0,
								activity.mFont, "3rd score, temps="+HighScore3.timer+" nbSwitch="+HighScore3.nbSwitch,
								activity.getVertexBufferObjectManager());
			HS3.setPosition(mCamera.getWidth()/2 - NHS.getWidth()/2,  5*(mCamera.getHeight()/8) - NHS.getHeight()/2 );
			attachChild(HS3) ;
		}
		attachChild(NHS); attachChild(HS1);  
		
		
		maze.serializeMaze();
		
		
//finshiHim, pas finalize car sinon elle est appelée par le gc avant et du coup fait n'importe quoi ....
Bille.getSharedInstance().finishHim();
GoalZone.getSharedInstance().finishHim();
	}
	

	@Override
	public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1, float arg2, float arg3) {
    	activity.setCurrentScene(new MainMenuScene());
	    return false;
	}
	
}
