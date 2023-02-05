package GRIME;

import Util.Time;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class WindowHandler {
    int width, height;
    String title;
    private long appWindow;
    private static WindowHandler windowHandler = null;
    private static Scene currentScene;

    private WindowHandler() {
        this.width = 1360;
        this.height = 768;
        this.title = "GRIME";
    }

    public static void changeScene (int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                //currentScene.init();
                break;
            case 1:
                currentScene = new LevelScene();
                break;
            default:
                assert false : "Unknown scene " + newScene;
        }
    }

    public  static WindowHandler get() {
    if (WindowHandler.windowHandler == null) {
        WindowHandler.windowHandler = new WindowHandler();
        }

    return WindowHandler.windowHandler;
    }

    public void run() {
        System.out.printf("Hello LWJGL %s", Version.getVersion());
        init();
        loop();

        //free the memory
        glfwFreeCallbacks(appWindow);
        glfwDestroyWindow(appWindow);

        //terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        //set error callback
        GLFWErrorCallback.createPrint(System.err).set();

        //Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW.");
        }
        //Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE,GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED,GLFW_TRUE);

        //Create Window
        appWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        System.out.println(appWindow);
        if (appWindow == NULL) {
            throw new IllegalStateException("Failed to create App Window");
        }

        //Adding input listeners to window
        glfwSetCursorPosCallback(appWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(appWindow, MouseListener::mouseBtnCallback);
        glfwSetScrollCallback(appWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(appWindow, KeyListener::keyCallback);


        //Make the OpenGl the context current
        glfwMakeContextCurrent(appWindow);
        //Enable vsync
        glfwSwapInterval(1);

        //make the window visible
        glfwShowWindow(appWindow);
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        WindowHandler.changeScene(0);
    }


    public void loop() {
        float beginTime = Time.getTime();
        float endTime;
        float dt = -1.0f;


        while (!glfwWindowShouldClose(appWindow)) {

            //Poll events
            glfwPollEvents();

            glClearColor(0.25f,0.0f,0.25f,1.0f);
            glClear(GL_COLOR_BUFFER_BIT);


           if (KeyListener.isKeyDown(GLFW_KEY_SPACE)) {
               System.out.println("space key is pressed");
           }
           if (MouseListener.mouseButtonIsDown(GLFW_MOUSE_BUTTON_1)){
               System.out.println("m1 is pressed");
           }
            if (dt >= 0) {
                currentScene.update(dt);
            }

            glfwSwapBuffers(appWindow);

            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;

        }
    }
}
