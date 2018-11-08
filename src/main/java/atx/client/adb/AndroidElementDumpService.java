package atx.client.adb;

import atx.client.model.AndroidElement;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//解析uidump.xml文件
public class AndroidElementDumpService extends DefaultHandler {
	private List<AndroidElement> dumps = null;
	private AndroidElement dump = null;

	public List<AndroidElement> getDumps(InputStream xml) {

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = null;
		try {
			parser = factory.newSAXParser();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		AndroidElementDumpService handler = new AndroidElementDumpService();
		try {
			parser.parse(xml, handler);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return handler.getDumps();
	}

	private List<AndroidElement> getDumps() {
		return dumps;
	}

	@Override
	public void startDocument() throws SAXException {
		dumps = new ArrayList<AndroidElement>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if ("node".equals(qName)) {
			dump = new AndroidElement();
			dump.setText(attributes.getValue("text"));
			dump.setPackageName(attributes.getValue("package"));
			dump.setResourceName(attributes.getValue("resource-id"));
			dump.setClassName(attributes.getValue("class"));
			dump.setContentDescription(attributes.getValue("content-desc"));
			dump.setCheckable(Boolean.parseBoolean(attributes.getValue("checkable")));
			dump.setChecked(Boolean.parseBoolean(attributes.getValue("checked")));
			dump.setClickable(Boolean.parseBoolean(attributes.getValue("clickable")));
			dump.setEnable(Boolean.parseBoolean(attributes.getValue("enable")));
			dump.setFocuseable(Boolean.parseBoolean(attributes.getValue("focusable")));
			dump.setFocused(Boolean.parseBoolean(attributes.getValue("focused")));
			dump.setScrollable(Boolean.parseBoolean(attributes.getValue("scrollable")));
			dump.setLongClickable(Boolean.parseBoolean(attributes.getValue("long-clickable")));
			dump.setSelected(Boolean.parseBoolean(attributes.getValue("selected")));
//			attributes.getValue("bounds");

			dump.setCoordinateInfo(getBounds(attributes.getValue("bounds")));



//			dump.setText(attributes.getValue("text"));
//			dump.setResourceId(attributes.getValue("resource-id"));
//			dump.setClassName(attributes.getValue("class"));
//			dump.setContentDesc(attributes.getValue("content-desc"));
//			dump.setCheckable(attributes.getValue("checkable"));
//			dump.setChecked(attributes.getValue("checked"));
//			dump.setClickable(attributes.getValue("clickable"));
//			dump.setBounds(attributes.getValue("bounds"));

			dumps.add(dump);
		}
	}

	public List<Float> getBounds(String str){
//		CharSequence input = getAttrib(att, str).get(
//				ElementAttribs.BOUNDS);
//
//		if (input == null) {
//			new TestException("未在当前界面找到元素(" + str + ")");
//			return null;
//		}
		List<Float> coordinateInfo =  new ArrayList<Float>();;
		Pattern pattern = Pattern.compile("([0-9]+)");
		Matcher mat = pattern.matcher(str);
		ArrayList<Float> coords = new ArrayList<Float>();
		while (mat.find()) {
			coords.add(new Float(mat.group()));
		}

		float startX = coords.get(0);
		float startY = coords.get(1);
		float endX = coords.get(2);
		float endY = coords.get(3);

		float centerCoordsX = (endX - startX) / 2 + startX;
		float centerCoordsY = (endY - startY) / 2 + startY;

		coordinateInfo.add(centerCoordsX);
		coordinateInfo.add(centerCoordsY);
		return coordinateInfo;
	}
}
