package org.dunoid.web.html;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;

public class HtmlObject implements HtmlElement {
	private String tag;
	private Map<String, String >attributes;
	private HtmlArray elements = new HtmlArray();
	
	public HtmlObject(String tag, Map<String, String> attributes){
		this.tag = tag;
		this.attributes = attributes;
	}
	
	public 
	HtmlObject(String tag,  Map<String, String> attributes, String content) {
		this(tag, attributes);
		elements.add(new HtmlText(content));
	}
	
	public String tag(){
		return tag;
	}
	
	public HtmlArray items(){
		return elements;
	}
	
	@Override
	public void write(OutputStream stream, int tabs) throws IOException {
		String tab = HtmlElement.getTab(tabs);
		String open = tab+"<"+tag;
		if(attributes != null){
			for(Entry<String, String> pair : attributes.entrySet()){
				open += String.format(
						" %s=\"%s\"", pair.getKey(), pair.getValue());
			}
		}
		open += ">\n";
		stream.write(open.getBytes());
		elements.write(stream, tabs+1);
		stream.write((tab+"</"+tag+">\n").getBytes());
	}

}
