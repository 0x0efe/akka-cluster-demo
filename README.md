## Akka Cluster Demo Application

[![Build Status](https://travis-ci.org/efekahraman/akka-cluster-demo.svg?branch=master)](https://travis-ci.org/efekahraman/akka-cluster-demo)

Demo application created for http://ekahraman.me

### Compiling

Run `sbt assembly` to compile and package standalone JAR.

### Running

* Seed Node: You need to export `port` environment variable for the seed node. See `application.conf` for default value (`2551`).
```
export port=2551
java -jar AkkaClusterDemo-assembly-1.0.jar
```

* Consumer Node:
```
java -jar AkkaClusterDemo-assembly-1.0.jar consumer
```

* Producer Node:
```
java -jar AkkaClusterDemo-assembly-1.0.jar producer
```

### License

Licensed under MIT License.
