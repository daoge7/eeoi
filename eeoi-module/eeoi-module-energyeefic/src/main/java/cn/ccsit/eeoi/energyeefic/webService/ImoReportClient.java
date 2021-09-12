package cn.ccsit.eeoi.energyeefic.webService;

import cn.ccsit.eeoi.energyeefic.vo.ImoOilVo;
import cn.ccsit.eeoi.energyeefic.vo.ReaportImoRptVo;
import cn.ccsit.eeoi.system.utils.FtpProperties;
import cn.ccsit.eeoi.system.utils.ValidateXML;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
public class ImoReportClient {
    private static String url = "https://gisis.imo.org/WebServices/FUEL/FUELData.asmx";
    private static String authorityCode = "Public";
    private static String username = "ccs_service";
    private static String password = "54j9tg";

    @Autowired
    FtpProperties ftpProperties;

    /**
     * 发起POST请求
     *
     * @param reaportImoRptVo
     */
    public boolean sendPost(ReaportImoRptVo reaportImoRptVo) {
        // 创建httpClient实例对象
        HttpClient httpClient = new HttpClient();
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        // 创建post请求方法实例对象
        PostMethod postMethod = new PostMethod(url);
        // 设置post请求超时时间
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        postMethod.addRequestHeader("Content-Type", "text/xml");
        String xmlStr = this.createXml(reaportImoRptVo);
        try {
            //json格式的参数解析
            RequestEntity entity = new StringRequestEntity(xmlStr, "application/json", "UTF-8");
            postMethod.setRequestEntity(entity);
            httpClient.executeMethod(postMethod);
            String result = postMethod.getResponseBodyAsString();
            postMethod.releaseConnection();
            log.warn("send imo result |" +  reaportImoRptVo.getImoNu() + "|"  + result);
            return result.indexOf("<SubmitRecordResult>true</SubmitRecordResult>") != -1;
        } catch (IOException e) {
            return false;
        }
    }

//    private String createXml(ReaportImoRptVo reaportImoRptVo) {
//        StringBuilder xmlBody = new StringBuilder();
//        //zxh: 增加报文头
//        xmlBody.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"yes\"?>");
//        xmlBody.append("<Records xmlns=\"https://gisis.imo.org/XML/FUEL/2018\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"https://gisis.imo.org/WebServices/XML/FUEL/FUEL.xsd\">");
//        xmlBody.append("<Record ReferenceCode=\"" + reaportImoRptVo.getRefCode() + "\">");
//        xmlBody.append("<ReportingStartDate>");
//        xmlBody.append(reaportImoRptVo.getStartTime());
//        xmlBody.append("</ReportingStartDate>");
//        xmlBody.append("<ReportingEndDate>");
//        xmlBody.append(reaportImoRptVo.getEndTime());
//        xmlBody.append("</ReportingEndDate>");
//        xmlBody.append("<ShipFlag>");
//        //TODO1: 要转为3位字母国家代码，已经在VO加工中处理
//        xmlBody.append(reaportImoRptVo.getFlag());
//        xmlBody.append("</ShipFlag>");
//        xmlBody.append("<ShipIMONumber>");
//        xmlBody.append(reaportImoRptVo.getImoNu());
//        xmlBody.append("</ShipIMONumber>");
//        xmlBody.append("<ShipType>");
//        //TODO1: 船舶类型要使用IMO要求的类型，已经在报告VO对象中处理
//        xmlBody.append(reaportImoRptVo.getShipType());
//        xmlBody.append("</ShipType>");
//        xmlBody.append("<ShipGrossTonnage>");
//        xmlBody.append(reaportImoRptVo.getGross());
//        xmlBody.append("</ShipGrossTonnage>");
//        xmlBody.append("<ShipNetTonnage>");
//        xmlBody.append(reaportImoRptVo.getNet());
//        xmlBody.append("</ShipNetTonnage>");
//        xmlBody.append("<ShipDeadweight>");
//        xmlBody.append(reaportImoRptVo.getDwt());
//        xmlBody.append("</ShipDeadweight>");
//        if (reaportImoRptVo.getMainEngines() != null && reaportImoRptVo.getMainEngines().size() > 0) {
//            xmlBody.append("<ShipMainPropulsionPowers>");
//            //TODO1: 需要处理多种类型的数据，主要针对历史数据，格式：No1: 1020.0 / No2: 1020.0 / No3: 1020.0，已经在VO加工中处理
//            for (String item : reaportImoRptVo.getMainEngines()) {
//                xmlBody.append("<ShipMainPropulsionPower>");
//                xmlBody.append(item);
//                xmlBody.append("</ShipMainPropulsionPower>");
//            }
//            xmlBody.append("</ShipMainPropulsionPowers>");
//        }
//        if (reaportImoRptVo.getAuxEngines() != null && reaportImoRptVo.getAuxEngines().size() > 0) {
//            xmlBody.append("<ShipAuxiliaryEnginesPowers>");
//            //TODO1: 需要处理多种类型的数据，主要针对历史数据，格式：No1: 1020.0 / No2: 1020.0 / No3: 1020.0，已经在VO加工中处理
//            for (String item : reaportImoRptVo.getAuxEngines()) {
//                xmlBody.append("<ShipAuxiliaryEnginesPower>");
//                xmlBody.append(item);
//                xmlBody.append("</ShipAuxiliaryEnginesPower>");
//            }
//            xmlBody.append("</ShipAuxiliaryEnginesPowers>");
//        }
//        xmlBody.append("<ShipEEDI>");
//        xmlBody.append(reaportImoRptVo.getEedi());
//        xmlBody.append("</ShipEEDI>");
//        xmlBody.append("<DistanceTravelled>");
//        xmlBody.append(reaportImoRptVo.getDistance());
//        xmlBody.append("</DistanceTravelled>");
//        xmlBody.append("<HoursUnderway>");
//        xmlBody.append(reaportImoRptVo.getDistanceHour());
//        xmlBody.append("</HoursUnderway>");
//        if (reaportImoRptVo.getImoOils() != null && reaportImoRptVo.getImoOils().size() > 0) {
//            xmlBody.append("<ConsumptionData>");
//            for (ImoOilVo item : reaportImoRptVo.getImoOils()) {
//                //zxh: 如果燃油消耗量为零，则不要该油种类的节点
//                if (item.getQuantity().intValue() == 0) {
//                    continue;
//                }
//                xmlBody.append("<Consumption>");
//                xmlBody.append("<Quantity>");
//                xmlBody.append(item.getQuantity());
//                xmlBody.append("</Quantity>");
//                xmlBody.append("<Type>");
//                //TODO1: 燃油类型要使用标准的燃油类型，已经在VO对象中处理完成
//                xmlBody.append(item.getType());
//                xmlBody.append("</Type>");
//                xmlBody.append("<DataCollectionMethod>");
//                //TODO1：燃油消耗计量方法要用IMO的标准叫法，已经在VO对象中处理完成
//                xmlBody.append(item.getDataCollectionMethod());
//                xmlBody.append("</DataCollectionMethod>");
//                xmlBody.append("</Consumption>");
//            }
//            xmlBody.append("</ConsumptionData>");
//        }
//        xmlBody.append("</Record>");
//        xmlBody.append("</Records>");
//        String recordsXml = xmlBody.toString();
//        //TODO: 使用IMO标准XSD验证DCS上报数据有效性，需要修改方法的返回类型，以支持校验结果向前台传送
//        if (!ValidateXML.validate(recordsXml, "/downLoad/imoDcs.xsd")) {
//
//        }
//        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" +
//                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\r\n" +
//                "  <soap12:Header>\r\n" +
//                "    <AuthenticationHeader xmlns=\"https://gisis.imo.org/XML/FUEL/2018\">\r\n" +
//                "      <AuthorityCode>" + authorityCode + "</AuthorityCode>\r\n" +
//                "      <Username>" + username + "</Username>\r\n" +
//                "      <Password>" + password + "</Password>\r\n" +
//                "    </AuthenticationHeader>\r\n" +
//                "  </soap12:Header>\r\n" +
//                "  <soap12:Body>\r\n" +
//                "    <SubmitRecord xmlns=\"https://gisis.imo.org/XML/FUEL/2018\">\r\n" +
//                "      <RecordsXml><![CDATA[" + recordsXml + "]]></RecordsXml>\r\n" +
//                "    </SubmitRecord>\r\n" +
//                "  </soap12:Body>\r\n" +
//                "</soap12:Envelope>";
////        xml = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:ns=\"https://gisis.imo.org/XML/FUEL/2018\">\n" +
////                "   <soap:Header>\n" +
////                "      <ns:AuthenticationHeader>\n" +
////                "         <!--Optional:-->\n" +
////                "         \n" +
////                "         <!--Optional:-->\n" +
////                "         \n" +
////                "         <!--Optional:-->\n" +
////                "         <ns:AuthorityCode>Public</ns:AuthorityCode><ns:Username>ccs_service</ns:Username><ns:Password>54j9tg</ns:Password>\n" +
////                "      </ns:AuthenticationHeader>\n" +
////                "   </soap:Header>\n" +
////                "   <soap:Body>\n" +
////                "      <ns:SubmitRecord>\n" +
////                "         <!--Optional:-->\n" +
////                "         \n" +
////                "      <ns:RecordsXml><![CDATA[" + recordsXml + "]]></ns:RecordsXml></ns:SubmitRecord>\n" +
////                "   </soap:Body>\n" +
////                "</soap:Envelope>";
//        return xml;
//    }

