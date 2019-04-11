package com.example.myapplication;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
/**
 * Created by Administrator on 2019/3/21 0021.
 */

public class PersonService {
    public List<Person> getPersons(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory=SAXParserFactory.newInstance();
        SAXParser parser=factory.newSAXParser();
        PersonHandler personHandler=new PersonHandler();
        try {
            parser.parse(inputStream,personHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputStream.close();
        return personHandler.getPersons();
    }
    private class PersonHandler extends DefaultHandler{
        private List<Person> persons=null;
        String tagName=null;
        Person person;
        public List<Person> getPersons(){
            return persons;
        }
        public void startDocument(){
            persons=new ArrayList<Person>();
        }

        public void startElement(String uri, String localName, String qName, Attributes attributes){
            tagName=localName;
            if("person".equals(tagName)){
                person=new Person();
                person.setId(new Integer(attributes.getValue("id")));
            }
        }
        public void characters(char[] ch,int start,int length){
            if(tagName!=null){
                String data=new String(ch,start,length);
                if("name".equals(tagName)){
                    person.setName(data);
                }else if("age".equals(tagName)){
                    person.setAge(new Short(data));
                }
            }
        }
        public void endElement(String uri,String localName,String qName){
            if("person".equals(localName)){
                persons.add(person);
                person=null;
            }
            tagName=null;
        }
    }
}
