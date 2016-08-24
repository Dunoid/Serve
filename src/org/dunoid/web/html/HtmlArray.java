package org.dunoid.web.html;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class HtmlArray extends ArrayList<HtmlElement> implements HtmlElement {
	private static final long serialVersionUID = 1L;

	/**
	 * Add a collection of objects to the HtmlArray
	 * @param elements HtmlElements AND/OR Strings to add to the array (strings
	 * are automatically wrapped in {@link HtmlText} objects
	 */
	public void addAll(Object... elements){
		for(Object e : elements){
			if(e instanceof HtmlElement){
				add((HtmlElement)e);
			}
			else if(e instanceof String){
				add(new HtmlText((String) e));
			}
			else {
				throw new IllegalArgumentException(
						"Tried to add object with invalid class: "+e);
			}
		}
	}

	@Override
	public void write(OutputStream stream, int tabulation) throws IOException {
		for(HtmlElement html : this){
			html.write(stream, tabulation);
		}
	}
}
