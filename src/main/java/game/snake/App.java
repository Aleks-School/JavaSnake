package game.snake;

import game.snake.game.Settings;
import game.snake.ui.util.Page;
import game.snake.ui.util.Window;
import game.snake.utility.Vector2;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

/**JavaFX App*/
public class App extends Application {
    private class FontLoader {
        // -- Attributes -- \\
        public final URL[] FONT_FILES;

        // -- Constructors -- \\
        /**
         * Creates a {@code FontLoader} object to load the font files in the given file
         * @param fontFilePath the relative path to the font {@link File}
         * @throws URISyntaxException if {@code fontFilePath} yields an invalid {@link URI}
         * @throws NullPointerException if {@code fontFilePath} is {@code null}
         */
        public FontLoader(String fontFilePath) throws URISyntaxException {
            if (getResource(fontFilePath) != null) {
                ArrayList<URL> files = new ArrayList<URL>();

                addFiles(files, fontFilePath+"/any", GENERAL_FONTS);
                addFiles(files, fontFilePath+"/"+getOS(), SYSTEM_FONTS);

                FONT_FILES = files.toArray(new URL[files.size()]);
            } else {
                FONT_FILES = new URL[0];
            }
        }

        // -- Methods -- \\
        private String getOS() {
            final String OS = System.getProperty("os.name").toLowerCase();

            if ("The Android Project".equals(System.getProperty("java.specification.vendor"))) {
                return "adr";
            } else if (OS.contains("mac")) {
                return "mac";
            } else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix") || OS.contains("sunos")) {
                return "lin";
            } else {
                return "win";
            }
        }

        /**
         * Finds a {@link URL} from {@code filePath}
         * @param filePath the path to the file
         * @return a {@link URL} originating at {@code filePath}
         * @throws NullPointerException if {@code filePath} is {@code null}
         */
        private URL getResource(String filePath) {
            return App.class.getResource(filePath);
        }

        /**
         * Returns the font files in {@code file}
         * @param files the {@link ArrayList} of files to add the font tiles from {@code file} to
         * @param folder the {@link File} to get the font files from
         * @param fontFiles an array of {@link Font} {@code File}s to add to the existing fonts
         */
        private void addFiles(ArrayList<URL> files, String folder, String[] fontFiles) {
            for (String fontFile : fontFiles) {
                URL font = getResource(folder+"/"+fontFile);
                if (font != null) {files.add(font);}
            }
        }

        /*public boolean loadFont(String fontName) {
            for (File file : FONT_FILES) {
                if (file.getName().equals(fontName)) {
                    boolean success = true;
                    try {Font.loadFont(file.toURI().toURL().toExternalForm().replaceAll("%20", " "), 0);} catch (MalformedURLException e) {success = false;}
                    return success;
                }
            }
            return false;
        }*/

        public void loadFonts() {
            for (URL file : FONT_FILES) {
                Font.loadFont(file.toExternalForm().replaceAll("%20", " "), 0);
            }
        }
    }

    private static final String[] GENERAL_FONTS = {"StarJedi.ttf","Minecraftia.ttf"};
    private static final String[] SYSTEM_FONTS = {"Cambria.ttf"};

    @SuppressWarnings("exports")
    public static final BooleanProperty IS_LOADED = new SimpleBooleanProperty(false);


    @SuppressWarnings("exports")
    @Override public void start(Stage primaryStage) throws Exception {
        new FontLoader("fonts").loadFonts();

        Window window = new Window(primaryStage, new Vector2(Settings.MAX_CELLS, Settings.MAX_CELLS));
        Page main = new Page("Settings");

        window.setTitle("Snake");
        //window.setIcon(new Image(App.class.getResourceAsStream("images/icon32.png")), new Image(App.class.getResourceAsStream("images/icon48.png")), new Image(App.class.getResourceAsStream("images/icon64.png")));
        window.setPage(main);
        window.show();
    }

    public static void main(String[] args) {launch(args);}
}