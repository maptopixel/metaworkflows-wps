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
					
		//create the map for the result
		//Map<String,IData> result = new HashMap<String, IData>();				
		//result = wpsClientAndCatalogueShim( result,  data, wpsURL, wpsProcessID, useGeoNetworkFromWrapper);
		
		//Call the shim as adapter for wps request building and catalogue query
		WPSClientAndCatalogueShim shimResult = new WPSClientAndCatalogueShim( data, wpsURL, wpsProcessID, useGeoNetworkFromWrapper);
		Map<String,IData> result = shimResult.result;
	
		return result;
	}
 	    
	
	/*
	public Map<String,IData> wpsClientAndCatalogueShim( Map<String,IData> resultHolder,  Map<String, List<IData>> data, String wpsURL, String wpsProcessID, boolean useGeoNetworkFromWrapper){		
		//Wrangle the data into a HashMap suitable for use in GenericWPSClient
		HashMap<String, Object> variables= new HashMap<String, Object>();
		logger.info("Starting loop to create <String,Object> hashmap from the inputs");
		logger.info("size of data map: " +data.size());
		for (Entry<String, List<IData>> entry : data.entrySet()) {			
		    logger.info("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		    String inputValue2 = entry.getValue().get(0).getPayload().toString();
		    logger.info("input value2: " + inputValue2);
		    
		    if (inputValue2.toLowerCase().contains("geonetwork") & useGeoNetworkFromWrapper){
		    	logger.info("GeoNetwork: Calling MetaWorkflow from wrapper");
		    	MetaWorkflow metaWorkflow = new MetaWorkflow();
		    	Element retrievedElement = null;
				try {
					retrievedElement = metaWorkflow.GetMetadata((String)inputValue2);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			        
				//get the location of the retrieved element
		        String Urllocation = metaWorkflow.getLocationElement(retrievedElement).getText();				        
			    logger.info("GeoNetwork: UrlLocation for request " + Urllocation);
		        inputValue2 = Urllocation;
		    }		    
		    
		    variables.put(entry.getKey(), inputValue2); 
		}		
		
				
		//Execute the actual WPS process    
    	logger.info("Starting WPS call");
		GenericWPSClient wpsClientWrapper = new GenericWPSClient(wpsURL, wpsProcessID, variables, null);
		HashMap<String, Object> results = wpsClientWrapper.getOutputs();
							
		//Bubble up the WPS outputs into this WPS
		logger.info("size of results map: " + results.size());
		for (Entry<String, Object> entry : results.entrySet()) {
			logger.info("results key: " + entry.getKey());
			logger.info("results value: " + entry.getValue());			
			LiteralStringBinding resultBinding = new LiteralStringBinding((String)entry.getValue());
			resultHolder.put(entry.getKey(), resultBinding);
		}
		logger.info("Completed WPS wrapper execute");
		return resultHolder;
	}//end of wrangler
	
	*/
	
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
