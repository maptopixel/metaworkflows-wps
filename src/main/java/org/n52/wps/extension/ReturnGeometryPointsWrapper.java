package org.n52.wps.extension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.n52.wps.io.data.IData;
import org.n52.wps.io.data.binding.literal.LiteralStringBinding;
import org.n52.wps.server.AbstractSelfDescribingAlgorithm;
import org.n52.wps.server.ExceptionReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Simple WPS wrapper that executes a WPS 
 * 
 * @author Julian Rosser
 *
 */
public class ReturnGeometryPointsWrapper extends AbstractSelfDescribingAlgorithm {

	private static final Logger logger = LoggerFactory.getLogger(ReturnGeometryPointsWrapper.class);
					
	String wpsURL = "http://localhost:8010/wps/WebProcessingService?";
	String wpsProcessID = "org.n52.wps.server.r.test.return.geometry.points";
	
	private static final boolean useGeoNetworkFromWrapper = true;
	
	static final String INPUT = "data"; //was inputString
	static final String INPUT_ATTRIBUTE = "attributename"; //was inputString
	static final String OUTPUT = "out"; // was resultOutput

	public Class<?> getInputDataType(String identifier) {
		if (identifier.equals(INPUT)) {
			return LiteralStringBinding.class;
		}
		if (identifier.equals(INPUT_ATTRIBUTE)) {
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
		logger.info("Executing WPS process...");	
		
		logger.info("this.getWellKnownName() " + this.getWellKnownName());		
		logger.info("this.getDescription(): " + this.getDescription().toString());
					
		//Call the shim as adapter for wps request building and catalogue query
		WPSClientAndCatalogueShim shimResult = new WPSClientAndCatalogueShim( data, wpsURL, wpsProcessID, useGeoNetworkFromWrapper);
		Map<String,IData> result = shimResult.result;
	
		return result;
	}
 	    

	
	@Override
	public List<String> getInputIdentifiers() {
		List<String> list = new ArrayList();
		list.add(INPUT);
		list.add( INPUT_ATTRIBUTE);
		return list;
	}

	@Override
	public List<String> getOutputIdentifiers() {
		List<String> list = new ArrayList();
		list.add(OUTPUT);
		return list;
	}
	
}
