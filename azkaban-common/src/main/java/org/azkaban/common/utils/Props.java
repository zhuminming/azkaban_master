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

package org.azkaban.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

public class Props {
    private final Map<String, String> _current;
    private Props _parent;
    private String source = null;

    /**
     * Constructor for empty props with empty parent.
     */
    public Props() {
	this(null);
    }

    /**
     * Constructor for empty Props with parent override.
     *
     * @param parent
     */
    public Props(Props parent) {
	this._current = new HashMap<String, String>();
	this._parent = parent;
    }

    public Props(Props parent, File file) {
	this(parent);
	setSource(file.getPath());
	try {
	    InputStream input = new BufferedInputStream(new FileInputStream(
		    file));
	    Properties propertie = new Properties();
	    propertie.load(input);
	    for (String proName : propertie.stringPropertyNames()) {
		_current.put(proName, propertie.getProperty(proName));
	    }

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public Props(Props parent, String filePath) {
	this(parent, new File(filePath));
    }

    public Props get_parent() {
	return _parent;
    }

    public void set_parent(Props _parent) {
	this._parent = _parent;
    }

    public String getSource() {
	return source;
    }

    public void setSource(String source) {
	this.source = source;
    }

    public Map<String, String> get_current() {
	return _current;
    }

    public String getString(String key) {
	return get(key);
    }

    public String getString(String key, String defalutValue) {
	if (containsKey(key)) {
	    return get(key);
	}
	return defalutValue;
    }

    public Integer getInt(String key) {
	return get(key) == null ? -1 : Integer.parseInt(get(key));
    }

    private String get(Object key) {
	if (_current.containsKey(key)) {
	    return _current.get(key);
	} else if (_parent != null) {
	    return _parent.get(key);
	} else {
	    return null;
	}
    }

    public boolean containsKey(Object key) {
	return _current.containsKey(key)
		|| (_parent != null && _parent.containsKey(key));
    }
}
