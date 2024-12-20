package game.snake.ui.event;

import game.snake.ui.util.Page;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;

import java.io.IOException;


public class LeaveEvent extends Event {
    private static final long serialVersionUID = 5920060263369226837L;

	// -- Attributes -- \\
    public static final EventType<LeaveEvent> TYPE = new EventType<LeaveEvent>(ANY, "LeavePage");

    public final Page PAGE;

    // -- Constructors -- \\
    /**
     * Creates a {@code LeaveEvent} object with a {@code Page}
     * @param page the page to load to next
     */
    public LeaveEvent(Page page) {
        super(TYPE);
        PAGE = page;
    }
    /**
     * Creates a {@code LeaveEvent} object with a {@link Page} loaded from an fxml file
     * @param fxml the name of the fxml file
     * @throws IOException if an error occurs during loading
     * @throws NullPointerException if {@code fxml} is {@code null}
     */
    public LeaveEvent(String fxml) throws IOException, NullPointerException {
        super(TYPE);
        if (!fxml.contains(".fxml")) {fxml += ".fxml";}
        PAGE = FXMLLoader.load(getClass().getResource(fxml));;
    }
}
