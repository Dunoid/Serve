package org.dunoid.web.test;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.dunoid.web.html.HtmlArray;
import org.dunoid.web.html.HtmlObject;
import org.dunoid.web.html.HtmlPage;
import org.dunoid.web.http.HttpData.HttpCode;

public class HtmlTest {

	public static void main(String[] args) {
		OutputStream out = System.out;
		HtmlArray head = new HtmlArray();
		head.add(new HtmlObject("title", null, "Test Content"));
		
		HtmlArray body = new HtmlArray();
		
		Map<String, String> paraProperties = new HashMap<String, String>();
		paraProperties.put("id", "para");
		paraProperties.put("class", "content");
		
		HtmlObject para = new HtmlObject("p", paraProperties, "This is a paragraph!");

		body.addAll(new HtmlObject("h1", null, "This is the head!"), para);
		
		HtmlPage page = new HtmlPage(HttpCode.OK, head, body);
		
		try {
			page.write(out, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