    private String createXml(ReaportImoRptVo reaportImoRptVo) {
        StringBuilder xmlBody = new StringBuilder();
        log.info("createXml |" +  reaportImoRptVo.toString());
        //zxh: 增加报文头
        xmlBody.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"yes\"?>");
        xmlBody.append("<Records xmlns=\"https://gisis.imo.org/XML/FUEL/2018\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"https://gisis.imo.org/WebServices/XML/FUEL/FUEL.xsd\">");
        xmlBody.append("<Record ReferenceCode=\"" + reaportImoRptVo.getRefCode() + "\">");
        xmlBody.append("<ReportingStartDate>");
        xmlBody.append(reaportImoRptVo.getStartTime());
        xmlBody.append("</ReportingStartDate>");
        xmlBody.append("<ReportingEndDate>");
        xmlBody.append(reaportImoRptVo.getEndTime());
        xmlBody.append("</ReportingEndDate>");
        xmlBody.append("<ShipFlag>");
        //TODO1: 要转为3位字母国家代码，已经在VO加工中处理
        xmlBody.append(reaportImoRptVo.getFlag());
        xmlBody.append("</ShipFlag>");
        xmlBody.append("<ShipIMONumber>");
        xmlBody.append(reaportImoRptVo.getImoNu());
        xmlBody.append("</ShipIMONumber>");
        xmlBody.append("<ShipType>");
        //TODO1: 船舶类型要使用IMO要求的类型，已经在报告VO对象中处理
        xmlBody.append(reaportImoRptVo.getShipType());
        xmlBody.append("</ShipType>");
        if("Other".equals(reaportImoRptVo.getShipType())){
            xmlBody.append("<ShipTypeOther>");
            xmlBody.append(reaportImoRptVo.getShipTypeOther());
            xmlBody.append("</ShipTypeOther>");
        }
        xmlBody.append("<ShipGrossTonnage>");
        xmlBody.append(reaportImoRptVo.getGross());
        xmlBody.append("</ShipGrossTonnage>");
        xmlBody.append("<ShipNetTonnage>");
        xmlBody.append(reaportImoRptVo.getNet());
        xmlBody.append("</ShipNetTonnage>");
        xmlBody.append("<ShipDeadweight>");
        xmlBody.append(reaportImoRptVo.getDwt());
        xmlBody.append("</ShipDeadweight>");
        if (reaportImoRptVo.getMainEngines() != null && reaportImoRptVo.getMainEngines().size() > 0) {
            xmlBody.append("<ShipMainPropulsionPowers>");
            //TODO1: 需要处理多种类型的数据，主要针对历史数据，格式：No1: 1020.0 / No2: 1020.0 / No3: 1020.0，已经在VO加工中处理
            for (String item : reaportImoRptVo.getMainEngines()) {
                xmlBody.append("<ShipMainPropulsionPower>");
                xmlBody.append(fmtBigDecimal(item));
                xmlBody.append("</ShipMainPropulsionPower>");
            }
            xmlBody.append("</ShipMainPropulsionPowers>");
        }
        if (reaportImoRptVo.getAuxEngines() != null && reaportImoRptVo.getAuxEngines().size() > 0) {
            xmlBody.append("<ShipAuxiliaryEnginesPowers>");
            //TODO1: 需要处理多种类型的数据，主要针对历史数据，格式：No1: 1020.0 / No2: 1020.0 / No3: 1020.0，已经在VO加工中处理
            for (String item : reaportImoRptVo.getAuxEngines()) {
                xmlBody.append("<ShipAuxiliaryEnginesPower>");
                xmlBody.append(fmtBigDecimal(item));
                xmlBody.append("</ShipAuxiliaryEnginesPower>");
            }
            xmlBody.append("</ShipAuxiliaryEnginesPowers>");
        }
        xmlBody.append("<ShipEEDI>");
        xmlBody.append(reaportImoRptVo.getEedi());
        xmlBody.append("</ShipEEDI>");
        xmlBody.append("<DistanceTravelled>");
        xmlBody.append(reaportImoRptVo.getDistance() == null ? "" : bigDecimal2LongLibya(reaportImoRptVo.getDistance()));
        xmlBody.append("</DistanceTravelled>");
        xmlBody.append("<HoursUnderway>");
        xmlBody.append(bigDecimal2LongLibya(reaportImoRptVo.getDistanceHour()));
        xmlBody.append("</HoursUnderway>");
        if (reaportImoRptVo.getImoOils() != null && reaportImoRptVo.getImoOils().size() > 0) {
            xmlBody.append("<ConsumptionData>");
            for (ImoOilVo item : reaportImoRptVo.getImoOils()) {
                //zxh: 如果燃油消耗量为零，则不要该油种类的节点
                if (item.getQuantity().intValue() == 0) {
                    continue;
                }
                xmlBody.append("<Consumption>");
                xmlBody.append("<Quantity>");
                xmlBody.append(bigDecimal2LongLibya(item.getQuantity()));
                xmlBody.append("</Quantity>");
                xmlBody.append("<Type>");
                //TODO1: 燃油类型要使用标准的燃油类型，已经在VO对象中处理完成
                xmlBody.append(item.getType());
                xmlBody.append("</Type>");
                xmlBody.append("<DataCollectionMethod>");
                //TODO1：燃油消耗计量方法要用IMO的标准叫法，已经在VO对象中处理完成
                xmlBody.append(item.getDataCollectionMethod());
                xmlBody.append("</DataCollectionMethod>");
                xmlBody.append("</Consumption>");
            }
            xmlBody.append("</ConsumptionData>");
        }
        xmlBody.append("</Record>");
        xmlBody.append("</Records>");
        String recordsXml = xmlBody.toString();
        log.info("recordsXml |"+recordsXml);
        //TODO: 使用IMO标准XSD验证DCS上报数据有效性，需要修改方法的返回类型，以支持校验结果向前台传送
        if (!ValidateXML.validate(recordsXml, "/downLoad/imoDcs.xsd")) {

        }
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\r\n" +
                "  <soap12:Header>\r\n" +
                "    <AuthenticationHeader xmlns=\"https://gisis.imo.org/XML/FUEL/2018\">\r\n" +
                "      <AuthorityCode>" + authorityCode + "</AuthorityCode>\r\n" +
                "      <Username>" + username + "</Username>\r\n" +
                "      <Password>" + password + "</Password>\r\n" +
                "    </AuthenticationHeader>\r\n" +
                "  </soap12:Header>\r\n" +
                "  <soap12:Body>\r\n" +
                "    <SubmitRecord xmlns=\"https://gisis.imo.org/XML/FUEL/2018\">\r\n" +
                "      <RecordsXml><![CDATA[" + recordsXml + "]]></RecordsXml>\r\n" +
                "    </SubmitRecord>\r\n" +
                "  </soap12:Body>\r\n" +
                "</soap12:Envelope>";
//        xml = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:ns=\"https://gisis.imo.org/XML/FUEL/2018\">\n" +
//                "   <soap:Header>\n" +
//                "      <ns:AuthenticationHeader>\n" +
//                "         <!--Optional:-->\n" +
//                "         \n" +
//                "         <!--Optional:-->\n" +
//                "         \n" +
//                "         <!--Optional:-->\n" +
//                "         <ns:AuthorityCode>Public</ns:AuthorityCode><ns:Username>ccs_service</ns:Username><ns:Password>54j9tg</ns:Password>\n" +
//                "      </ns:AuthenticationHeader>\n" +
//                "   </soap:Header>\n" +
//                "   <soap:Body>\n" +
//                "      <ns:SubmitRecord>\n" +
//                "         <!--Optional:-->\n" +
//                "         \n" +
//                "      <ns:RecordsXml><![CDATA[" + recordsXml + "]]></ns:RecordsXml></ns:SubmitRecord>\n" +
//                "   </soap:Body>\n" +
//                "</soap:Envelope>";
        return xml;
    }


