package ch.basler.playground.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.basler.playground.twitter.TwitterClient;

@RestController
@RequestMapping(value = "/kafka/twitter")
public class KafkaController {

  @Autowired
  private TwitterClient twitterClient;

  @RequestMapping("/version")
  public String showVersion() {
    String message = "Kafka ready to read and produce";
    return message;
  }

  @RequestMapping(value = "/startTwitter")
  public void readMessagesFromTwitter() {
    twitterClient.readMessages();
  }

}
