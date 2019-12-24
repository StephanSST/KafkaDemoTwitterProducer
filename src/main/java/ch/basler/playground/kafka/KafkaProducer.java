package ch.basler.playground.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import ch.basler.playground.model.RssFeedEntry;

@Service
public class KafkaProducer {

  private static final Logger LOG = LoggerFactory.getLogger(KafkaProducer.class);
  private static final String TOPIC = "twitter";

  @Autowired
  private KafkaTemplate<String, RssFeedEntry> kafkaTemplate;

  public void sendMessage(RssFeedEntry rssFeedEntry) {
    LOG.info(String.format("#### -> Producing message -> %s", rssFeedEntry));

    Message<RssFeedEntry> message = MessageBuilder.withPayload(rssFeedEntry)//
        .setHeader(KafkaHeaders.TOPIC, TOPIC)//
        .build();

    kafkaTemplate.send(message);
  }
}