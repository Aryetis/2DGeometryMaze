package com.projetandroid;


import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;


import com.projetandroid.R;



public class MainMenuScene extends MenuScene implements IOnMenuItemClickListener{

	BaseActivity activity;
	final int MENU_START = 0;
	final int MENU_EXIT = 9;

	public MainMenuScene() {
		//on récupère les éléments nécessaires à la création de la scene depuis l'instance de BaseACtivity
		super(BaseActivity.getSharedInstance().mCamera);
		activity = BaseActivity.getSharedInstance();

		// on personalise notre scène, y ajoute des boutons, du texte, change la couleur de fond d'écran
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		IMenuItem startButton = new TextMenuItem(MENU_START, activity.mFont, activity.getString(R.string.start), activity.getVertexBufferObjectManager());
		startButton.setPosition(mCamera.getWidth()/2 - startButton.getWidth()/2, mCamera.getHeight()/3 - startButton.getHeight()/2);
		addMenuItem(startButton);
		IMenuItem exitButton = new TextMenuItem(MENU_EXIT, activity.mFont, activity.getString(R.string.exit), activity.getVertexBufferObjectManager());
		exitButton.setPosition(mCamera.getWidth()/2 - exitButton.getWidth()/2, 2*(mCamera.getHeight()/3) - exitButton.getHeight()/2);
		addMenuItem(exitButton);


		setOnMenuItemClickListener(this);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1, float arg2, float arg3) {
		switch (arg1.getID()) {
		case MENU_START:
			activity.setCurrentScene(new LoadingLevelMenu());
			return true;

		case MENU_EXIT:
			activity.finish();

		default:
			break;
		}
		return false;
	}


}
