package com.adstats.util.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.ProtocolOptions;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.QueryOptions;
import static java.lang.Integer.parseInt;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;

final class ClusterFactory {

  static Cluster createCassandraClientCluster(Properties properties) {
    // Basic config for non-production use.
    // For production, I would consider setting up reconnection policy (reconnect delays),
    // pooling (i.e. heart beat interval) options and socket options (connect and read timeouts)
    return Cluster.builder()
        .addContactPointsWithPorts(parseContactPoints(properties))
        .withProtocolVersion(ProtocolVersion.V3)
        .withQueryOptions(new QueryOptions().setConsistencyLevel(ConsistencyLevel.LOCAL_QUORUM))
        .build();
  }

  private static Set<InetSocketAddress> parseContactPoints(Properties properties) {
    String clusterNodes = properties.getProperty("nodes");
    if (clusterNodes == null) {
      throw new NoSuchElementException("Can not find 'nodes' property to create cassandra cluster");
    }
    Set<InetSocketAddress> contactPoints = new HashSet<>();
    for (String rawAddress : clusterNodes.split(",")) {
      String[] addressParts = rawAddress.trim().split(":");
      String host = addressParts[0];
      int port = addressParts.length > 1 ? parseInt(addressParts[1]) : ProtocolOptions.DEFAULT_PORT;
      InetSocketAddress contactPoint = new InetSocketAddress(host, port);
      contactPoints.add(contactPoint);
    }
    return contactPoints;
  }

  private ClusterFactory() {
  }
}
