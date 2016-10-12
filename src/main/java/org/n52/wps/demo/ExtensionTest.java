package org.n52.wps.demo;

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
public class ExtensionTest extends AbstractSelfDescribingAlgorithm {

	static final String INPUT = "inputString";
	static final String OUTPUT = "resultOutput";

	public Class<?> getInputDataType(String identifier) {
		if (identifier.equals(INPUT)) {
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
		
		System.out.println("Jules test algo");
		result.put(OUTPUT, data.get(INPUT).get(0));
		return result;
	}

	@Override
	public List<String> getInputIdentifiers() {
		return Collections.singletonList(INPUT);
	}

	@Override
	public List<String> getOutputIdentifiers() {
		return Collections.singletonList(OUTPUT);
	}

}
