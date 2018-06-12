package com.akhalikov.adstats.util.cassandra;

import com.datastax.driver.core.Session;
import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedCassandraConfig {

  @Bean
  Session cassandraSession() throws Exception {
    Session session = SessionFactory.createCassandraSession();
    createSchema(session);
    return session.getCluster().connect("adstats");
  }

  private static void createSchema(Session session) {
    CQLDataLoader cqlDataLoader = new CQLDataLoader(session);
    cqlDataLoader.load(new ClassPathCQLDataSet("scripts/db-create.cql"));
  }
}
