package org.dunoid.web.http;

import java.util.Arrays;
import java.util.Iterator;

public class RequestURI implements Iterable<RequestURI>{
	private class URI_Iterator implements Iterator<RequestURI>{
		private RequestURI uri;
		URI_Iterator(RequestURI uri){
			this.uri = uri;
		}
		@Override
		public boolean hasNext() {
			return uri != null;
		}
		@Override
		public RequestURI next() {
			RequestURI next = uri;
			uri = uri.subDirectory;
			return next;
		}
	}
	
	private String fileName;
	private RequestURI subDirectory;
	private boolean root;
	
	public RequestURI(String path){		
		if(path.startsWith("/")){
			path = path.substring(1);
		}
		if(path.length() == 0){
			root = true;
			fileName = "";
			subDirectory = null;
		}
		else {
			int idx;
			if((idx = path.indexOf('/')) > 0){
				fileName = path.substring(0, idx);
				subDirectory = new RequestURI(path.substring(idx+1));
			}
			else {
				fileName = path;
				subDirectory = null;
			}
		}
	}
	
	@Override
	public Iterator<RequestURI> iterator() {
		return new URI_Iterator(this);
	}

	public String getFileName() {
		return fileName;
	}

	public RequestURI next() {
		return subDirectory;
	}
	public RequestURI get(int i){
		if(i < 0){
			throw new IllegalArgumentException(
					"Tried to access negative subdirectories!");
		}
		else if(i == 0){
			return this;
		}
		else if(subDirectory == null){
			throw new IllegalArgumentException(
					"Tried to access null subdirectory!");
		}
		else {
			return subDirectory.get(i-1);
		}
	}

	public boolean isRoot() {
		return root;
	}
	
	public boolean isEnd(){
		return subDirectory == null;
	}
	
	public int depth(){
		if(root){
			return 0;
		}
		else if(subDirectory == null){
			return 1;
		}
		else {
			return 1 + subDirectory.depth();
		}
	}
	
	public boolean matches(String... path){
		if(path.length == 0){
			return root;
		}
		else if(!path[0].equals(fileName) && !path[0].equals("*")){
			return false;
		}
		else if(!isEnd()){
			return next().matches(Arrays.copyOfRange(path, 1, path.length));
		}
		else if(path.length == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public String toString(){
		if(isEnd()){
			return fileName;
		}
		else {
			return this.fileName + "/" +subDirectory.toString();
		}
	}
}
