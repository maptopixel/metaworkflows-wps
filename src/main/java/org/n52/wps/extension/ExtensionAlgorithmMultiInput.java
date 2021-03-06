/**
 * Copyright (C) 2013
 * by 52 North Initiative for Geospatial Open Source Software GmbH
 *
 * Contact: Andreas Wytzisk
 * 52 North Initiative for Geospatial Open Source Software GmbH
 * Martin-Luther-King-Weg 24
 * 48155 Muenster, Germany
 * info@52north.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.n52.wps.extension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.n52.wps.io.data.IData;
import org.n52.wps.io.data.binding.literal.LiteralStringBinding;
import org.n52.wps.server.AbstractSelfDescribingAlgorithm;
import org.n52.wps.server.ExceptionReport;

/**
 * Simple mockup algorithm doing nothing special.
 * 
 * @author matthes rieke
 *
 */
public class ExtensionAlgorithmMultiInput extends AbstractSelfDescribingAlgorithm {

	static final String INPUT = "inputString";
	static final String INPUT2 = "inputString2";
	static final String OUTPUT = "resultOutput";

	public Class<?> getInputDataType(String identifier) {
		if (identifier.equals(INPUT)) {
			return LiteralStringBinding.class;
		}
		if (identifier.equals(INPUT2)) {
			return LiteralStringBinding.class;
		}
		return null;
	}

	public Class<?> getOutputDataType(String identifier) {
		if (identifier.equals(OUTPUT)) {
			return LiteralStringBinding.class;
		}
		return null;
	}

	public Map<String, IData> run(Map<String, List<IData>> data)
			throws ExceptionReport {
		Map<String,IData> result = new HashMap<String, IData>();
		
		String tempString = data.get(INPUT).get(0).getPayload().toString();		
		String tempString2 = data.get(INPUT2).get(0).getPayload().toString();
		
		LiteralStringBinding stringBinding = new LiteralStringBinding(tempString + " " + tempString2);
		
		result.put(OUTPUT, stringBinding);
		return result;
	}

	@Override
	public List<String> getInputIdentifiers() {
		List<String> list = new ArrayList();
		list.add(INPUT);
		list.add(INPUT2);
		return list;
	}

	@Override
	public List<String> getOutputIdentifiers() {
		return Collections.singletonList(OUTPUT);
	}

}
