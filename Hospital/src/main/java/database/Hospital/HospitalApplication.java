package database.Hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class HospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalApplication.class, args);
	}

	// 监听 Spring Boot 启动完成事件，启动后自动打开浏览器
	@EventListener(ApplicationReadyEvent.class)
	public void openBrowserAfterStartup() {
		String url = "http://localhost:8080";
		System.out.println("启动完成，尝试打开浏览器: " + url);
		openBrowser(url);
	}

	// 打开浏览器的方法，兼容 Windows, Mac, Linux
	private static void openBrowser(String url) {
		try {
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				Desktop.getDesktop().browse(new URI(url));
			} else {
				// Linux 特殊处理，使用 Runtime 调用 xdg-open
				String os = System.getProperty("os.name").toLowerCase();
				if (os.contains("linux")) {
					Runtime.getRuntime().exec("xdg-open " + url);
				} else {
					System.out.println("当前系统不支持自动打开浏览器，请手动访问: " + url);
				}
			}
		} catch (IOException | URISyntaxException e) {
			System.err.println("无法打开浏览器，请手动访问: " + url);
			e.printStackTrace();
		}
	}
}
