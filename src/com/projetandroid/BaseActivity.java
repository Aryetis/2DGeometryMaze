package com.projetandroid;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.util.ArrayList;
import android.graphics.Typeface;


/*
 * Unique activit� du programme, sert de r�ceptacle aux sc�nes de andEngine  
 */

public class BaseActivity extends SimpleBaseGameActivity {

	static final int CAMERA_WIDTH = 800;
	static final int CAMERA_HEIGHT = 480;

	public Font mFont;
	public Camera mCamera;
	public ArrayList<String> avaibleMaps;
	 
	//A reference to the current scene
	public Scene mCurrentScene;
	public static BaseActivity instance;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
	    instance = this;
	    mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
	 
	    return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	}

	@Override
	protected void onCreateResources() {
	    mFont = FontFactory.create(this.getFontManager(),this.getTextureManager(), 256, 256,Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
	    mFont.load();
	    avaibleMaps = new ArrayList<String>();
	}

	@Override
	protected Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		mCurrentScene = new SplashScene();
		return mCurrentScene;
	}
	
	public static BaseActivity getSharedInstance() {
	    return instance;
	}
	 
	// to change the current main scene
	public void setCurrentScene(Scene scene) {
	    mCurrentScene = scene;
	    getEngine().setScene(mCurrentScene);
	}
	

    @Override
    public void onDestroy() {
            super.onDestroy();
            if (mCurrentScene != null) {
                    System.exit(0);
            }
    }
}
