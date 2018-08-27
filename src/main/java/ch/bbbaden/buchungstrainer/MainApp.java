package ch.bbbaden.buchungstrainer;

import ch.bbbaden.buchungstrainer.util.FXMLUtil;
import ch.bbbaden.buchungstrainer.util.Settings;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Settings.createApplicationSettings();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLUtil.CHOICE));
        FXMLUtil.changeScene(stage, loader);

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}
