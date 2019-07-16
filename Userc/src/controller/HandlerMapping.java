package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import pojo.Controller;

public class HandlerMapping {
	private HashMap<String, Controller> mappings;
	//list.do ==> MemberListController
	public HandlerMapping() {
		mappings=new HashMap<String,Controller>();
		initConnect();
	}
	private void initConnect() {
		/*mappings.put("/list.do", new MemberListController());
		mappings.put("/insertHtml.do", new MemberInsertHtmlController());
		mappings.put("/insert.do", new MemberInsertController());
		mappings.put("/delete.do", new MemberDeleteController());
		mappings.put("/content.do", new MemberContentController());*/
		//command.xml 파일 로딩-->DOM(Tree구조)
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		//class.forName
		try {
			//FileInputStream fis=new FileInputStream("command.xml");
			ClassLoader loader=getClass().getClassLoader();
			URL path=loader.getResource("/controller/command.xml");
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc=builder.parse(path.getPath());
			//ArratList NodeList
			NodeList commands=doc.getElementsByTagName("command");
			for (int i = 0; i < commands.getLength(); i++) {
				Element command=(Element) commands.item(i);
				String key=command.getAttribute("key");
				String value=command.getAttribute("class");
				mappings.put(key, (Controller) Class.forName(value).newInstance());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Controller getController(String key) {
		return mappings.get(key);
	}
}
