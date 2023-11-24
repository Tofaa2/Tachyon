package net.tachyon.ping;

import net.tachyon.network.player.PlayerConnection;

/**
 * Consumer used to fill a {@link ResponseData} object before being sent to a connection.
 *
 * <p>Can be specified in {@link net.tachyon.Server#setResponseDataConsumer(ResponseDataConsumer)}.
 */
@FunctionalInterface
public interface ResponseDataConsumer {

  /**
   * A method to fill the data with the response.
   *
   * @param playerConnection The player connection to which the response should be sent.
   * @param responseData The data for the response.
   */
  void accept(PlayerConnection playerConnection, ResponseData responseData);
}
