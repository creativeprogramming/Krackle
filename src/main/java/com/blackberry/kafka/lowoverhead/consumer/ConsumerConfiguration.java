package com.blackberry.kafka.lowoverhead.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerConfiguration {
  private static final Logger LOG = LoggerFactory
      .getLogger(ConsumerConfiguration.class);

  private List<String> metadataBrokerList;
  private int fetchMessageMaxBytes;
  private int fetchWaitMaxMs;
  private int fetchMinBytes;
  private int socketReceiveBufferBytes;
  private String autoOffsetReset;

  public ConsumerConfiguration(Properties props) throws Exception {
    LOG.info("Building configuration.");

    metadataBrokerList = new ArrayList<String>();
    String metadataBrokerListString = props.getProperty("metadata.broker.list");
    if (metadataBrokerListString == null || metadataBrokerListString.isEmpty()) {
      throw new Exception("metadata.broker.list cannot be empty.");
    }
    for (String s : metadataBrokerListString.split(",")) {
      // This is not a good regex. Could make it better.
      if (s.matches("^[\\.a-zA-Z0-9-]*:\\d+$")) {
        metadataBrokerList.add(s);
      } else {
        throw new Exception(
            "metata.broker.list must contain a list of hosts and ports (localhost:123,192.168.1.1:456).  Got "
                + metadataBrokerListString);
      }
    }
    LOG.info("metadata.broker.list = {}", metadataBrokerList);

    fetchMessageMaxBytes = Integer.parseInt(props.getProperty(
        "fetch.message.max.bytes", "" + (1024 * 1024)));
    if (fetchMessageMaxBytes <= 0) {
      throw new Exception("fetch.message.max.bytes must be positive.");
    }
    LOG.info("fetch.message.max.bytes = {}", fetchMessageMaxBytes);

    fetchWaitMaxMs = Integer.parseInt(props.getProperty("fetch.wait.max.ms",
        "100"));
    if (fetchWaitMaxMs < 0) {
      throw new Exception("fetch.wait.max.ms cannot be negative.");
    }
    LOG.info("fetch.wait.max.ms = {}", fetchWaitMaxMs);

    fetchMinBytes = Integer.parseInt(props.getProperty("fetch.min.bytes", "1"));
    if (fetchMinBytes < 0) {
      throw new Exception("fetch.min.bytes cannot be negative.");
    }
    LOG.info("fetch.min.bytes = {}", fetchMinBytes);

    socketReceiveBufferBytes = Integer.parseInt(props.getProperty(
        "socket.receive.buffer.bytes", "" + (64 * 1024)));
    if (socketReceiveBufferBytes < 0) {
      throw new Exception("socket.receive.buffer.bytes must be positive.");
    }
    LOG.info("socket.receive.buffer.bytes = {}", socketReceiveBufferBytes);

    autoOffsetReset = props.getProperty("auto.offset.reset", "largest");
    LOG.info("auto.offset.reset = {}", autoOffsetReset);
  }

  public List<String> getMetadataBrokerList() {
    return metadataBrokerList;
  }

  public void setMetadataBrokerList(List<String> metadataBrokerList) {
    this.metadataBrokerList = metadataBrokerList;
  }

  public int getFetchMessageMaxBytes() {
    return fetchMessageMaxBytes;
  }

  public void setFetchMessageMaxBytes(int fetchMessageMaxBytes) {
    this.fetchMessageMaxBytes = fetchMessageMaxBytes;
  }

  public int getFetchWaitMaxMs() {
    return fetchWaitMaxMs;
  }

  public void setFetchWaitMaxMs(int fetchWaitMaxMs) {
    this.fetchWaitMaxMs = fetchWaitMaxMs;
  }

  public int getFetchMinBytes() {
    return fetchMinBytes;
  }

  public void setFetchMinBytes(int fetchMinBytes) {
    this.fetchMinBytes = fetchMinBytes;
  }

  public int getSocketReceiveBufferBytes() {
    return socketReceiveBufferBytes;
  }

  public void setSocketReceiveBufferBytes(int socketReceiveBufferBytes) {
    this.socketReceiveBufferBytes = socketReceiveBufferBytes;
  }

  public String getAutoOffsetReset() {
    return autoOffsetReset;
  }

  public void setAutoOffsetReset(String autoOffsetReset) {
    this.autoOffsetReset = autoOffsetReset;
  }

}
