package GRIME;

import java.awt.event.KeyEvent;

import static org.lwjgl.opengl.GL11.glClearColor;

public class LevelEditorScene extends Scene {
    private boolean changingScene = false;
    private float timeToChangeScene = 2.0f;

    public LevelEditorScene() {
        System.out.println("Inside level editor Scene");
    }

    @Override
    public void update(float dt) {

        System.out.println((1.0f/dt)+"FPS");

        if (!changingScene && KeyListener.isKeyDown(KeyEvent.VK_SPACE)) {
            changingScene = true;
        }

        if(changingScene && timeToChangeScene > 0) {
            timeToChangeScene -= dt;

        } else if (changingScene) {
            WindowHandler.changeScene(1);
        }

    }
}
