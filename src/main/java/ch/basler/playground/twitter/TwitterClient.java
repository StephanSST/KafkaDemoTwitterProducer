package ch.basler.playground.twitter;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;

import ch.basler.playground.kafka.KafkaProducer;
import ch.basler.playground.model.Tweet;

@Service
public class TwitterClient {
  private static final Logger LOG = LoggerFactory.getLogger(TwitterClient.class);

  private final TwitterConnection twitterConnection;
  private final TwitterAuthentication twitterAuthentication;

  @Autowired
  private KafkaProducer kafkaProducer;

  private Client hosebirdClient;

  private LinkedBlockingQueue<String> msgQueue;

  private LinkedBlockingQueue<Event> eventQueue;

  @Autowired
  public TwitterClient(TwitterConnection twitterConnection, TwitterAuthentication twitterAuthentication) {
    this.twitterConnection = twitterConnection;
    this.twitterAuthentication = twitterAuthentication;

    msgQueue = new LinkedBlockingQueue<String>(100000);
    eventQueue = new LinkedBlockingQueue<Event>(1000);

    ClientBuilder builder = new ClientBuilder()//
        .name("KafkaDemoTwitterProducer-hbc-01") // optional: mainly for the logs
        .hosts(this.twitterConnection.getHosebirdHosts())//
        .authentication(this.twitterAuthentication.getHosebirdAuth())//
        .endpoint(this.twitterConnection.getHosebirdEndpoint())//
        .processor(new StringDelimitedProcessor(msgQueue))//
        .eventMessageQueue(eventQueue); // optional: use this if you want to process client events

    hosebirdClient = builder.build();

    // Attempts to establish a connection.
    hosebirdClient.connect();
  }

  public void readMessages() {
    try {
      // on a different thread, or multiple different threads....
      while (!hosebirdClient.isDone()) {
        String msg = msgQueue.take();
        processMessage(msg);
      }
      hosebirdClient.stop();
    } catch (InterruptedException ex) {
      LOG.error("Unerwarteter Fehler beim Empfang vom Tweet", ex);
    }
  }

  private void processMessage(String jsonTweet) {
    try {
      LOG.info(jsonTweet);

      ObjectMapper objectMapper = new ObjectMapper();
      Tweet tweet = objectMapper.readValue(jsonTweet, Tweet.class);

      LOG.info("Tweet: " + tweet);

      kafkaProducer.sendMessage(jsonTweet);

    } catch (JsonProcessingException ex) {
      String errorMessage = "Fehler beim Parsen vom Json-Tweet";
      LOG.error(errorMessage, ex);
      throw new IllegalStateException(errorMessage, ex);
    }

  }
}
