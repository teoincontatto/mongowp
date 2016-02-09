
Name:           mongowp
Version:        0.41.SNAPSHOT
Release:        1%{?dist}
Summary:        MongoWP Parent

License:        GNU AFFERO PUBLIC LICENSE, Version 3, 19 November 2007
URL:            https://www.8kdata.com

Source0:    https://github.com/teoincontatto/mongowp/archive/rpm_package.zip#/mongowp-rpm_package.zip
Source1:    mongowp_super_pom.xml
    

Source2:    https://github.com/ThreeTen/threetenbp/archive/v1.3.1.zip#/threetenbp-1.3.1.zip
        

Source3:    https://github.com/mongodb/mongo-java-driver/archive/r3.0.4.zip#/mongo-java-driver-r3.0.4.zip
        

Patch0:    81b8899fd0a7481082b889cbbf5eb885.patch
        

BuildArch:  noarch

BuildRequires: maven-local
    

BuildRequires: mvn(aopalliance:aopalliance)
        

BuildRequires: mvn(ch.qos.logback:logback-classic)
        

BuildRequires: mvn(ch.qos.logback:logback-core)
        

BuildRequires: mvn(com.google.code.findbugs:annotations)
        

BuildRequires: mvn(com.google.code.findbugs:jsr305)
        

BuildRequires: mvn(com.google.guava:guava)
        

BuildRequires: mvn(com.google.inject:guice)
        

BuildRequires: mvn(io.netty:netty-buffer)
        

BuildRequires: mvn(io.netty:netty-codec)
        

BuildRequires: mvn(io.netty:netty-common)
        

BuildRequires: mvn(io.netty:netty-transport)
        

BuildRequires: mvn(javax.inject:javax.inject)
        

BuildRequires: mvn(junit:junit)
        

BuildRequires: mvn(net.jcip:jcip-annotations)
        

BuildRequires: mvn(org.hamcrest:hamcrest-core)
        

BuildRequires: mvn(org.slf4j:slf4j-api)
        

BuildRequires: mvn(io.netty:netty-handler)
        

%description
mongowp is a Java layer that enables the development of server-side MongoDB wire protocol implementations.
		Any application designed to act as a mongo server could rely on this layer to implement the wire protocol.
		Examples of such applications may be mongo proxies, connection poolers or in-memory implementations, to name a few.
    

%package parent
Summary: MongoWP Parent
%description parent
mongowp is a Java layer that enables the development of server-side MongoDB wire protocol implementations.
		Any application designed to act as a mongo server could rely on this layer to implement the wire protocol.
		Examples of such applications may be mongo proxies, connection poolers or in-memory implementations, to name a few.
        

%package messages
Summary: mongowp-messages
%description messages
The MongoWP project that contains message classes
        

%package protocol
Summary: mongowp-protocol
%description protocol
The MongoWP project that contains protocol exceptions and constants
        

%package mongo-server
Summary: Mongo Server: Core
%description mongo-server
The MongoWP project that contains the core classes
        

%package mongo-server-api
Summary: Mongo Server: Unsafe API
%description mongo-server-api
First MongoWP Server API version, usually called unsafe because callbacks are less type safe than the newer versions
        

%package mongo-server-api-safe
Summary: Mongo Server: Safe API
%description mongo-server-api-safe
Second MongoWP Server API version, usually called safe because callbacks are type safe
        

%package library
Summary: Mongo Server: Commands Libraries
%description library
Aggregation project that contains MongoWP safe command libraries.
        

%package v3m0
Summary: Mongo Server: Command Library 3.0
%description v3m0
Mongo library that emulates MongoDB 3.0 commands
        

%package parent-pom
Summary: Mongo Client (POM)
%description parent-pom
Aggregation project that contains MongoWP client implementations
        

%package core
Summary: Mongo Client: Core
%description core
MongoWP Client core project, used by other client projects to share common platform independent implementations
        

