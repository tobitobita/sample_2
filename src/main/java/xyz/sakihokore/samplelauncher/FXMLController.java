package xyz.sakihokore.samplelauncher;

import com.sun.jna.Native;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Psapi;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import static com.sun.jna.platform.win32.WinNT.PROCESS_QUERY_INFORMATION;
import static com.sun.jna.platform.win32.WinNT.PROCESS_VM_READ;
import static com.sun.jna.platform.win32.WinUser.SM_CXFULLSCREEN;
import static com.sun.jna.platform.win32.WinUser.SM_CXSCREEN;
import static com.sun.jna.platform.win32.WinUser.SM_CYFULLSCREEN;
import static com.sun.jna.platform.win32.WinUser.SM_CYSCREEN;
import com.sun.jna.ptr.IntByReference;
import java.io.IOException;
import java.math.BigDecimal;
import static java.math.RoundingMode.FLOOR;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
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

        final List<HWND> activeWindows = new ArrayList<>();
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
            if (className.equalsIgnoreCase("OpusApp") || className.equalsIgnoreCase("XLMAIN")) {
                activeWindows.add(hWnd);
            }
            if (className.equalsIgnoreCase("SunAwtFrame")) {
                if (title.contains("Astah") || title.contains("astah")) {
                    activeWindows.add(hWnd);
                }
            }
            return true;
        }, null);
        System.out.println("call end EnumWindows.");
        final int width = USER32.GetSystemMetrics(SM_CXSCREEN);
        final int height = USER32.GetSystemMetrics(SM_CYSCREEN);
        System.out.printf("w:%d, h:%d\n", width, height);
        final int workWidth = USER32.GetSystemMetrics(SM_CXFULLSCREEN);
        final int workHeight = USER32.GetSystemMetrics(SM_CYFULLSCREEN);
        System.out.printf("ww:%d, wh:%d\n", workWidth, workHeight);
        if (activeWindows.isEmpty()) {
            return;
        }
        Rectangle2D[] rects = calc(workWidth, workHeight, activeWindows.size());
        IntStream.range(0, activeWindows.size()).forEach(index -> {
            Rectangle2D rect = rects[index];
            USER32.MoveWindow(activeWindows.get(index), (int) rect.getMinX(), (int) rect.getMinY(), (int) rect.getWidth(), (int) rect.getHeight(), true);
        });

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

    private static class Pair {

        private final int _a;
        private final int _b;
        private final BigDecimal a;
        private final BigDecimal b;

        Pair(int a, int b) {
            this._a = a;
            this._b = b;
            this.a = new BigDecimal(_a);
            this.b = new BigDecimal(_b);
        }

        public int getDistance() {
            return Math.abs(_a - _b);
        }

        @Override
        public String toString() {
            return String.format("a:%d, b:%d", _a, _b);
        }
    }

    private static Pair calcPair(final int count) {
        System.out.printf("count:%d\n", count);
        final int scale = new BigDecimal(Math.sqrt(count)).scale();
        final int maxValue = scale == 0 || count % 2 == 0 ? count : count + 1;
        final Pair divisor = IntStream.rangeClosed(0, maxValue)
                .parallel()
                .filter(value -> value != 0 && maxValue % value == 0)
                .mapToObj(value -> {
                    return new Pair(value, maxValue / value);
                })
                .peek(pair -> {
                    System.out.printf("約数:%s\n", pair);
                })
                .min((pair1, pair2) -> {
                    return pair1.getDistance() - pair2.getDistance();
                }).get();
        System.out.printf("タイルは、%sです。\n", divisor);
        return divisor;
    }

    public static Rectangle2D[] calc(final int screenWidth, final int screenHeight, final int windowCount) {
        final BigDecimal width = new BigDecimal(screenWidth);
        final BigDecimal height = new BigDecimal(screenHeight);

        final Pair pair = calcPair(windowCount);
        final int oneWidth = width.divide(pair.b, FLOOR).intValue();
        final int oneHeight = height.divide(pair.a, FLOOR).intValue();
        final int otherWidth = width.remainder(pair.b).intValue();
        final int otherHeight = height.remainder(pair.a).intValue();
        System.out.printf("width: %d * %d + %d = %d\n", oneWidth, pair._b, otherWidth, oneWidth * pair._b + otherWidth);
        System.out.printf("height: %d * %d + %d = %d\n", oneHeight, pair._a, otherHeight, oneHeight * pair._a + otherHeight);

        final Rectangle2D[] windowRects = new Rectangle2D[windowCount];
        int minX = 0;
        int minY = 0;
        int count = 0;
        for (int row = 0; row < pair._a; ++row) {
            final int theHeight = row + 1 == pair._a ? oneHeight + otherHeight : oneHeight;
            for (int col = 0; col < pair._b; ++col) {
                if (count == windowCount) {
                    break;
                }
                final int theWidth = col + 1 == pair._b ? oneWidth + otherWidth : oneWidth;
                windowRects[count++] = new Rectangle2D(minX, minY, theWidth, theHeight);
                minX += oneWidth;
            }
            minX = 0;
            minY += oneHeight;
        }

        Arrays.stream(windowRects).forEach(System.out::println);

        return windowRects;
    }
}
