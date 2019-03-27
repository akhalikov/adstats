[![Build Status](https://travis-ci.org/akhalikov/adstats.svg?branch=master)](https://travis-ci.org/akhalikov/adstats) [![codecov](https://codecov.io/gh/akhalikov/adstats/branch/master/graph/badge.svg)](https://codecov.io/gh/akhalikov/adstats) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/1d1cc66d09da459aa1164edfb6667a90)](https://www.codacy.com/app/akhalikov/adstats?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=akhalikov/adstats&amp;utm_campaign=Badge_Grade)

# AdStats assignment

Simple AdTech web service that tracks the ads that are being delivered through their lifecycle 
and generates some simple statistics.

Requirements:

* Maven
* Java 8

### Build and run

The easiest way to build the application is to run the command:

```
$ mvn spring-boot:run
```

The application runs on port `9090` so if you go to `http://localhost:9090/actuator/health` you should see `{"status":"UP"}` message.

### Docker

Docker image can be created with the following command:

```
$ docker build -t adstats .
```

Then you can run the application in docker container:

```
$ docker run -p 5000:9090 adstats
```

[API Description](https://github.com/akhalikov/adstats-assignment/wiki/API-Description)
