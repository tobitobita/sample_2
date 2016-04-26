package xyz.sakihokore.samplelauncher;

import com.sun.jna.Native;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Psapi;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import static com.sun.jna.platform.win32.WinNT.PROCESS_QUERY_INFORMATION;
import static com.sun.jna.platform.win32.WinNT.PROCESS_VM_READ;
import com.sun.jna.ptr.IntByReference;
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

    private static final User32 USER32 = User32.INSTANCE;
    private static final Psapi PSAPI = Psapi.INSTANCE;
    private static final Kernel32 KERNEL32 = Kernel32.INSTANCE;

    @FXML
    private WebView webView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        webView.getEngine().load("http://astah.change-vision.com/ja/");
    }

    public void handleButtonAction(ActionEvent event) {
        System.out.println(event.getSource());
        final Button btn = (Button) event.getSource();
        final Optional<String> appPath;
        // TODO css（fx）のidなので、これでよいのか再考する。
        switch (btn.getId()) {
            case "word":
                appPath = Optional.of("C:\\Program Files (x86)\\Microsoft Office\\Office14\\WINWORD.EXE");
                break;
            case "excel":
                appPath = Optional.of("C:\\Program Files (x86)\\Microsoft Office\\Office14\\EXCEL.EXE");
                break;
            case "pro":
                appPath = Optional.of("C:\\Program Files\\astah-professional\\astah-pro.exe");
                break;
            case "sysml":
                appPath = Optional.of("C:\\Program Files\\astah-sysml\\astah-sys.exe");
                break;
            case "gsn":
                appPath = Optional.of("C:\\Program Files\\astah-gsn\\astah-gsn.exe");
                break;
            default:
                System.out.println(event);
                appPath = Optional.empty();
                break;
        }
        if (!appPath.isPresent()) {
            return;
        }
        final ProcessBuilder pb = new ProcessBuilder(appPath.get());
        try {
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleLayoutAction(ActionEvent event) {
        System.out.println("xyz.sakihokore.samplelauncher.FXMLController.handleLayoutAction()");
        USER32.EnumWindows((hWnd, arg) -> {
            if (!USER32.IsWindowVisible(hWnd)) {
                return true;
            }
            final String title = getWindowText(hWnd);
            final String className = getClassName(hWnd);
            final IntByReference processId = getWindowThreadProcessId(hWnd);
            final HANDLE hProcess = KERNEL32.OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, false, processId.getValue());
            final String fileName = getModuleFileNameEx(hProcess, null);
            System.out.printf("title:%s, className:%s, fileName:%s\n", title, className, fileName);
            if (title.indexOf("NetBeans") > 0) {
                System.out.println("moveWindow");
                //DLL.MoveWindow(hWnd, 100, 100, 320, 240, true);
            }
            return true;
        }, null);
    }

    private static final int BUF_SIZE = 1024;
    private static final int BUF_CHAR_SIZE = BUF_SIZE * 2;

    private static WString L(String str) {
        return new WString(str);
    }

    private static String getWindowText(WinDef.HWND hWnd) {
        final char[] buf = new char[BUF_CHAR_SIZE];
        USER32.GetWindowText(hWnd, buf, BUF_SIZE);
        return Native.toString(buf);
    }

    private static String getClassName(WinDef.HWND hWnd) {
        final char[] buf = new char[BUF_CHAR_SIZE];
        USER32.GetClassName(hWnd, buf, BUF_SIZE);
        return Native.toString(buf);
    }

    private static IntByReference getWindowThreadProcessId(WinDef.HWND hWnd) {
        final IntByReference p = new IntByReference();
        USER32.GetWindowThreadProcessId(hWnd, p);
        return p;
    }

    private static String getModuleFileNameEx(final HANDLE process, final HANDLE module) {
        final char[] buf = new char[BUF_CHAR_SIZE];
        PSAPI.GetModuleFileNameExW(process, module, buf, BUF_SIZE);
        return Native.toString(buf);
    }
}
