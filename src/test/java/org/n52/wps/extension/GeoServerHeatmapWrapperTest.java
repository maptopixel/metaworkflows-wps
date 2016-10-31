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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.n52.wps.io.data.IData;
import org.n52.wps.io.data.binding.literal.LiteralStringBinding;
import org.n52.wps.server.ExceptionReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoServerHeatmapWrapperTest {
	
	private static final Logger logger = LoggerFactory.getLogger(GeoServerHeatmapWrapperTest.class);

	@Test
	public void testAlgo() throws ExceptionReport {
		Map<String, List<IData>> map = readData();
		
		GeoServerHeatmapWrapper algo = new GeoServerHeatmapWrapper();
		Map<String, IData> result = algo.run(map);
		
		Assert.assertTrue("Result not available!", result.get(GeoServerHeatmapWrapper.OUTPUT) != null);
		logger.info("Got result: '{}'", result.get(GeoServerHeatmapWrapper.OUTPUT).getPayload().toString());
	}

	private Map<String, List<IData>> readData() {

		LiteralStringBinding stringBinding = new LiteralStringBinding("http://geoprocessing.westus.cloudapp.azure.com:8082/geoserver/geomesa/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=geomesa:brext_event&maxFeatures=50");
		//LiteralStringBinding stringBinding = new LiteralStringBinding("http://localhost:8000/geoserver/sf/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=sf:archsites&maxFeatures=50");
		//LiteralStringBinding stringBinding = new LiteralStringBinding("http://localhost:8005/geonetwork/srv/eng/xml.metadata.get?id=39836");

		LiteralStringBinding stringBindingRadius = new LiteralStringBinding("50");
		

		LiteralStringBinding stringBinding2 = new LiteralStringBinding("100");
		LiteralStringBinding stringBinding3 = new LiteralStringBinding("2000");
		LiteralStringBinding stringBinding4 = new LiteralStringBinding("1000");
		                            		
		List<IData> inputDataList1 = new ArrayList<IData>();
		inputDataList1.add(stringBinding);
		
		List<IData> inputDataListRadius = new ArrayList<IData>();
		inputDataListRadius.add(stringBindingRadius);
		
		List<IData> inputDataList2 = new ArrayList<IData>();
		inputDataList2.add(stringBinding2);
		
		List<IData> inputDataList3 = new ArrayList<IData>();
		inputDataList3.add(stringBinding3);
		
		List<IData> inputDataList4 = new ArrayList<IData>();
		inputDataList4.add(stringBinding4);
		
		
		
		Map<String,List<IData>> map = new HashMap<String, List<IData>>();
		map.put(GeoServerHeatmapWrapper.INPUT, inputDataList1);
		map.put(GeoServerHeatmapWrapper.INPUT_ATTRIBUTE, inputDataListRadius);
		map.put(GeoServerHeatmapWrapper.INPUT_ATTRIBUTE2, inputDataList2);
		map.put(GeoServerHeatmapWrapper.INPUT_ATTRIBUTE3, inputDataList3);
		map.put(GeoServerHeatmapWrapper.INPUT_ATTRIBUTE4, inputDataList4);
		
		return map;
	}
	
}
