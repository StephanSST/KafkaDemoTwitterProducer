package ch.basler.playground.twitter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

@Service
public class TwitterConnection {
  private static final Logger LOG = LoggerFactory.getLogger(TwitterConnection.class);

  private StatusesFilterEndpoint hosebirdEndpoint;
  private Hosts hosebirdHosts;
  private Authentication hosebirdAuth;

  public TwitterConnection() {
    LOG.info("Twitter connection wird initialisiert.");

    hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
    hosebirdEndpoint = new StatusesFilterEndpoint();
    // Optional: set up some followings and track terms
    List<Long> followings = Lists.newArrayList(1234L, 566788L);
    List<String> terms = Lists.newArrayList("twitter", "api");
    hosebirdEndpoint.followings(followings);
    hosebirdEndpoint.trackTerms(terms);

    hosebirdAuth = new OAuth1("consumerKey", "consumerSecret", "token", "secret");
    LOG.info("OAuth auf Twitter initialisiert: " + hosebirdAuth);

    LOG.info("Twitter connection erfolgreich initialisiert: " + hosebirdHosts);
    LOG.info("Twitter connection erfolgreich initialisiert: " + hosebirdEndpoint);
  }

  public StatusesFilterEndpoint getHosebirdEndpoint() {
    return hosebirdEndpoint;
  }

  public void setHosebirdEndpoint(StatusesFilterEndpoint hosebirdEndpoint) {
    this.hosebirdEndpoint = hosebirdEndpoint;
  }

  public Hosts getHosebirdHosts() {
    return hosebirdHosts;
  }

  public void setHosebirdHosts(Hosts hosebirdHosts) {
    this.hosebirdHosts = hosebirdHosts;
  }

  public Authentication getHosebirdAuth() {
    return hosebirdAuth;
  }

  public void setHosebirdAuth(Authentication hosebirdAuth) {
    this.hosebirdAuth = hosebirdAuth;
  }

}