%package driver-wrapper
Summary: Mongo Client: Driver Wrapper
%description driver-wrapper
MongoWP Client project that delegates on the official MongoDB Java Driver
        

%package netty-bson-parent
Summary: netty-bson-parent
%description netty-bson-parent
mongowp is a Java layer that enables the development of server-side MongoDB wire protocol implementations.
		Any application designed to act as a mongo server could rely on this layer to implement the wire protocol.
		Examples of such applications may be mongo proxies, connection poolers or in-memory implementations, to name a few.
        

%package netty-bson-api
Summary: netty-bson-api
%description netty-bson-api
mongowp is a Java layer that enables the development of server-side MongoDB wire protocol implementations.
		Any application designed to act as a mongo server could rely on this layer to implement the wire protocol.
		Examples of such applications may be mongo proxies, connection poolers or in-memory implementations, to name a few.
        

%package bson-mongo-driver
Summary: bson-mongo-driver
%description bson-mongo-driver
mongowp is a Java layer that enables the development of server-side MongoDB wire protocol implementations.
		Any application designed to act as a mongo server could rely on this layer to implement the wire protocol.
		Examples of such applications may be mongo proxies, connection poolers or in-memory implementations, to name a few.
        

%package dependencies
Summary: mongowp dependencies Java packages
%description dependencies
mongowp dependencies Java packages
        

%package javadoc
Summary: mongowp documentation
%description javadoc
mongowp documentation
        

%prep
    

%mvn_alias 'ch.qos.logback:logback-classic:jar:1.1.3' 'ch.qos.logback:logback-classic:jar:1.1.2'
        

%mvn_alias 'ch.qos.logback:logback-core:jar:1.1.3' 'ch.qos.logback:logback-core:jar:1.1.2'
        

%mvn_alias 'ch.qos.logback:logback-classic:jar:1.1.3' 'ch.qos.logback:logback-classic:jar:1.1.1'
        

%mvn_alias 'ch.qos.logback:logback-core:jar:1.1.3' 'ch.qos.logback:logback-core:jar:1.1.1'

%mvn_alias 'com.google.code.findbugs:jsr305:jar:1.3.9' 'com.google.code.findbugs:jsr305:jar:0.1.SNAPSHOT'
        

%mvn_alias 'com.google.code.findbugs:jsr305:jar:3.0.0' 'com.google.code.findbugs:jsr305:jar:0.1.SNAPSHOT'
        

%mvn_alias 'com.google.code.findbugs:jsr305:jar:3.0.1' 'com.google.code.findbugs:jsr305:jar:0.1.SNAPSHOT'
        

%mvn_alias 'com.github.stephenc.jcip:jcip-annotations:jar:1.0-1' 'com.github.stephenc.jcip:jcip-annotations:jar:1.0'
        

%mvn_alias 'com.google.code.findbugs:jFormatString:jar:3.0.0' 'com.google.code.findbugs:jFormatString:jar:2.0.2'
        

%mvn_alias 'org.apache.ant:ant:jar:1.9.4' 'org.apache.ant:ant:jar:1.9.6'
        

%mvn_alias 'org.apache.ant:ant-launcher:jar:1.9.4' 'org.apache.ant:ant-launcher:jar:1.9.6'
        

%mvn_alias 'com.google.guava:guava:jar:19.0' 'com.google.guava:guava:jar:18.0'
        

%mvn_alias 'com.google.guava:guava:jar:19.0' 'com.google.guava:guava:jar:17.0'
        

%mvn_alias 'io.netty:netty-buffer:jar:4.0.33.Final' 'io.netty:netty-buffer:jar:4.0.28'
        

%mvn_alias 'io.netty:netty-codec:jar:4.0.33.Final' 'io.netty:netty-codec:jar:4.0.28'
        

%mvn_alias 'io.netty:netty-common:jar:4.0.33.Final' 'io.netty:netty-common:jar:4.0.28'
        

