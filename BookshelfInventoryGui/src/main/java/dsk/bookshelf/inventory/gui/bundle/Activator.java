package dsk.bookshelf.inventory.gui.bundle;

import java.io.InputStream;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator extends Application implements BundleActivator {

    private ClassLoader classLoader;

    @Override
    public void start(Stage stage) throws Exception {
        if (classLoader == null) {
            classLoader = this.getClass().getClassLoader();
        }
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setClassLoader(classLoader);
        // TODO
        //loader.setResources(ResourceBundle.getBundle("fx_message"));
        try (InputStream is = classLoader.getResource("fxml/Scene.fxml").openConnection().getInputStream()) {
            fxmlLoader.load(is);
            Parent root = fxmlLoader.getRoot();
            Scene scene = new Scene(root);
            stage.setTitle("JavaFX and Maven");
            stage.setScene(scene);
            stage.show();
        }
    }

    @Override
    public void stop() {
        System.out.println("stop.");
    }

    public static void main(String[] args) {
        launch(args);
        System.out.println("exit");
    }

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.printf("START GUI.\n");
        launch(Activator.class);
        context.getBundle(0).stop();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.printf("STOP GUI.\n");
    }
}
