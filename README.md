### What is this repository for? ###

* This tool is to check if your JIRA instance is affected by the bug [JRA-47568|https://jira.atlassian.com/browse/JRA-47568] and should be used in conjunction with the SSLPoke tool
* Version
* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)

### How do I get set up? ###

    git clone git@bitbucket.org:atlassianlabs/httpclienttest.git
	cd httpclienttest
    mvn package 
	java -jar target/httpclienttest-1.0.jar jira.atlassian.com:443
	
	
### How do I use my own truststore with this tool? ###
	java -Djavax.net.ssl.trustStore=/path/to/truststore.jks -jar target/httpclienttest-1.0.jar jira.atlassian.com:443 

### What is the expected output ###

The expected output ie either a PKIX exception, indicating that your certificate is affected by the SNI bug, or you have a misconfigured truststore.

### How do I know if my truststore is ok? ###

The [SSLPoke test|https://confluence.atlassian.com/kb/unable-to-connect-to-ssl-services-due-to-pkix-path-building-failed-779355358.html] does not use the Apache HTTPClient libraries and therefore will connect successfully if the truststore is correctly configured. 