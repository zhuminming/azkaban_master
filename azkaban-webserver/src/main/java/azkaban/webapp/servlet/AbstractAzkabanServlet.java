/*
 * Copyright 2012 LinkedIn Corp.
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

package azkaban.webapp.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.azkaban.common.server.AzkabanServer;
import org.azkaban.common.utils.Props;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import azkaban.webapp.AzkabanWebServer;


/**
 * Base Servlet for pages
 */
public abstract class AbstractAzkabanServlet extends HttpServlet {
  private static final DateTimeFormatter ZONE_FORMATTER = DateTimeFormat
      .forPattern("z");

  private static final long serialVersionUID = -1;
  public static final String LOG_URL_PREFIX = "log_url_prefix";
  public static final String HTML_TYPE = "text/html";
  public static final String XML_MIME_TYPE = "application/xhtml+xml";
  public static final String JSON_MIME_TYPE = "application/json";

  private AzkabanServer application;
  private String name;
  private String label;
  private String color;


  /**
   * To retrieve the application for the servlet
   *
   * @return
   */
  public AzkabanServer getApplication() {
    return application;
  }

  /**
   * Creates a new velocity page to use.
   *
   * @param req
   * @param resp
   * @param template
   * @return
   */
  protected Page newPage(HttpServletRequest req, HttpServletResponse resp,
      String template) {
	VelocityEngine ve = new VelocityEngine();
    Page page = new Page(req, resp, ve, template);
    page.add("azkaban_name", name);
    page.add("azkaban_label", label);
    page.add("azkaban_color", color);
    page.add("timezone", ZONE_FORMATTER.print(System.currentTimeMillis()));
    page.add("currentTime", (new DateTime()).getMillis());
    page.add("context", req.getContextPath());


    return page;
  }
}
