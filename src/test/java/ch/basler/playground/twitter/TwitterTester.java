package ch.basler.playground.twitter;

public class TwitterTester {

  public static void main(String[] args) {
    String twitterApiKey = getSystemProperty("twitter.api.key");
    String twitterApiSecret = getSystemProperty("twitter.api.secret");
    String twitterAccessToken = getSystemProperty("twitter.access.token");
    String twitterAccessSecret = getSystemProperty("twitter.access.secret");

    TwitterAuthentication twitterAuthentication = new TwitterAuthentication();
    twitterAuthentication.setTwitterApiKey(twitterApiKey);
    twitterAuthentication.setTwitterApiSecret(twitterApiSecret);
    twitterAuthentication.setTwitterAccessToken(twitterAccessToken);
    twitterAuthentication.setTwitterAccessSecret(twitterAccessSecret);

    TwitterConnection twitterConnection = new TwitterConnection();
    System.out.println("Authentication: " + twitterAuthentication.getHosebirdAuth());
    TwitterClient twitterClient = new TwitterClient(twitterConnection, twitterAuthentication);

    twitterClient.readMessages();
  }

  private static String getSystemProperty(String key) {
    String property = System.getProperty(key);
    if (property == null) {
      throw new IllegalArgumentException("VM Argument [" + key + "] war nicht gesetzt.");
    }
    return property;
  }

}
