package game.snake.ui.util;

import game.snake.Game;
import game.snake.ui.event.InitializationEvent;
import game.snake.ui.event.LeaveEvent;
import game.snake.ui.exceptions.NullPageException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

/**
 * Base class for application pages
 * @author Aleksandr Stinchcomb
 */
public class Page extends Node {
    // -- Attributes -- \\
    //private final String RESOURCES = "resources/";
    private final String PAGES = "pages/"; //RESOURCES + "pages/";

    private Controller controller;

    private Window window;
    private Parent root;
    private String css;

    // -- Constructors -- \\
    /**
     * Creates a custom {@code Page} object
     * @return a {@code Page} object
     */
    public Page() {
        connectEvent();
    }
    /**
     * Creates a new page from a root node
     * @param root the root layout node of the page
     * @return a {@code Page} object
     */
    public Page(Parent root) {
        this.root = root;
        connectEvent();
    }
    /**
     * Creates a {@code Page} object from an fxml file
     * @param pageName the name of the fxml file
     * @return a {@code Page} object
     * @throws NullPageException if {@code pageName} doesn't find a fxml file
     */
    public Page(String pageName) throws NullPageException {
        pageName = pageName.replaceAll("\\..+", "");
        
        try {
            FXMLLoader loader = new FXMLLoader(getPath(PAGES+pageName+".fxml"));
            
            root = loader.load();
            controller = loader.getController();
            if (controller != null) {controller.page(this);}
        } catch (NullPointerException | IOException exception) {
            exception.printStackTrace();
            throw new NullPageException(String.format("Missing fxml file '%s.fxml'", pageName));
        }
        
        try {
            css = getPath(PAGES+pageName+".css").toExternalForm();
        } catch (NullPointerException exception) {
            css = "";
        }

        connectEvent();
    }
    /**
     * Creates a {@code Page} object from an fxml file inside of {@code pane}
     * @param pageName the name of the fxml file
     * @param pane the pane to display the page in
     * @return a {@code Page} object
     * @throws NullPageException if {@code pageName} doesn't find a fxml file
     */    
    public Page(String pageName, Pane pane) throws NullPageException {
        this(pageName);
        pane.getStylesheets().add(css);
        pane.getChildren().add(root);
    }
    

    // -- Methods -- \\
    protected void connectEvent() {
        addEventHandler(InitializationEvent.TYPE, event -> {
            window = event.WINDOW;
            event.consume();
        });
    }

    /**
     * Finds a file and returns a {@link URL} object containing the path of the file
     * @param path the path to a file in the file directory
     * @return the {@link URL} of the file
     * @throws NullPointerException if a file can't be found from the {@code path}
     */
    private URL getPath(String path) throws NullPointerException {
        return Game.class.getResource(path);
    }

    /**
     * Returns the {@code Window} the page is in
     * @return the {@link Window} object the page is linked to
     */
    public Window window() {
        return window;
    }

    /**
     * Returns the root {@link Node}
     * @return the root layout {@link Node} of the page
     */
    public Parent root() {
        return root;
    }

    /**
     * Returns the css code for displaying the {@code Page}
     * @return a String 
     */
    public String css() {
        return css;
    }

    /**
     * Returns the object that controls the page
     * @return the page controller object
     */
    public Controller controller() {
        return controller;
    }

    /**
     * Returns a boolean indicating if changes can be made in the page
     * @return a boolean of if the page is locked or not
     */
    public boolean isLocked() {
        return controller.locked();
    }

    /**
     * Locks/Unlocks the page
     * @param locked a boolean indicating whether to lock the page<p>{@code true}: locks the page so no changes can be made to/in the page<p>{@code false}: unlocks the page so that changes can be made to/in the page
     */
    public void setLocked(boolean locked) {
        controller.locked(locked);
    }

    /**
     * Fires a {@link LeaveEvent} to exit and load a new {@code Page}
     * @param page the {@code Page} to load
     */
    public void load(Page page) {
        if (window != null) {
            page.fireEvent(new InitializationEvent(window));
            window.fireEvent(new LeaveEvent(page));
        } else if (root != null && (root instanceof Pane)) {
            load(page, (Pane)root);
        }
    }
    /**
     * Fires a {@link LeaveEvent} to exit and load a new {@code Page} from the given file {@code pageName}
     * @param pageName the name of the file to load a {@code Page} from
     * @throws NullPageException if {@code pageName} doesn't find an fxml file
     */
    public void load(String pageName) throws NullPageException {
        load(new Page(pageName));
    }
    /**
     * Sets the content in {@code pane} to the content in {@code page}
     * @param page the {@code Page} to load
     * @param pane the pane to load {@code page} into
     */
    public void load(Page page, Pane pane) {
        pane.getStylesheets().setAll(page.css());
        pane.getChildren().setAll(page.root());
    }
    /**
     * Sets the content in {@code pane} to the content in a {@code Page} loaded from {@code pageName}
     * @param pageName the name of the file to load a {@code Page} from
     * @param pane the pane to load {@code page} into
     * @throws NullPageException if {@code pageName} doesn't find an fxml file
     */
    public void load(String pageName, Pane pane) throws NullPageException {
        load(new Page(pageName), pane);
    }

    /**
     * Adds the content in {@code pane} to the content in {@code page}
     * @param page the {@code Page} to load
     * @param pane the pane to load {@code page} into
     */
    public void add(Page page, Pane pane) {
        pane.getStylesheets().add(page.css());
        pane.getChildren().add(page.root());
    }
    /**
     * Adds the content in {@code pane} to the content in a {@code Page} loaded from {@code pageName}
     * @param pageName the name of the file to load a {@code Page} from
     * @param pane the pane to load {@code page} into
     * @throws NullPageException if {@code pageName} doesn't find an fxml file
     */
    public void add(String pageName, Pane pane) throws NullPageException {
        add(new Page(pageName), pane);
    }

    @Override
    public String toString() {
        return String.format("%s[window: %s, parent: %s, css: %s]", getClass().getName(), window != null, root != null, css != "");
    }
}