%mvn_alias 'io.netty:netty-transport:jar:4.0.33.Final' 'io.netty:netty-transport:jar:4.0.28'
        

%mvn_alias 'io.netty:netty-buffer:jar:4.0.26.Final' 'io.netty:netty-buffer:jar:4.0.28'
        

%mvn_alias 'io.netty:netty-codec:jar:4.0.26.Final' 'io.netty:netty-codec:jar:4.0.28'
        

%mvn_alias 'io.netty:netty-common:jar:4.0.26.Final' 'io.netty:netty-common:jar:4.0.28'
        

%mvn_alias 'io.netty:netty-handler:jar:4.0.26.Final' 'io.netty:netty-handler:jar:4.0.28'
        

%mvn_alias 'io.netty:netty-transport:jar:4.0.26.Final' 'io.netty:netty-transport:jar:4.0.28'
        

%mvn_alias 'org.slf4j:slf4j-api:jar:1.7.6' 'org.slf4j:slf4j-api:jar:1.7.12'
        

%mvn_alias 'org.slf4j:slf4j-api:jar:1.7.13' 'org.slf4j:slf4j-api:jar:1.7.12'
        

%mvn_alias 'org.slf4j:slf4j-api:jar:1.7.6' 'org.slf4j:slf4j-api:jar:1.7.10'
        

%mvn_alias 'org.slf4j:slf4j-api:jar:1.7.13' 'org.slf4j:slf4j-api:jar:1.7.10'
        

%mvn_alias 'org.slf4j:slf4j-api:jar:1.7.6' 'org.slf4j:slf4j-api:jar:1.7.7'
        

%mvn_alias 'org.slf4j:slf4j-api:jar:1.7.13' 'org.slf4j:slf4j-api:jar:1.7.7'
        

%mvn_alias 'org.slf4j:slf4j-api:jar:1.7.6' 'org.slf4j:slf4j-api:jar:1.7.14'
        

%mvn_alias 'org.slf4j:slf4j-api:jar:1.7.13' 'org.slf4j:slf4j-api:jar:1.7.14'
        

%mvn_alias 'com.google.inject:guice:jar:4.0' 'com.google.inject:guice:jar:3.2.5'
        

%mvn_alias 'com.google.inject:guice:jar:4.0' 'com.google.inject:guice:jar:3.2.2'
        

%mvn_alias 'junit:junit:jar:4.12' 'junit:junit:jar:4.11'
        

%mvn_package 'maven-packager:super-pom'
    

%mvn_package 'org.mongodb:mongo-java-driver-mock-parent'
        

%mvn_package 'com.8kdata.mongowp:mongowp-parent:pom:0.41-SNAPSHOT' 'parent'
        

%mvn_package 'com.8kdata.mongowp:mongowp-messages:jar:0.41-SNAPSHOT' 'messages'
        

%mvn_package 'com.8kdata.mongowp:mongowp-protocol:jar:0.41-SNAPSHOT' 'protocol'
        

%mvn_package 'com.8kdata.mongowp:mongo-server:jar:0.41-SNAPSHOT' 'mongo-server'
        

%mvn_package 'com.8kdata.mongowp:mongo-server-api:jar:0.41-SNAPSHOT' 'mongo-server-api'
        

%mvn_package 'com.8kdata.mongowp:mongo-server-api-safe:jar:0.41-SNAPSHOT' 'mongo-server-api-safe'
        

%mvn_package 'com.8kdata.mongowp.api.safe.library:library:pom:0.41-SNAPSHOT' 'library'
        

%mvn_package 'com.8kdata.mongowp.api.safe.library:v3m0:jar:0.41-SNAPSHOT' 'v3m0'
        

%mvn_package 'com.8kdata.mongowp.client:parent-pom:pom:0.41-SNAPSHOT' 'parent-pom'
        

%mvn_package 'com.8kdata.mongowp.client:core:jar:0.41-SNAPSHOT' 'core'
        

