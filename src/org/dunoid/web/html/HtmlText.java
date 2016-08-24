package org.dunoid.web.html;

import java.io.IOException;
import java.io.OutputStream;

public class HtmlText implements HtmlElement {
	private String[] text;
	private boolean escapeText = true;
	
	/**
	 * Escape any HTML characters
	 * @param text The text to escape
	 * @return HTML-safe text
	 */
	public final String scrub(String text){
		return text.replaceAll("&", "&amp;")
		           .replaceAll(">", "&gt;")
		           .replaceAll("<", "&lt;");
	}
	
	public HtmlText(String[] lines){
		this.text = lines;
	}
	public HtmlText(String block){
		this(block.split("\n"));
	}
	protected void escapeText(boolean escape){
		this.escapeText = escape;
	}
	public boolean isEscaped(){
		return escapeText;
	}
	@Override
	public void write(OutputStream stream, int tabs) throws IOException {
		String tab = HtmlElement.getTab(tabs);
		for(String line : text){
			line = tab+line+"\n";
			if(escapeText){
				line = scrub(line);
			}
			stream.write(line.getBytes());
		}
	}
}
