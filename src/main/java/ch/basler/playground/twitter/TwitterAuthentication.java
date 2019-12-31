package ch.basler.playground.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

@Service
public class TwitterAuthentication {
  private static final Logger LOG = LoggerFactory.getLogger(TwitterAuthentication.class);

  private Authentication hosebirdAuth;

  @Value("${twitter.api.key}")
  private String twitterApiKey;

  @Value("${twitter.api.secret}")
  private String twitterApiSecret;

  @Value("${twitter.access.token}")
  private String twitterAccessToken;

  @Value("${twitter.access.secret}")
  private String twitterAccessSecret;

  public void setTwitterApiKey(String twitterApiKey) {
    this.twitterApiKey = twitterApiKey;
  }

  public void setTwitterApiSecret(String twitterApiSecret) {
    this.twitterApiSecret = twitterApiSecret;
  }

  public void setTwitterAccessToken(String twitterAccessToken) {
    this.twitterAccessToken = twitterAccessToken;
  }

  public void setTwitterAccessSecret(String twitterAccessSecret) {
    this.twitterAccessSecret = twitterAccessSecret;
  }

  public Authentication getHosebirdAuth() {
    if (hosebirdAuth == null) {
      hosebirdAuth = new OAuth1(twitterApiKey, twitterApiSecret, twitterAccessToken, twitterAccessSecret);
      LOG.info("OAuth auf Twitter initialisiert: " + getHosebirdAuth());
    }
    return hosebirdAuth;
  }

}
