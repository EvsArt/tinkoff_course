package edu.project3.pojo;

import java.net.InetAddress;
import java.time.LocalDateTime;

public class LogInfo {

    public LogInfo() {
    }

    private InetAddress clientAddress;
    private LocalDateTime dateTime;
    private RequestMethod method;
    private String resource;
    private String protocol;
    private int responseStatus;
    private int bodyBytesSent;
    private String httpReferer;
    private String userAgent;

    public LogInfo setClientAddress(InetAddress clientAddress) {
        this.clientAddress = clientAddress;
        return this;
    }

    public LogInfo setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public LogInfo setMethod(RequestMethod method) {
        this.method = method;
        return this;
    }

    public LogInfo setResource(String resource) {
        this.resource = resource;
        return this;
    }

    public LogInfo setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public LogInfo setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
        return this;
    }

    public LogInfo setBodyBytesSent(int bodyBytesSent) {
        this.bodyBytesSent = bodyBytesSent;
        return this;
    }

    public LogInfo setHttpReferer(String httpReferer) {
        this.httpReferer = httpReferer;
        return this;
    }

    public LogInfo setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public InetAddress getClientAddress() {
        return clientAddress;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getResource() {
        return resource;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public int getBodyBytesSent() {
        return bodyBytesSent;
    }

    public String getHttpReferer() {
        return httpReferer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    @Override
    public boolean equals(Object obj) {
        LogInfo obj2 = (LogInfo) obj;
        return clientAddress.equals(obj2.clientAddress)
            && dateTime.equals(obj2.dateTime)
            && method.equals(obj2.method)
            && resource.equals(obj2.resource)
            && protocol.equals(obj2.protocol)
            && responseStatus == obj2.responseStatus
            && bodyBytesSent == obj2.bodyBytesSent
            && httpReferer.equals(obj2.httpReferer)
            && userAgent.equals(obj2.userAgent);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public enum RequestMethod {
        GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;

        public static RequestMethod stringToRequestMethod(String str) {
            return switch (str) {
                case "GET" -> GET;
                case "HEAD" -> HEAD;
                case "POST" -> POST;
                case "PUT" -> PUT;
                case "PATCH" -> PATCH;
                case "DELETE" -> DELETE;
                case "OPTIONS" -> OPTIONS;
                case "TRACE" -> TRACE;
                case null, default -> null;
            };
        }

    }

}
