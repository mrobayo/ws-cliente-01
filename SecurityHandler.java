/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uees.taller72;

import java.util.Collections;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 *
 * @author janito
 */
public class SecurityHandler implements SOAPHandler<SOAPMessageContext> {

    public boolean handleMessage(SOAPMessageContext smc) {
        //SOAPMessage msg = smc.getMessage();

        Boolean outboundProperty = (Boolean) smc
                .get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty.booleanValue()) {

            SOAPMessage message = smc.getMessage();

            try {

                SOAPEnvelope envelope = smc.getMessage().getSOAPPart()
                        .getEnvelope();
                SOAPHeader header = envelope.getHeader();//.addHeader();

                // get SOAP envelope from SOAP message
                SOAPElement security = header
                        .addChildElement(
                                "Security",
                                "wss",
                                "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");

                SOAPElement timeStamp = security
                        .addChildElement(
                                "Timestamp",
                                "wsu",
                                "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");

                SOAPElement createdTime = timeStamp.addChildElement("Created",
                        "wsu");
                createdTime.addTextNode("2012-12-07T13:40:21Z");

                SOAPElement expires = timeStamp.addChildElement("Expires",
                        "wsu");
                expires.addTextNode("2019-12-07T13:40:21Z");

                SOAPElement usernameToken = security.addChildElement(
                        "UsernameToken", "wss");
                // usernameToken.addAttribute(new QName("xmlns:wsu"),
                // "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");

                SOAPElement username = usernameToken.addChildElement(
                        "Username", "wss");
                username.addTextNode("USUARIO");

                SOAPElement password = usernameToken.addChildElement(
                        "Password", "wss");
                password.setAttribute(
                        "Type",
                        "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest");
                password.addTextNode("Qro+U/5Swf50Pt04i4WS/PsbljY=");
                // password.addTextNode("Qro+U/5Swf50Pt04i4WS/PsbljY=");

                SOAPElement nonce = usernameToken.addChildElement("Nonce",
                        "wss");
                nonce.setAttribute(
                        "EncodingType",
                        "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
                nonce.addTextNode("He+y+afyHy7lWugWrn6LBQ==");
                // nonce.addTextNode("He+y+afyHy7lWugWrn6LBQ==");

                SOAPElement created = usernameToken
                        .addChildElement(
                                "Created",
                                "wsu",
                                "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
                created.addTextNode("2012-12-07T13:40:21Z");
                // created.addTextNode("2012-12-07T13:40:21Z");

                // Print out the outbound SOAP message to System.out
                message.writeTo(System.out);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {

                // This handler does nothing with the response from the Web
                // Service so
                // we just print out the SOAP message.
                SOAPMessage message = smc.getMessage();
                message.writeTo(System.out);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return outboundProperty;

//        System.out.println("************************ edu.uees.taller72.SecurityHandler.handleMessage()");
//        return true;
    }

    public Set<QName> getHeaders() {
        System.out.println("************************  edu.uees.taller72.SecurityHandler.getHeaders()");
        return Collections.EMPTY_SET;
    }

    public boolean handleFault(SOAPMessageContext messageContext) {
        System.out.println("************************ edu.uees.taller72.SecurityHandler.handleFault()");
        return true;
    }

    public void close(MessageContext context) {
        System.out.println("************************ edu.uees.taller72.SecurityHandler.close()");
    }

}
