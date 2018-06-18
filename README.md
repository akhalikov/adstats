[![Build Status](https://travis-ci.org/akhalikov/adstats-assignment.svg?branch=master)](https://travis-ci.org/akhalikov/adstats-assignment) [![codecov](https://codecov.io/gh/akhalikov/adstats-assignment/branch/master/graph/badge.svg)](https://codecov.io/gh/akhalikov/adstats-assignment)

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

The application runs on port 9090 so if you go to `http://localhost:9090/actuator/health` you should see `{"status":"UP"}` message.

### Docker

Docker image can be created with the following command:

```
$ docker build -t adstats .
```

Then you can run the application in docker container:

```
$ docker run -p 5000:9090 adstats
```

### API description

1. `POST /ads/delivery`

Request	format:

```json
{
    "advertisementId":	4483,
    "deliveryId": "244cf0db-ba28-4c5f-8c9c-2bf11ee42988",
    "time": "2018-01-07T18:32:23.602300+0000",
    "browser": "Chrome",
    "os":	"iOS",
    "site":	"http://super-dooper-news.com"
}
```

Response format:

* HTTP status code 200 if all went fine
* HTTP status code 500 if any error occurred

2. `POST /ads/click`

```json
{
    "deliveryId": "244cf0db-ba28-4c5f-8c9c-2bf11ee42988",
    "clickId": "fff54b83-49ff-476f-8bfb-2ec22b252c32",
    "time":	"2018-01-07T18:32:34.201100+0000"
}
```

Response format:

* HTTP status code 200 if all went fine
* HTTP status code 404 if we never received the give click
* HTTP status code 500 if any error occurred

3. `GET	/ads/statistics?start={start}&end={end}`

```json
{
    "interval":	{
      "start": "2018-01-07T14:30:00+0000",
      "end"	:  "2018-01-07T18:20:00+0000"
    },
    "stats": {
      "deliveries": 10,
      "clicks":	  4,
      "installs": 1
    }
}
```

4. `GET	/ads/statistics?start={start}&end={end}&group_by={category1}...&group_by={categoryN}`

```json
{
    "interval":	{
        "start": "2018-01-07T14:30:00+0000",
        "end":	 "2018-01-07T18:20:00+0000"
    },
    "data":	[
      {
          "fields":	{
              "browser": "Chrome"
          },
          "stats":	{
            "deliveries": 10,
            "clicks":	  4,
            "installs":	  1
        }
      },
      {
        "fields": {
          "browser": "Safari"
        },
        "stats": {
          "deliveries":	2,
          "clicks":	  1,
          "installs": 1
        }
      }
    ]
}
```
