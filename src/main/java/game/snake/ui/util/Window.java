package game.snake.ui.util;

import game.snake.ui.event.InitializationEvent;
import game.snake.ui.event.LeaveEvent;
import game.snake.ui.exceptions.NullPageException;
import game.snake.utility.Vector2;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.HashMap;


/**
 * A class I created for abstracting and simplifying application windows & pages
 * @author Aleksandr Stinchcomb
 * @see Stage
 */
public class Window {
    // -- Attributes -- \\
    private static final Vector2 MAX_WINDOW_SIZE = new Vector2(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());

    private final HashMap<String, Page> PAGES = new HashMap<String, Page>();

    private Page currentPage;
    
    public final Stage STAGE;
    public final Scene SCENE;

    // -- Constructors -- \\
    /**Creates a blank window*/
    public Window() {
        STAGE = new Stage();
        SCENE = new Scene(new StackPane(), MAX_WINDOW_SIZE.X, MAX_WINDOW_SIZE.Y);

        STAGE.setScene(SCENE);
        setSize(MAX_WINDOW_SIZE, Vector2.ZERO, MAX_WINDOW_SIZE);
        setResizable(true);
        connectEvents();
    }
    /**
     * Creates a blank window with defined size
     * @param size the size to set the window to
     */
    public Window(Vector2 size) {
        STAGE = new Stage();
        SCENE = new Scene(new StackPane(), MAX_WINDOW_SIZE.X, MAX_WINDOW_SIZE.Y);
        
        STAGE.setScene(SCENE);
        setSize(size, Vector2.ZERO, MAX_WINDOW_SIZE);
        setResizable(true);
        connectEvents();
    }
    /**
     * Creates a blank window from {@code stage} with the given {@code size}
     * @param stage the stage container of the window
     * @param size the size to set the window to
     */
    public Window(Stage stage, Vector2 size) {
        STAGE = stage;
        SCENE = new Scene(new StackPane(), MAX_WINDOW_SIZE.X, MAX_WINDOW_SIZE.Y);
        
        STAGE.setScene(SCENE);
        setSize(size, Vector2.ZERO, MAX_WINDOW_SIZE);
        setResizable(true);
        connectEvents();
    }
    /**
     * Creates a blank window from {@code stage} with the given {@code size} on the given {@code page}
     * @param stage the stage container of the window
     * @param page the application page
     * @param size the size to set the window to
     */
    public Window(Stage stage, Page page, Vector2 size) {
        //LEAVE_HANDLER = newHandler();
        
        STAGE = stage;
        SCENE = new Scene(page.root(), MAX_WINDOW_SIZE.X, MAX_WINDOW_SIZE.Y);
        currentPage = page;
        
        STAGE.setScene(SCENE);
        setSize(size, Vector2.ZERO, MAX_WINDOW_SIZE);
        setResizable(true);
        setPage(page);
        connectEvents();
    }

	// -- Methods -- \\
    private void connectEvents() {
        STAGE.addEventHandler(LeaveEvent.TYPE, event -> {
            Page page = event.PAGE;
            
            SCENE.setRoot(page.root());
            currentPage = page;
        });

        STAGE.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
            if (currentPage != null) {
                Controller controller = currentPage.controller();

                if (controller != null) {
                    controller.onCloseRequest();//perform the operation when the window is closing
                }
            }
        });
    }

    public boolean visible() {
        return STAGE.isShowing();
    }

    public boolean focused() {
        return STAGE.isFocused();
    }

    public boolean resizable() {
        return STAGE.isResizable();
    }
    public void setResizable(boolean resizable) {
        STAGE.setResizable(resizable);
    }

    public void show() {
        if (STAGE.isIconified()) {
            STAGE.setIconified(false);
        }
        STAGE.show();
    }

    public void hide() {
        STAGE.hide();
    }
    
    public void close() {
    	STAGE.close();
    }

    public Vector2 getSize() {
        return new Vector2(STAGE.getWidth(), STAGE.getHeight());
    }

    public Vector2 getMinSize() {
        return new Vector2(STAGE.getMinWidth(), STAGE.getMinHeight());
    }

    public Vector2 getMaxSize() {
        return new Vector2(STAGE.getMaxWidth(), STAGE.getMaxHeight());
    }

    public void setSize(Vector2 size) {
        STAGE.setWidth(Math.min(Math.max(size.X, STAGE.getMinWidth()), STAGE.getMaxWidth()));
        STAGE.setHeight(Math.min(Math.max(size.Y, STAGE.getMinHeight()), STAGE.getMaxHeight()));
    }
    public void setSize(Vector2 minSize, Vector2 maxSize) {
        STAGE.setMinWidth(minSize.X);
        STAGE.setMinHeight(minSize.Y);

        STAGE.setMaxWidth(maxSize.X);
        STAGE.setMaxHeight(maxSize.Y);
    }
    public void setSize(Vector2 size, Vector2 minSize, Vector2 maxSize) {
        setSize(minSize, maxSize);
        setSize(size);
    }

    public void setMinSize(Vector2 minSize) {
        STAGE.setMinWidth(minSize.X);
        STAGE.setMinHeight(minSize.Y);
    }

    public void setMaxSize(Vector2 maxSize) {
        STAGE.setMinWidth(maxSize.X);
        STAGE.setMinHeight(maxSize.Y);
    }

    public void minimize() {
        STAGE.setIconified(true);
    }

    public void maximize() {
        STAGE.setMaximized(true);
    }

    public void setTransparency(double transparency) {
        STAGE.setOpacity(1 - transparency);
    }

    public void setTitle(String title) {
        STAGE.setTitle(title);
    }
    
    public void setIcon(Image... image) {
    	STAGE.getIcons().setAll(image);
    }
    
    public void addPage(String name, Page page) {
        PAGES.put(name, page);
    }

    public Page getPage(String name) throws NullPageException {
        Page page = PAGES.get(name);
        if (page == null) {
            throw new NullPageException("Error 404: Page '%s' not found");
        } else {
            return page;
        }
    }

    public void setPage(Page page) {
        if (currentPage != null) {
            currentPage.load(page);
        } else {
            SCENE.setRoot(page.root());
        }

        currentPage = page;
        page.fireEvent(new InitializationEvent(this));
    }
    public void setPage(String name) throws NullPageException {
        setPage(getPage(name));
    }

    public void fireEvent(Event event) {
        STAGE.fireEvent(event);
    }

    @Override
    public String toString() {
        return String.format("%s[page: '%s', size: '%s']", getClass().getName(), currentPage, new Vector2(STAGE.getWidth(), STAGE.getHeight()));
    }
}