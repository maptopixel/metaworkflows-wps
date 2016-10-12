package org.n52.wps.extension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.n52.wps.io.data.IData;
import org.n52.wps.io.data.binding.literal.LiteralStringBinding;
import org.n52.wps.server.AbstractSelfDescribingAlgorithm;
import org.n52.wps.server.ExceptionReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cobweb.m24.GenericWPSClient;


/**
 * Simple WPS wrapper that executes a WPS 
 * 
 * @author Julian Rosser
 *
 */
public class GeoServerBufferWrapper extends AbstractSelfDescribingAlgorithm {

	private static final Logger logger = LoggerFactory.getLogger(GeoServerBufferWrapper.class);
					
	String wpsURL = "http://localhost:8000/geoserver/ows?";
	String wpsProcessID = "gs:BufferFeatureCollection";
	
	private static final boolean useGeoNetwork = true;
	
	static final String INPUT = "features"; //was inputString
	static final String INPUT_ATTRIBUTE = "distance"; //was inputString
	static final String OUTPUT = "result"; // was resultOutput

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
					
		//create the map for the result
		Map<String,IData> result = new HashMap<String, IData>();
		
		//Wrangle the data into a HashMap suitable for use in GenericWPSClient
		HashMap<String, Object> variables= new HashMap<String, Object>();
		logger.info("Starting loop to create <String,Object> hashmap from the inputs");
		logger.info("size of data map: " +data.size());
		for (Entry<String, List<IData>> entry : data.entrySet()) {			
		    logger.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		    String inputValue2 = entry.getValue().get(0).getPayload().toString();
		    logger.info("input value2: " + inputValue2);
		    variables.put(entry.getKey(), inputValue2); 
		}
				
		//Execute the actual WPS process    
    	logger.info("Starting WPS call");
		GenericWPSClient wpsClient = new GenericWPSClient(wpsURL, wpsProcessID, variables, null,useGeoNetwork);
		HashMap<String, Object> results = wpsClient.getOutputs();
							
		//Bubble up the WPS outputs into this WPS
		logger.info("size of results map: " + results.size());
		for (Entry<String, Object> entry : results.entrySet()) {
			logger.info("results key: " + entry.getKey());
			logger.info("results value: " + entry.getValue());			
			LiteralStringBinding resultBinding = new LiteralStringBinding((String)entry.getValue());
			result.put(entry.getKey(), resultBinding);
		}
		logger.info("Completed WPS wrapper execute");	
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
