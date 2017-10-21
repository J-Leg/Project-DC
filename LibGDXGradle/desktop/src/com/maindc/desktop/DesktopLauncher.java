package com.maindc.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.engine.desktop.DCGame;
import com.maindc.DC_Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		// Ensure height to width ratio is 0.6
		
		config.width = 1600;
		config.height = 960;
		config.title = "Project-DC";
		
		new LwjglApplication(new DCGame(), config);
	}
}
