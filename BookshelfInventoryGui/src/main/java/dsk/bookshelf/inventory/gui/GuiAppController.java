package dsk.bookshelf.inventory.gui;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class GuiAppController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private TabPane tabs;

    @FXML
    protected void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @FXML
    protected void handleExitButtonAction(ActionEvent event) {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setClassLoader(classLoader);
        // TODO
        //loader.setResources(ResourceBundle.getBundle("fx_message"));
        try (InputStream is = classLoader.getResource("fxml/LoadedScene.fxml").openConnection().getInputStream()) {
            Tab t = new Tab("追加１");
            t.setContent(fxmlLoader.load(is));
            this.tabs.getTabs().addAll(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
