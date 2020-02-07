package packet;

/**
 * A container for all the data the server sends back as a response to a query.
 */
public interface ServerResponse {
  /**
   * @return The packet ID.
   */
  int id();
}
