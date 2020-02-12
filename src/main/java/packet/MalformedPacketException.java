package packet;

public class MalformedPacketException extends Exception {
  public MalformedPacketException(String message, Throwable cause) {
    super(message, cause);
  }
}
