package ch.basler.playground.twitter;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;

@Service
public class TwitterClient {
  private static final Logger LOG = LoggerFactory.getLogger(TwitterClient.class);

  @Autowired
  private TwitterConnection twitterConnection;

  private Client hosebirdClient;

  private LinkedBlockingQueue<String> msgQueue;

  private LinkedBlockingQueue<Event> eventQueue;

  public TwitterClient() {
    msgQueue = new LinkedBlockingQueue<String>(100000);
    eventQueue = new LinkedBlockingQueue<Event>(1000);

    ClientBuilder builder = new ClientBuilder()//
        .name("Hosebird-Client-01") // optional: mainly for the logs
        .hosts(twitterConnection.getHosebirdHosts())//
        .authentication(twitterConnection.getHosebirdAuth())//
        .endpoint(twitterConnection.getHosebirdEndpoint())//
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

  private void processMessage(String msg) {
    LOG.info("Tweet alert -> " + msg);
  }
}
