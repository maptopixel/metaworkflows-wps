package org.n52.wps.extension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jdom.Element;
import org.n52.wps.io.data.IData;
import org.n52.wps.io.data.binding.literal.LiteralStringBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cobweb.m24.GenericWPSClient;

import com.metaworkflows.MetaWorkflow;

public class WPSClientAndCatalogueShim {
	
	private static final Logger logger = LoggerFactory.getLogger(WPSClientAndCatalogueShim.class);
	
	public Map<String,IData> result = new HashMap<String, IData>();
	 
	public WPSClientAndCatalogueShim( Map<String, List<IData>> data, String wpsURL, String wpsProcessID, boolean useGeoNetworkFromWrapper){		
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
			result.put(entry.getKey(), resultBinding);
		}
		logger.info("Completed WPS wrapper execute");

		//result = resultHolder;
	}//end of wrangler
	
	
}
