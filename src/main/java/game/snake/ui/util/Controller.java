package game.snake.ui.util;

public abstract class Controller {
    // -- Attributes -- \\
    private Page page;
    private boolean locked = false;

    // -- Methods -- \\
    /**
     * Getter for the {@code page} attribute
     * @return the {@link Page} object the controller is linked to
     */
    public Page page() {
        return page;
    }

    /**
     * Setter for the {@code page} attribute
     * @param page the {@link Page} object the controller is linked to
     */
    public void page(Page page) {
        this.page = page;
    }

    /**
     * Getter for the {@code locked} attribute
     * @return a boolean indicating if changes can be made to the page
     */
    public boolean locked() {
        return locked;
    }

    /**
     * Setter for the {@code locked} attribute
     * @param lock a boolean indicating if the page is to be locked
     */
    public void locked(boolean lock) {
        this.locked = lock;
    }

    @Override
    public String toString() {
        return String.format("%s[page: %s]", getClass().getName(), page != null);
    }

    /**Initializes the page*/
    public void initialize() {}

    /**Performs the given actions when the window is closed*/
    public void onCloseRequest() {}

    /**Updates the page*/
    public void update() {}
    /**Updates the page with the given value(s)*/
    public <T> void update(T value) {}
}