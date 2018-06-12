CREATE KEYSPACE IF NOT EXISTS adstats WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': '1'};

CREATE TABLE IF NOT EXISTS adstats.delivery (
delivery_id TEXT,
advertisement_id INT,
time TIMESTAMP,
browser TEXT,
os TEXT,
site TEXT,
PRIMARY KEY(delivery_id, advertisement_id)
);

CREATE TABLE IF NOT EXISTS adstats.click (
click_id TEXT,
delivery_id TEXT,
time TIMESTAMP,
PRIMARY KEY(click_id, delivery_id)
);
