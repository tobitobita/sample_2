package com.dsk;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Ses {

	private static final String FROM = "makoto.tobita@change-vision.com";
	private static final String TO = "makoto.tobita@change-vision.com";

	public static void main(String[] args) {
		AmazonSimpleEmailService client
				= new AmazonSimpleEmailServiceClient();
		client.setEndpoint("https://email.us-west-2.amazonaws.com");
		client.setRegion(Region.getRegion(Regions.US_WEST_2));

		// リクエスト作成。
		SendEmailRequest request = new SendEmailRequest()
				// 送信元。
				.withSource(FROM)
				// 送信先。
				.withDestination(new Destination()
						.withToAddresses(new String[]{TO}))
				// メッセージ。
				.withMessage(new Message()
						// 件名。
						.withSubject(new Content()
								.withData("テストメースです。"))
						// 本文。
						.withBody(new Body()
								// テキスト。
								.withText(new Content()
										.withData("🍣うまい！"))));
		try {
			client.sendEmail(request);
			System.out.println("send mail.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
		}
	}
}
