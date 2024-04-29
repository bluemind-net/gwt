/*
* Copyright 2023 Google Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License"); you may not
* use this file except in compliance with the License. You may obtain a copy of
* the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
* WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
* License for the specific language governing permissions and limitations under
* the License.
*/
package com.google.gwt.dev.shell.jetty;

import com.google.gwt.core.ext.TreeLogger;


import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Response;

import org.eclipse.jetty.util.component.AbstractLifeCycle;


/**
 * Log jetty requests/responses to TreeLogger.
 */
public class JettyRequestLogger extends AbstractLifeCycle implements
    RequestLog {

  private final TreeLogger logger;
  private final TreeLogger.Type normalLogLevel;

  public JettyRequestLogger(TreeLogger logger, TreeLogger.Type normalLogLevel) {
    this.logger = logger;
    assert (normalLogLevel != null);
    this.normalLogLevel = normalLogLevel;
  }

  /**
   * Log an HTTP request/response to TreeLogger.
   */
  public void log(Request request, Response response) {
    int status = response.getStatus();
    if (status < 0) {
      // Copied from NCSARequestLog
      status = 404;
    }
    // if (status != 404) {
    //   // Ignore 404 errors, log the first other call to the server if we haven't logged yet
    //   maybeLogDeprecationWarning(logger);
    // }
    TreeLogger.Type logStatus, logHeaders;
    if (status >= 500) {
      logStatus = TreeLogger.ERROR;
      logHeaders = TreeLogger.INFO;
    } else if (status == 404) {
      if ("/favicon.ico".equals(request.getHttpURI())
          && request.getHttpURI().getQuery() == null) {
        /*
          * We do not want to call the developer's attention to a 404 when
          * requesting favicon.ico. This is a very common 404.
          */
        logStatus = TreeLogger.TRACE;
        logHeaders = TreeLogger.DEBUG;
      } else {
        logStatus = TreeLogger.WARN;
        logHeaders = TreeLogger.INFO;
      }
    } else if (status >= 400) {
      logStatus = TreeLogger.WARN;
      logHeaders = TreeLogger.INFO;
    } else {
      logStatus = normalLogLevel;
      logHeaders = TreeLogger.DEBUG;
    }

    String userString = Request.getAuthenticationState(request).getUserPrincipal().getName();
    if (userString == null) {
      userString = "";
    } else {
      userString += "@";
    }
    String bytesString = "";
    if (Response.getContentBytesWritten(response) > 0) {
      bytesString = " " + Response.getContentBytesWritten(response) + " bytes";
    }
    if (logger.isLoggable(logStatus)) {
      TreeLogger branch = logger.branch(logStatus, String.valueOf(status)
          + " - " + request.getMethod() + ' ' + request.getHttpURI() + " ("
          + userString + Request.getRemoteAddr(request) + ')' + bytesString);
      if (branch.isLoggable(logHeaders)) {
        logHeaders(branch.branch(logHeaders, "Request headers"), logHeaders,
            request.getHeaders());
        logHeaders(branch.branch(logHeaders, "Response headers"), logHeaders,
            null);
      }
    }
  }

  private void logHeaders(TreeLogger logger, TreeLogger.Type logLevel, HttpFields fields) {
    for (int i = 0; i < fields.size(); ++i) {
      HttpField field = fields.getField(i);
      logger.log(logLevel, field.getName() + ": " + field.getValue());
    }
  }
}