%mvn_package 'com.8kdata.mongowp.client:driver-wrapper:jar:0.41-SNAPSHOT' 'driver-wrapper'
        

%mvn_package 'com.8kdata.netty-bson:netty-bson-parent:pom:0.41-SNAPSHOT' 'netty-bson-parent'
        

%mvn_package 'com.8kdata.netty-bson:netty-bson-api:jar:0.41-SNAPSHOT' 'netty-bson-api'
        

%mvn_package 'com.8kdata.netty-bson:bson-mongo-driver:jar:0.41-SNAPSHOT' 'bson-mongo-driver'
        

%mvn_package 'org.threeten:threetenbp:jar:1.3.1' dependencies
            

%mvn_package 'org.mongodb:bson:jar:3.0.4' dependencies
            

%mvn_package 'org.mongodb:mongodb-driver-core:jar:3.0.4' dependencies
            

%mvn_package 'org.mongodb:mongodb-driver:jar:3.0.4' dependencies
            

%mvn_package 'org.mongodb:mongo-java-driver:jar:3.0.4' dependencies
            

%setup -c -n 'mongowp-rpm_package'
cp %{SOURCE1} .
    

%setup -T -D -a 2 -n 'mongowp-rpm_package'
        

%setup -T -D -a 3 -n 'mongowp-rpm_package'
        

patch -p 6 -f -d 'mongo-java-driver-r3.0.4' < %PATCH0
        

%build
    

%mvn_build -s -- -f 'mongowp_super_pom.xml' -Denforcer.skip=true
    

%install
%mvn_install
    

cat '.mfiles-threetenbp' >> '.mfiles-dependencies'
            

cat '.mfiles-bson' >> '.mfiles-dependencies'
            

cat '.mfiles-mongodb-driver-core' >> '.mfiles-dependencies'
            

cat '.mfiles-mongodb-driver' >> '.mfiles-dependencies'
            

cat '.mfiles-mongo-java-driver' >> '.mfiles-dependencies'
            

cat '.mfiles-dependencies'|sort|uniq > '.mfiles-dependencies.filtered'
mv '.mfiles-dependencies.filtered' '.mfiles-dependencies'
    

cat '.mfiles-super-pom' >> '.mfiles-extras'
    

cat '.mfiles-mongo-java-driver-mock-parent' >> '.mfiles-extras'
        

cat '.mfiles-extras'|sort|uniq > '.mfiles-extras.filtered'
mv '.mfiles-extras.filtered' '.mfiles-extras'
    

%files 'parent' -f '.mfiles-mongowp-parent'
        

%files 'messages' -f '.mfiles-mongowp-messages'
        

%files 'protocol' -f '.mfiles-mongowp-protocol'
        

%files 'mongo-server' -f '.mfiles-mongo-server'
        

%files 'mongo-server-api' -f '.mfiles-mongo-server-api'
        

%files 'mongo-server-api-safe' -f '.mfiles-mongo-server-api-safe'
        

%files 'library' -f '.mfiles-library'
        

%files 'v3m0' -f '.mfiles-v3m0'
        

%files 'parent-pom' -f '.mfiles-parent-pom'
        

%files 'core' -f '.mfiles-core'
        

%files 'driver-wrapper' -f '.mfiles-driver-wrapper'
        

%files 'netty-bson-parent' -f '.mfiles-netty-bson-parent'
        

%files 'netty-bson-api' -f '.mfiles-netty-bson-api'
        

%files 'bson-mongo-driver' -f '.mfiles-bson-mongo-driver'
        

%files 'dependencies' -f '.mfiles-dependencies'
    

%files javadoc -f '.mfiles-javadoc'
    

%files -f '.mfiles-extras'
    

%changelog
* Mon Feb 08 2016 MongoWP Project Contributors <mongowp@8kdata.com> - 0.41-SNAPSHOT
- 0.41-SNAPSHOT
    
