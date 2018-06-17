CREATE KEYSPACE IF NOT EXISTS adstats WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': '1'};

CREATE TABLE IF NOT EXISTS adstats.delivery (
delivery_id text,
advertisement_id int,
"time" timestamp,
browser text,
os text,
site text,
PRIMARY KEY(delivery_id, "time")
) WITH CLUSTERING ORDER BY ("time" ASC);

CREATE TABLE IF NOT EXISTS adstats.click (
click_id text,
delivery_id text,
"time" timestamp,
PRIMARY KEY(click_id, "time")
) WITH CLUSTERING ORDER BY ("time" ASC);

CREATE TABLE IF NOT EXISTS adstats.install (
install_id text,
click_id text,
"time" timestamp,
PRIMARY KEY(install_id, "time")
) WITH CLUSTERING ORDER BY ("time" ASC);

CREATE TABLE IF NOT EXISTS adstats.stats (
metric_name text,
event_time timestamp,
browser text,
os text,
counter_value counter,
PRIMARY KEY(metric_name, event_time, browser, os)
);
