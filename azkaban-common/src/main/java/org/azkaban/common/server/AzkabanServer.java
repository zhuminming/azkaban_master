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

package org.azkaban.common.server;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.azkaban.common.utils.Props;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.server.Operation;
import java.util.Arrays;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

public abstract class AzkabanServer {
	private static final Logger logger = Logger.getLogger(AzkabanServer.class);
	public static final String AZKABAN_PROPERTIES_FILE = "azkaban.properties";
	public static final String AZKABAN_PRIVATE_PROPERTIES_FILE = "azkaban.private.properties";
	public static final String DEFAULT_CONF_PATH = "conf";

	public static Props loadProps(String path) {
		return loadProps(path, new OptionParser());
	}

	public static Props loadProps(String path, OptionParser parser) {

		// Grabbing the azkaban settings from the conf directory.
		Props azkabanSettings = null;

		File file=new File(path+"/"+AZKABAN_PROPERTIES_FILE);
		System.out.println(file.getPath());
		azkabanSettings = new Props(azkabanSettings, file);

		return azkabanSettings;
	}


}
