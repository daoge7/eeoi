package cn.ccsit.eeoi.system.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
@Slf4j
public class ValidateXML {
    public static Boolean validate(String xml, String xsdPath){
        Boolean pass = true;
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            Resource resource = new ClassPathResource(xsdPath);
            InputStream is = resource.getInputStream();
            Source xsdSource = new StreamSource(is);
//            Source xsdSource = new StreamSource(new FileInputStream(xsdPath));
            Schema schema = schemaFactory.newSchema(xsdSource);
            Validator validator = schema.newValidator();
            Source s = new StreamSource(new StringReader(xml));
            validator.validate(s);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.getMessage());
            pass = false;
        }
        return pass;
    }
}