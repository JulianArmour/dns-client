package app;

public class BadCommandLineArgument extends Exception {
  public BadCommandLineArgument(String msg) {
    super(msg);
  }
}
