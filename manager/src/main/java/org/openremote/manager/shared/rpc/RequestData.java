package org.openremote.manager.shared.rpc;

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.HttpHeaders;

/**
 * TODO https://issues.jboss.org/browse/RESTEASY-1315
 */
@JsType
public class RequestData {
    @HeaderParam(HttpHeaders.AUTHORIZATION)
    @JsProperty(name = HttpHeaders.AUTHORIZATION)
    public String authorization;

    @HeaderParam("XSRF")
    @JsProperty(name= "XSRF")
    public String xsrfToken;

    @JsProperty(name = "$callback")
    public Callback callback;

    @JsProperty
    public String entity;

    @JsProperty(name = "$contentType")
    public String contentType;

    @JsProperty(name = "$accepts")
    public String accepts;

    @JsProperty(name = "$apiURL")
    public String apiURL;

    @JsProperty(name = "$username")
    public String username;

    @JsProperty(name = "$password")
    public String password;
}