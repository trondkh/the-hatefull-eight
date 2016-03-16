package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class XMLFile {
	
	private String path,			//Path/URL to the file
				rawFile;			//The file content as a string, unformatted
	private boolean online;			//Tells whether the path is a URL or local path
	
	private ArrayList<Integer> pointer;						//Points to index of parent tag
	private ArrayList<String> tagText;						//Text of tag, given <tagname .....> text </tagname>
	private ArrayList<ArrayList<Integer>> children;
	private ArrayList<ArrayList<String>> attributeNames, 	//Contains the names of the attributes for current tag
										attributes;			//Contains the values of the attributes for current tag
	
															//First entry in attributeNames will be "tagname"
															//First entry in attributes will be the name
	
															//Second entry in attributeNames will be "stringindex", and will give the index to tagText
															//Second entry in attributes will be the actual index to tagText. -1 if there is no text
	
	
	public XMLFile(boolean online, String path) {
		//Setting private variables
		this.path = path;
		this.online = online;
		
		
		//Loading file into rawFile
		//Loads URL if online, else loads local path
		if(online) {rawFile = loadURL(path);} else {rawFile = loadFile(path);}
		
		
		//Initializing ArrayLists
		pointer = new ArrayList<Integer>();
		children = new ArrayList<ArrayList<Integer>>();
		tagText = new ArrayList<String>();
		attributeNames = new ArrayList<ArrayList<String>>();
		attributes = new ArrayList<ArrayList<String>>();
		
		format();
	}
	
	
	//Formats rawFile
	private void format() {
		int currentIndex = 0;		//Index of the character after last parsed tag
		int currentPointer = -1; 	//Index of the current parent tag
		int tagCounter = 0;			//Counts number of tags (Will also function as index of last tag added)
		
		
		//Parsing loop, formats one tag per iteration
		while(currentIndex < rawFile.length()) {
			int endIndex = getTagEndIndex(currentIndex);
			if(endIndex == -1) break;						//Exits loop if endIndex == -1, error code of getTagEndIndex()
			//System.out.println(rawFile.substring(currentIndex, endIndex));
			//if(rawFile.substring(currentIndex).trim().startsWith("<xCoord")) System.out.println("Pre: " + rawFile.substring(currentIndex, endIndex));
			
			//Parses tag, and updates index to parent tag if new tag is multi component
			//For more info see comment on parseTag()!
			boolean[] tagType = parseTag(rawFile.substring(currentIndex, endIndex));
			
			if(tagType[3]) {currentIndex = endIndex; continue;} 	//Indicates that tag is comment
			
			//Sets next tag as child of last multi component tag (potential parent tag)
			if(!tagType[2]) {
				//System.out.println("Setting pointer for " + tagCounter + " to " + currentPointer);
				pointer.add(currentPointer);
				children.add(new ArrayList<Integer>());
				if(currentPointer != -1) children.get(currentPointer).add(tagCounter);
			} else {
				//System.out.println("Ignoring");
			}
			
			if(tagType[0]) {
				//STEP UP
				//System.out.println(tagCounter + ": Up from " + currentPointer + " to " + (tagCounter));
				currentPointer = tagCounter;
			} else if(tagType[1]) {
				//STEP DOWN
				//System.out.println(tagCounter + ": Back from " + currentPointer + " to " + getPointer(currentPointer));
				currentPointer = getPointer(currentPointer);
			} else {
				//IGNORE
				//System.out.println(tagCounter + ": Standing still");
			}
			
			if(!tagType[2]) tagCounter += 1;
			currentIndex = endIndex;
			
			//System.out.println();
		}
	}
	
	
	
	////////////////FORMATTING METHODS/////////////////
	
	//Adds attribute with name at first open spot on tag at index
	private void addAttribute(int index, String name, String value) {
		if(index < 0) return; //Makes sure index is valid (not negative)
		
		//Makes sure the ArrayLists are big enough
		while(attributeNames.size() < index) {attributeNames.add(new ArrayList<String>());} 
		while(attributes.size() < index) {attributes.add(new ArrayList<String>());}
		
		
		//Makes sure attributes and attributeNames are of equal size 
		while(attributes.get(index).size() < attributeNames.get(index).size()) attributes.get(index).add("N/A");
		while(attributeNames.get(index).size() < attributes.get(index).size()) attributeNames.get(index).add("N/A");
		
		//Adds values to respective ArrayLists
		attributeNames.get(index).add(name);
		attributes.get(index).add(value);
	}
	
	//Adds text to first open spot in tagText ArrayList
	//returns new index of text in tagText
	private int addText(String text) {
		tagText.add(text);
		return tagText.size() - 1;
	}
	
	//Takes in full tag as <tagname attribute="value"...........> and formats it
	
	//returns first bit as 1 if tag is a single component tag, i.e. on the form <tagname attributeName="value"...../>
	//as opposed to multi component tags on the format <tagname attributeName="value".....>...</tagname>
	//returns second bit as 1 if tag is multi component and ending tag
	//return third bit as 1 if it is a comment and should be ignored
	private boolean[] parseTag(String tag) {
		boolean[] temp = new boolean[4];
			/*
			 * 0: Step up
			 * 1: Step down
			 * 2: Last Part
			 * 3: Comment
			 */
		
		//Tag will by default step up
		temp[0] = true;
		
		if(tag.length() < 3) return temp;
		
		//Converts tag string to usable format (removes spaces on start and end and ignores \n)
		tag = tag.replaceAll("\n", "");
		tag = tag.replaceAll("\t", "");
		tag.trim();
		
		//Trim that actually works... Removes every space before the tag, and '<' and '>' on beginning and end respectively
		int startIndex = 0;
		for(int i = 0; i < tag.length(); i++) {
			startIndex = i + 1;
			if(tag.charAt(i) != ' ' && tag.charAt(i) == '<') break;
		}
		tag = tag.substring(startIndex, tag.length() - 1);
		
		//Checks if tag is self contained
		if(tag.charAt(tag.length() - 1) == '/') {
			tag = tag.substring(0, tag.length() - 1);
			temp[0] = false;
			//System.out.println("Self contained tag");
		}
		
		//Checks if end tag, i.e. tag is on form </tagname>
		int readPos = 0;
		
		//Index of character that starts the first unparsed part of the tag
		if(tag.charAt(0) == '/') {
			temp[0] = false;
			temp[1] = true;
			temp[2] = true;
			readPos = 1;
			//System.out.println("Not entering");
			return temp;
		} else if(tag.charAt(0) == '!' || tag.charAt(0) == '?') {
			temp[3] = true;
			return temp;
		}
		
		//Adds ArrayLists for current tag in attributeNames and attributes
		attributeNames.add(new ArrayList<String>());
		attributes.add(new ArrayList<String>());
		
		//Index of current tag in ArrayLists attributeNames and attributes
		int tagIndex = attributeNames.size() - 1;
		
		//As in method getTagEndIndex(), ignore any action if currentCharacter inside a quote
		int quotes = 0;		
		
		//Checks if tag contains text
		boolean hasText = false;
		
		//Iterates through sections inside tag, e.g. tagname, attribute="value" etc
		for(int i = 0; i < tag.length(); i++) {
			if(tag.charAt(i) == '"') quotes += 1;
			if(quotes % 2 == 1) continue;
			
			//Checks if i is at end of new section
			if((tag.charAt(i) == ' ' || tag.charAt(i) == '>' || tag.charAt(i) == '<' || i == tag.length() - 1) && readPos < i && tag.substring(readPos, i).trim().length() > 0) {
				String tagSection;
				if(i == tag.length() - 1) tagSection = tag.substring(readPos, i + 1); else tagSection = tag.substring(readPos, i);
				
				//Handles tagSection
				String[] splittedTagSection = tagSection.split("=");
				if(splittedTagSection.length == 2 && attributeNames.size() > 1) {
					int start = -1;
					int end = -1;
					for(int i2 = 0; i2 < splittedTagSection[1].length(); i2++) {
						if(splittedTagSection[1].charAt(i2) == '"') {
							if(start == -1) {start = i2 + 1;} else {end = i2; break;}
						}
					}
					
					if(start != -1 && end != -1) addAttribute(tagIndex, splittedTagSection[0].trim(), splittedTagSection[1].substring(start, end).trim());
				} else if(splittedTagSection.length == 1 && attributeNames.get(tagIndex).size() == 0) {
					addAttribute(tagIndex, "tagname", splittedTagSection[0]);
					addAttribute(tagIndex, "stringindex", "-1");
				}
				
				readPos = i;
				
				//Handle text of tag
				if(tag.charAt(i) == '>') {
					String textOfTag = "";
					for(int i2 = 0; i2 < tag.length() - i; i2++) {
						if(tag.charAt(i + i2) == '<') {
							textOfTag = tag.substring(readPos + 1, i + i2);
							break;
						}
					}
					if(textOfTag.equals("")) continue;
						
					readPos = i + textOfTag.length() + 1;
					attributes.get(tagIndex).set(1, "" + addText(textOfTag));
					hasText = true;
					continue;
				}
			}
		}
		
		if(hasText) {
			//Tag containing text indicates that tag is self contained
			temp[0] = false;
			temp[1] = false;
		}
		
		return temp;
	}
	
	//Takes in start index of rawFile
	//returns end index of first tag encountered
	private int getTagEndIndex(int start) {
		int index = -1;
		
		int quotes = 0; 	//Counter for quotation marks
							//Makes sure the tag isn't erroneously split inside a quote
		
		boolean hasText = false; //Tells if the tag has text
		
		for(int i = 1; i < rawFile.length() - start; i++) {
			char currentChar = rawFile.charAt(start + i);
			if(currentChar == '"') {quotes += 1; continue;} //Adds to quotes counter
			if(quotes % 2 == 1) continue;					//If quotes % 2 == 1, currentChar is inside a qoute
															//and should therefore be ignored
			if(currentChar == '>') {
				index = start + i + 1;
				if(hasText) break;							//Breaks if '>' is end of ending tag
			}
			
			if(currentChar == '<' && index != -1 && !hasText) break;	//Breaks if there is no text, and '<' is start of new, independent tag
			
			if(!hasText && (Character.isAlphabetic(currentChar) || Character.isDigit(currentChar)) && index != -1) hasText = true;	//Indicates that tag has text
		}
		
		return index;
	}
	
	//Returns parent tag of tag at index
	private int getPointer(int index) {
		if(index < 0 || index >= pointer.size()) return -1;
		return pointer.get(index);
	}
	
	
	
	
	////////////////DEBUGGING OUTPUT METHODS//////////////////////
	public void printTag(int index) {
		System.out.println("--------PRINTING TAG FOR " + index + "---------");
		System.out.println("-Parent: " + getPointer(index));
		System.out.println("-Text: " + getText(index));
		System.out.println("-Attributes: ");
		for(int i = 0; i < attributeNames.get(index).size(); i++) {
			System.out.print("	");
			println(attributeNames.get(index).get(i), attributes.get(index).get(i), 38);
		}
		
		System.out.println("-Tag Tree:");
		ArrayList<Integer> temp = getTagTree(index);
		System.out.print("\t");
		for(int i = 0; i < temp.size(); i++) {System.out.print(temp.get(temp.size() - i - 1) + " ");}
		System.out.println();
	}
	
	
	//returns an ArrayList with indices of parents until it reaches an end
	public ArrayList<Integer> getTagTree(int index) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.add(index);
		
		int nextPointer = index;
		while(nextPointer != -1) {
			int lastPointer = nextPointer;
			nextPointer = getPointer(nextPointer);
			if(lastPointer == nextPointer) break;
			
			temp.add(nextPointer);
		}
		
		return temp;
	}
	
	
	//Print method that gives consistent spacing 
	public static void println(String left, String right, int span) {
		System.out.print(left);
		for(int i = 0; i < span - left.length() - right.length(); i++) System.out.print(" ");
		System.out.println(right);
	}
	
	public static void printIntegerArray(ArrayList<Integer> arrayList) {System.out.println(); for(int i = 0; i < arrayList.size(); i++) {System.out.print(arrayList.get(i) + " ");} System.out.println();}
	
	
	/*
	 * Loads file from local path
	 * returns file as raw String, unformatted
	 */
	private String loadFile(String path) {
		String temp = null;
		
		byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get(path));
			temp = new String(encoded, Charset.defaultCharset());
		} catch (IOException e) {
			System.err.println("Could not fetch data for: " + path);
			e.printStackTrace();
		}
		
		return temp;
	}
	
	/*
	 * Loads file from URL on the Internet
	 * returns file as raw String, unformatted
	 */
	private String loadURL(String path) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;
		String temp = null;
		
		try {
			URL url = new URL(path);
			
			is = url.openStream();
			byte[] byteChunk = new byte[4096];
			
			int n;
			while((n = is.read(byteChunk)) > 0) baos.write(byteChunk, 0, n);
			
			byte[] encoded = baos.toByteArray();
			temp = new String(encoded, Charset.defaultCharset());
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return temp;
	}
	
	//Returns path
	public String getPath() {return path;}
	
	//Returns file content as String, unformatted
	public String getFileContents() {return rawFile;}
	
	//Returns true if URL is loaded, else false
	public boolean isOnline() {return online;}
	
	//Returns number of tags
	public int getTagCount() {return attributeNames.size();}
	
	//Returns text for tag at index
	public String getText(int index) {
		if(index < 0 || index >= attributeNames.size()) return "N/A";
		if(Integer.parseInt(attributes.get(index).get(1)) == -1) return "N/A";
		return tagText.get(Integer.parseInt(attributes.get(index).get(1)));
	}
	
	//Returns attribute with name from tag at index
	public String getAttributeFromTag(int index, String attributeName) {
		if(index < 0 || index >= attributeNames.size()) return "N/A";
		for(int i = 0; i < attributeNames.get(index).size(); i++) {if(attributeNames.get(index).get(i).equals(attributeName)) return attributes.get(index).get(i);}
		return "N/A";
	}
	
	//Returns indices of tags with matching attribute names and values
	public ArrayList<Integer> getTagsWithAttributesAndValues(String name, String value) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for(int i = 0; i < attributeNames.size(); i++) {
			for(int i2 = 0; i2 < attributeNames.get(i).size(); i2++) {
				if(attributeNames.get(i).get(i2).equals(name)) {
					if(attributes.get(i).get(i2).equals(value)) {
						temp.add(i);
					}
				}
			}
		}
		return temp;
	}
	
	//Returns all tags with given tagname
	public ArrayList<Integer> getTagsNamed(String name) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for(int i = 0; i < attributeNames.size(); i++) {if(attributes.get(i).get(0).equals(name)) temp.add(i);}
		return temp;
	}
	
	//Returns children of tag
	public ArrayList<Integer> getTagChildren(int index) {
		if(index < 0 || index >= children.size()) return null;
		return children.get(index);
	}
	
	public String toString() {return rawFile;}
}
