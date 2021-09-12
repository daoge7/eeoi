package cn.ccsit.eeoi.system.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.plexus.logging.LoggerManager;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.FileInputStream;
import java.io.StringReader;
@Slf4j
public class ValidateUtil {

    private static Logger logger = LogManager.getLogger(ValidateUtil.class);

    public static Boolean validate(String xml,String xsdSrc){
        Boolean pass = true;
        try {
            Source root = new StreamSource(ValidateUtil.class.getClassLoader().getResourceAsStream(xsdSrc));
            Source[] sourceArr = {root};
            Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(sourceArr);
            Validator validator = schema.newValidator();
            Source s = new StreamSource(new StringReader(xml));
            validator.validate(s);
        }catch (Exception e){
            e.printStackTrace();
            logger.info(e.getMessage());
            pass = false;
        }
        return pass;
    }

}