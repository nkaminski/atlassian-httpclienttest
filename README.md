### What is this repository for? ###

1. This tool is to check if your JIRA instance is affected by the bug [JRA-47568](https://jira.atlassian.com/browse/JRA-47568) and should be used in conjunction with the SSLPoke tool.
1. This tool can also be used to help identify intermediary HTTP 302 redirect/intercept by a Load Balancer, SSO, Proxy, etc..

### How can I download and use the tool? ###

1. Download the [JAR File](https://bitbucket.org/atlassianlabs/httpclienttest/downloads/httpclienttest-1.0.2.jar)
2. Run the tool with the following command, replacing <TARGET-SERVER> and <TARGET-PORT> with your Server's hostname and port respectively. 
```sh
        java -jar httpclienttest-1.0.2.jar <TARGET-SERVER>:<TARGET-PORT>
```
	
### How do I use my own truststore with this tool? ###
Run the command with additional JVM arguments as below: 
```sh
	java -Djavax.net.ssl.trustStore=/path/to/truststore.jks -jar httpclienttest-1.0.2.jar https://jira.atlassian.com:443 
```

### How do I use an outbound proxy with this tool? ###
If the proxy is configured in environmental settings the JVM should pick it up, otherwise run it as below:
```sh
   java -Dhttp.proxyHost=proxy -Dhttp.proxyPort=3128 -Dhttps.proxyHost=proxy -Dhttps.proxyPort=3128 -jar httpclienttest-1.0.2.jar https://jira.atlassian.com:443 
```

### What is the expected output ###

The expected output i.e. either a PKIX exception, indicating that your certificate is affected by the SNI bug, or you have a misconfigured truststore. The tool contains Apache HttpClient logging at DEBUG level so will be verbose.

### How do I know if my truststore is ok? ###

The [SSLPoke test)(https://confluence.atlassian.com/kb/unable-to-connect-to-ssl-services-due-to-pkix-path-building-failed-779355358.html) does not use the Apache HTTPClient libraries and therefore will connect successfully if the truststore is correctly configured.

### How can I compile this tool from source code? ###
```sh
    git clone git@bitbucket.org:atlassianlabs/httpclienttest.git
	cd httpclienttest
    mvn package 
	java -jar target/httpclienttest-1.0.2.jar https://jira.atlassian.com:443
```

### Apache - HttpClient ###

This project uses the HTTPClient version 4.4.1 to replicate a bug for demonstration purposes. 

Apache HttpComponents Client
Copyright © 2016 The Apache Software Foundation

Licensed under the Apache License, Version 2.0 (the “License”); you may not use this file except in compliance with the License.  A copy of this License is available at (http://www.apache.org/licenses/LICENSE-2.0).  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