    private long bigDecimal2LongLibya(BigDecimal x) {
        if (x != null) {
            return x.setScale(0, BigDecimal.ROUND_CEILING).longValue();
        }
        return 0;
    }

    private String fmtBigDecimal(String s) {
        try {
            BigDecimal bd = new BigDecimal(s);
            int intValue = bd.setScale(0, BigDecimal.ROUND_CEILING).intValue();
            return String.valueOf(intValue);// xml格式要求，只取整数部分
        } catch (Exception e) {
            return s;
        }
    }

    private String createLibyaXml(List<ReaportImoRptVo> reaportImoRptVos) {
        StringBuilder xmlBody = new StringBuilder();
        xmlBody.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"yes\"?>");
        xmlBody.append("<Records>");
        for (ReaportImoRptVo reaportImoRptVo : reaportImoRptVos) {

            xmlBody.append("<Record>");
//            xmlBody.append("<Record  ReferenceCode=\"");
//            xmlBody.append(reaportImoRptVo.getRefCode());ShipAuxiliaryEnginesPower
//            xmlBody.append("\">");
            xmlBody.append("<ReportingStartDate>");
            xmlBody.append(reaportImoRptVo.getStartTime());
            xmlBody.append("</ReportingStartDate>");
            xmlBody.append("<ReportingEndDate>");
            xmlBody.append(reaportImoRptVo.getEndTime());
            xmlBody.append("</ReportingEndDate>");
            xmlBody.append("<ShipIMONumber>");
            xmlBody.append(reaportImoRptVo.getImoNu());
            xmlBody.append("</ShipIMONumber>");
            xmlBody.append("<ShipType>");
            xmlBody.append(reaportImoRptVo.getShipType());
            xmlBody.append("</ShipType>");
            if("Other".equals(reaportImoRptVo.getShipType())){
                xmlBody.append("<ShipTypeOther>");
                xmlBody.append(reaportImoRptVo.getShipType());
                xmlBody.append("</ShipTypeOther>");
            }
            xmlBody.append("<ShipGrossTonnage>");
            xmlBody.append(bigDecimal2LongLibya(reaportImoRptVo.getGross()));
            xmlBody.append("</ShipGrossTonnage>");

            //fix by cg 20210301
            if (reaportImoRptVo.getNet() != null &&  reaportImoRptVo.getNet().compareTo(BigDecimal.ZERO) != 0) {
                xmlBody.append("<ShipNetTonnage>");
                xmlBody.append(reaportImoRptVo.getNet());
                xmlBody.append("</ShipNetTonnage>");
            }


            xmlBody.append("<ShipDeadweight>");
            xmlBody.append(bigDecimal2LongLibya(reaportImoRptVo.getDwt()));
            xmlBody.append("</ShipDeadweight>");
            if (reaportImoRptVo.getMainEngines() != null && reaportImoRptVo.getMainEngines().size() > 0) {
                xmlBody.append("<ShipMainPropulsionPowers>");
                for (String item : reaportImoRptVo.getMainEngines()) {
                    xmlBody.append("<ShipMainPropulsionPower>");
                    xmlBody.append(fmtBigDecimal(item));
                    xmlBody.append("</ShipMainPropulsionPower>");
                }
                xmlBody.append("</ShipMainPropulsionPowers>");
            }
            if (reaportImoRptVo.getAuxEngines() != null && reaportImoRptVo.getAuxEngines().size() > 0) {
                xmlBody.append("<ShipAuxiliaryEnginesPowers>");
                for (String item : reaportImoRptVo.getAuxEngines()) {
                    xmlBody.append("<ShipAuxiliaryEnginesPower>");
                    xmlBody.append(fmtBigDecimal(item));
                    xmlBody.append("</ShipAuxiliaryEnginesPower>");
                }
                xmlBody.append("</ShipAuxiliaryEnginesPowers>");
            }

            if (reaportImoRptVo.getEedi() != null) {
                xmlBody.append("<ShipEEDI>");
                xmlBody.append(reaportImoRptVo.getEedi());
                xmlBody.append("</ShipEEDI>");
            }

            xmlBody.append("<ShipIceClass>");
            xmlBody.append("</ShipIceClass>");
            xmlBody.append("<DistanceTravelled>");
            xmlBody.append(reaportImoRptVo.getDistance() == null ? "" : bigDecimal2LongLibya(reaportImoRptVo.getDistance()));
            xmlBody.append("</DistanceTravelled>");
            xmlBody.append("<HoursUnderway>");
            xmlBody.append(reaportImoRptVo.getDistanceHour() == null ? "" : bigDecimal2LongLibya(reaportImoRptVo.getDistanceHour()));
            xmlBody.append("</HoursUnderway>");
            if (reaportImoRptVo.getImoOils() != null && reaportImoRptVo.getImoOils().size() > 0) {
                xmlBody.append("<ConsumptionData>");
                for (ImoOilVo item : reaportImoRptVo.getImoOils()) {
                    if (item.getQuantity() == null || item.getQuantity().intValue() == 0) {
                        continue;
                    }
                    xmlBody.append("<Consumption>");
                    xmlBody.append("<Quantity>");
                    xmlBody.append(Math.abs(bigDecimal2LongLibya(item.getQuantity())));
                    xmlBody.append("</Quantity>");
                    xmlBody.append("<Type>");
                    xmlBody.append(item.getType());
                    xmlBody.append("</Type>");
                    xmlBody.append("<DataCollectionMethod>");
                    xmlBody.append(item.getDataCollectionMethod());
                    xmlBody.append("</DataCollectionMethod>");
                    xmlBody.append("</Consumption>");
                }
                xmlBody.append("</ConsumptionData>");
            }
            xmlBody.append("</Record>");

        }
        xmlBody.append("</Records>");
        return xmlBody.toString();
    }

    public String downloadLibyaReports(List<ReaportImoRptVo> reaportImoRptVos) {
        Long random = System.currentTimeMillis();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        String xmlStr = createLibyaXml(reaportImoRptVos);
        File dir = new File(ftpProperties.getLocalPath());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = "LibyaReports-" + random + ".xml";
        File file = new File(ftpProperties.getLocalPath() + fileName);
//        String desktop = "C:\\Users\\bahua\\Desktop\\tem";
//        File file = new File(desktop + fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileUtils.writeStringToFile(file, xmlStr, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftpProperties.getDevProdPath() + "/mrv/downloadFile?fileName=" + fileName;
        ///api/mrv/downloadFile?fileName=LibyaReports-1595557683260.xml
    }

    public void downloadFile(String fileName, HttpServletResponse response) {
        // 发送给客户端的数据
        try {
            String type = new MimetypesFileTypeMap().getContentType(fileName);
            response.addHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", type);
            OutputStream outputStream = response.getOutputStream();
            byte[] buff = new byte[1024];
            FileInputStream bis = null;
            // 读取filename
            bis = new FileInputStream(new File(ftpProperties.getLocalPath() + fileName));
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = bis.read(b)) > 0) {
                outputStream.write(b, 0, i);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ImoReportClient imoReportClient = new ImoReportClient();
//        imoReportClient.downloadLibyaReports(null);
        long libya = imoReportClient.bigDecimal2LongLibya(new BigDecimal(13.22));
        System.out.println(libya);
    }
}
