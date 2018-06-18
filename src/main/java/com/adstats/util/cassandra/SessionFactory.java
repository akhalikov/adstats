package com.adstats.util.cassandra;

import static com.adstats.util.cassandra.ClusterFactory.createCassandraClientCluster;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import java.io.IOException;
import java.util.Properties;

import com.datastax.driver.extras.codecs.jdk8.InstantCodec;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;

final class SessionFactory {

  static Session createCassandraSession() throws Exception {
    int clusterPort = startEmbeddedCassandraAndGetPort();

    Properties clientProperties = new Properties();
    clientProperties.setProperty("nodes", "127.0.0.1:" + clusterPort);
    Cluster cluster = createCassandraClientCluster(clientProperties);
    cluster.getConfiguration().getCodecRegistry().register(InstantCodec.instance);
    return cluster.connect();
  }

  private static int startEmbeddedCassandraAndGetPort()
      throws TTransportException, IOException, ConfigurationException {

    System.setProperty("cassandra.start_rpc", "false");
    EmbeddedCassandraServerHelper.startEmbeddedCassandra(EmbeddedCassandraServerHelper.CASSANDRA_RNDPORT_YML_FILE);
    return EmbeddedCassandraServerHelper.getNativeTransportPort();
  }
}
