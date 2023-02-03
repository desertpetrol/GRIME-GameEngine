package GRIME;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    int width, height;
    String title;
    private long appWindow;
    private static Window window = null;
    private Window() {
        this.width = 1360;
        this.height = 768;
        this.title = "GRIME";
    }
    public  static Window get() {
    if (Window.window == null) {
        Window.window = new Window();
        }

    return Window.window;
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
    }


    public void loop() {
        while (!glfwWindowShouldClose(appWindow)) {
            //Poll events
            glfwPollEvents();

            glClearColor(0.25f,0.0f,0.25f,1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(appWindow);
        }

    }

}
