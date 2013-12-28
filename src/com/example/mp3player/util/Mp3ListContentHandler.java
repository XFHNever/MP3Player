package com.example.mp3player.util;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.example.mp3player.model.Mp3Info;

public class Mp3ListContentHandler extends DefaultHandler{
	private Mp3Info mp3Info = null;
	private List<Mp3Info> mp3Infos = null;
	private String tagName = null;
	
	public Mp3ListContentHandler() {
		super();
	}
	
	public Mp3ListContentHandler(List<Mp3Info> list) {
		super();
		this.mp3Infos = list;
	}

	public List<Mp3Info> getMp3Infos() {
		return mp3Infos;
	}

	public void setMp3Infos(ArrayList<Mp3Info> mp3Infos) {
		this.mp3Infos = mp3Infos;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		String temp = new String(ch, start, length);
		if (tagName.equals("id")) {
			mp3Info.setId(temp);
		} else if (tagName.equals("mp3.name")) {
			mp3Info.setMp3Name(temp);
		} else if (tagName.equals("mp3.size")) {
			mp3Info.setMp3Size(temp);
		} else if (tagName.equals("lrc.name")) {
			mp3Info.setLrcName(temp);
		} else if (tagName.equals("lrc.size")) {
			mp3Info.setLrcSize(temp);
		}
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
        if(qName.equals("resource")) {
        	mp3Infos.add(mp3Info);
        }
        tagName = "";
	
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		tagName = localName;
		System.out.println(11);
		if (tagName.equals("resource")) {
			mp3Info = new Mp3Info();
			System.out.println(22);
		} 
	}
     
}
