Distributed Transactions Outside J2EE Container
================================================  

---
___
  
  
Introduction
------------
basic project providing the structure and configuration to utilize JBOSS XTS libraries for managing a transactional context across nodes.  this is a spring based application which generates a war file suitable for deployment in any servlet container.  at this point, the project has been mostly cobbled together in order to share with others.  i'll try and clean up a little further once i've verified that i have configured things correctly.  all interactions with the db and message broker have been commented out.  see below regarding steps for configuring your environment for actual distributed XA transactions.

Running
------------
    cd <INSTALL_ROOT>
    mvn -DskipTests=true clean install   

*it's important to note that all tests will fail without first configuring the MYSQL db*   

    cp <INSTALL_ROOT>/hlc-txn-webapp/target/hlc-txn.war $CATALINA_HOME/webapps/ROOT.war
    $CATALINA_HOME/bin/startup.sh

*assuming you wish to deploy to tomcat. the only requirement is that your servlet container is deployed to the root context "/" and listens on port 8181*

    cd <INSTALL_ROOT>/hlc-txn-webapp
    mvn -DuseFile=false -Dtest=NonRpcSoapTxnClient test   

*kicks off the client for standard completion coordinator.  this is convoluted and somewhat silly--i'm probably doing something incorrect here*    

    cd <INSTALL_ROOT>/hlc-txn-client
    mvn -DuseFile=false -Dtest=RpcSoapTxnClient test    

*kicks off the client for non-standard, JBOSS specific completion coordinator.  currently, exception is raised using this client.*   

Further Configuration
------------
    cd <INSTALL_ROOT>/hlc-txn-persistence
    export MYSQL_ROOT_USER=<user_name>
    export MYSQL_ROOT_PWD=<user_pwd>
    mvn -e -DskipTests=true clean package -Pinitdb  

*this assumes you have a MYSQL instance installed locally and the correct credentials for creating schemas/tables and granting permissions to users*   


