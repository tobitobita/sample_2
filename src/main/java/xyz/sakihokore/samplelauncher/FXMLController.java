package xyz.sakihokore.samplelauncher;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;

public class FXMLController implements Initializable {

    @FXML
    private WebView webView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webView.getEngine().load("http://astah.change-vision.com/ja/");
    }

    public void handleButtonAction(ActionEvent event) {
        System.out.println(event.getSource());
        final Button btn = (Button) event.getSource();
        final Optional<String> appPath;
        switch (btn.getId()) {
            case "word":
                appPath = Optional.of("/Applications/Microsoft Word.app");
                break;
            case "excel":
                appPath = Optional.of("/Applications/Microsoft Excel.app");
                break;
            default:
                System.out.println(event);
                appPath = Optional.empty();
                break;
        }
        if (!appPath.isPresent()) {
            return;
        }
        final ProcessBuilder pb = new ProcessBuilder("open", appPath.get());
        try {
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
