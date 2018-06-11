package com.akhalikov.adstats.util.cassandra;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.Session;
import static com.datastax.driver.core.schemabuilder.SchemaBuilder.createKeyspace;
import static com.datastax.driver.core.schemabuilder.SchemaBuilder.createTable;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedCassandraConfig {
  private static final String KEYSPACE = "adstats";

  @Bean
  Session cassandraSession() throws Exception {
    Session session = SessionFactory.createCassandraSession();
    session = createKeyspaceAndConnect(session);
    createTables(session);
    return session;
  }

  private static Session createKeyspaceAndConnect(Session session) {
    session.execute(createKeyspace(KEYSPACE)
        .ifNotExists()
        .with()
        .replication(getReplicationSettings()));

    return session.getCluster().connect(KEYSPACE);
  }

  private static void createTables(Session session) {
    session.execute(createTable("delivery")
        .addPartitionKey("advertisement_id", DataType.cint())
        .addClusteringColumn("delivery_id", DataType.text())
        .addColumn("time", DataType.timestamp())
        .addColumn("browser", DataType.text())
        .addColumn("os", DataType.text())
        .addColumn("site", DataType.text()));
  }

  private static Map<String, Object> getReplicationSettings() {
    Map<String, Object> settings = new HashMap<>();
    settings.put("class", "SimpleStrategy");
    settings.put("replication_factor", "1");
    return settings;
  }
}
