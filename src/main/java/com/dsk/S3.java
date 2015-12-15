package com.dsk;

import static ch.qos.logback.core.util.OptionHelper.getEnv;
import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import static java.nio.charset.Charset.forName;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import static java.lang.String.format;
import static java.lang.System.getProperty;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class S3 {

	private static final String BUCKET_NAME = "tobi_temp";

	private static int count;

	private static final String[] S_12 = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};

	private static final DateTimeFormatter FORMATTER_PATH = DateTimeFormatter.ofPattern("yyyyMM");
	private static final DateTimeFormatter FORMATTER_DIR = DateTimeFormatter.ofPattern("dd");
	private static final DateTimeFormatter FORMATTER_FILE = DateTimeFormatter.ofPattern("HHmmssSSS");

	private static final String SEPARATOR = "file.separator";

	public static void main(String[] args) throws IOException {
		System.out.println("xyz.dsk.MainClass.main()");
		final ZonedDateTime now = ZonedDateTime.now();
		Path p = Paths.get(getEnv("java.io.tmpdir"), FORMATTER_DIR.format(now));
		if (Files.exists(p)) {
			FileUtils.forceDelete(p.toFile());
		}
		final Path dir = Files.createDirectory(p);
		final List<File> list = new ArrayList<>();
		while (list.size() < 100) {
			final Path path = Files.createFile(Paths.get(dir.toFile().getAbsolutePath(), FORMATTER_FILE.format(ZonedDateTime.now()) + ".csv"));
			try (PrintWriter out = new PrintWriter(new OutputStreamWriter(
					new BufferedOutputStream(new FileOutputStream(path.toFile())), forName("MS932")))) {
				int c = 0;
				while (c < 1000) {
					StringBuilder sb = new StringBuilder();
					sb.append("id").append(count).append(',');
					sb.append("名前").append(count).append(',');
					sb.append(count % 2 == 0 ? 0 : 1).append(',');
					sb.append("これは、サンプルです。値は").append(S_12[count % 12]).append("です。");
					System.out.println(sb.toString());
					out.println(sb.toString());
					count++;
					c++;
				}
				out.flush();
			}
			list.add(path.toFile());
		}
		final String virtualDirPath = format("%s%s%s", FORMATTER_PATH.format(now), getProperty(SEPARATOR), dir.getFileName());
		System.out.println(virtualDirPath);
		final AmazonS3 s3 = new AmazonS3Client();
		s3.setEndpoint("https://s3.amazonaws.com");
		TransferManager tm = null;
		try {
			tm = new TransferManager(s3);
			final MultipleFileUpload upload = tm.uploadFileList(BUCKET_NAME, virtualDirPath, dir.toFile(), list);
//			long lastTransferred = 0;
//			while (!upload.isDone()) {
//				long transferred = upload.getProgress().getBytesTransferred();
//				System.out.printf("worked: %d%n", (int) (transferred - lastTransferred));
//				lastTransferred = transferred;
//
//				System.out.printf("worked: %f%n", upload.getProgress().getPercentTransferred());
//
//				Thread.sleep(100);
//			}
			upload.waitForCompletion();
			System.out.println("uploaded.");
		} catch (AmazonClientException | InterruptedException e) {
			log.error(e.getLocalizedMessage(), e);
		} finally {
			if (tm != null) {
				tm.shutdownNow();
			}
		}

		FileUtils.forceDelete(dir.toFile());
	}
}
