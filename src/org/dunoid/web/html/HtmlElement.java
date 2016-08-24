package org.dunoid.web.html;

import org.dunoid.web.http.HttpResponse;

public interface HtmlElement extends HttpResponse {
	public static String tab = "\t";
	public static String getTab(int tabs){
		String tab = "";
		while(tabs --> 0){
			tab += HtmlElement.tab;
		}
		return tab;
	}
}
