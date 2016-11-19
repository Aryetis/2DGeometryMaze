package com.projetandroid;

import org.andengine.engine.handler.IUpdateHandler;

public class GameLoopUpdateHandler implements IUpdateHandler{

	@Override
	public void onUpdate(float arg0) {
		((GameScene)BaseActivity.getSharedInstance().mCurrentScene).moveBille();
	}

	@Override
	public void reset() {
		// Auto-generated method stub
	}

}
