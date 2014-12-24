package dsk.samplejavafx8;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainWindowController implements Initializable {

    private Scene subScene;

    @FXML
    private MenuBar mainMenu;

    @FXML
    private MenuItem closeMenu;

    @FXML
    private Label label;

    @FXML
    private void closeAction(ActionEvent event) {
        System.out.println("closeAction");
        Platform.exit();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");

        Stage stage = new Stage();
        stage.setTitle("JavaFX and Maven");
        stage.setScene(this.subScene);
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainMenu.setUseSystemMenuBar(true);
        try {
            this.subScene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/SubWindow.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
