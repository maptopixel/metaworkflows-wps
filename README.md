# WPS Wrappers using 52°North

This repo is based on the WPS-extension-Skeleton project on 52North.

#WPS Wrapper deployment instructions

Pull and import the project to Eclipse

Need to include other projects from:

https://bitbucket.org/julianrosser/qa-workflow-jclone

https://github.com/maptopixel/geonetwork-manage

Check the branch is the metaworkflow / metagetset2 branches

Export the jar of the wrapper-process-extensions (ie this repo) and probably need to tick the other repos to ensure the libraries are available from the WPS environment.



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
