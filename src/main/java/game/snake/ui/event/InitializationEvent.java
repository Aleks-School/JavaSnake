package game.snake.ui.event;

import game.snake.ui.util.Window;
import javafx.event.Event;
import javafx.event.EventType;


public class InitializationEvent extends Event {
    private static final long serialVersionUID = -2592733766592529685L;

	// -- Attributes -- \\
    public static final EventType<InitializationEvent> TYPE = new EventType<InitializationEvent>(ANY, "LoadPage");

    public final Window WINDOW;

    // -- Constructors -- \\
    /**
     * Creates a {@code InitializationEvent} object with a {@code Page}
     * @param window the window for the page to load in
     */
    public InitializationEvent(Window window) {
        super(TYPE);
        WINDOW = window;
    }
}
