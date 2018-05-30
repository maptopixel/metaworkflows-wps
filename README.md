# WPS Wrappers using 52°North

This repo is based on the WPS-extension-Skeleton project on 52North for creating WPS. It provides wrappers to enable a WPS workflow to execute process workflows using metadata objects stored in a GeoNetwork instance. This repository does therefore rely on setting up the (Workflow-AT)[https://github.com/cobweb-eu/workflow-at] tool and a library for [parsing/querying GeoNetwork]( https://github.com/maptopixel/geonetwork-manager)

## WPS Wrapper deployment instructions

1. Get familiar with https://github.com/cobweb-eu/workflow-at first by following that readme. 

2. When confident, swith to using the '''metagetset2''' branch

3. Pull and import this '''metaworkflows-wps''' project to Eclipse and pull and import https://github.com/maptopixel/geonetwork-manager

Export the jar of the wrapper-process-extensions (i.e. this repo) and probably need to tick the other repos to ensure the libraries are available from the WPS environment.


## Adding a Java Algorithm to your WPS instance

There are two steps which you need to fulfill

1. Copy the resulting jar to (WPS-deployment-directory)/WEB-INF/lib
2. Add your new Algorithms to the WPS config (in (WPS-deployment-directory)/config/wps_config.xml)

The second step could look like:

```xml
...
<Repository name="LocalAlgorithmRepository"
	className="org.n52.wps.server.LocalAlgorithmRepository" active="true">
	<Property name="Algorithm" active="true">org.n52.wps.extension.ExtensionAlgorithm</Property>
	<Property name="Algorithm" active="true">org.n52.wps.extension.AnnotatedExtensionAlgorithm</Property>
	<Property name="Algorithm" active="true">org.n52.wps.server.algorithm.SimpleBufferAlgorithm</Property>
  ...
</Repository>
...
```
### Only if you are using WPS 3.2.0 and later

If you add the fully qualified name of you algorithm to the file:

``/META-INF/services/org.n52.wps.server.IAlgorithm``

and drop the jar to (WPS-deployment-directory)/WEB-INF/lib, your algorithm will be added to the WPS automatically.
