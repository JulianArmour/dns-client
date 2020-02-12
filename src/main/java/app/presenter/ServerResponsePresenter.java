package app.presenter;

import packet.ResourceRecord;
import packet.ServerResponse;

import java.io.OutputStream;
import java.io.PrintWriter;

public class ServerResponsePresenter {
  private ServerResponse serverResponse;
  private PrintWriter printer;

  public ServerResponsePresenter(ServerResponse serverResponse,
                                 OutputStream outputStream) {
    this.serverResponse = serverResponse;
    printer = new PrintWriter(outputStream, true);
  }

  public void present() {
    //TODO
  }

  private void printAnswers() {


    serverResponse
        .getAnswers()
        .forEach(this::printResourceRecord);
  }

  private void printResourceRecord(ResourceRecord record) {
    switch (record.getType()) {
      case A:
        printer.printf("%s\t%s\t%d\t%s",
            "IP", record.getData(), record.getTTL(),
            serverResponse.authoritative() ? "auth" : "nonauth");
        break;
      case CNAME:

        break;

    }
  }
}
