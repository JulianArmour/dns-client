package app.presenter;

import app.config.QueryType;
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
    if (serverResponse.answerCount() + serverResponse.additionalCount() <= 0) {
      printer.println("NOTFOUND");
      return;
    }
    printAnswers();
    printAdditionals();
  }

  private void printAnswers() {
    if (serverResponse.answerCount() == 0)
      return;//nothing to print

    printer
        .printf("***Answer Section (%d records)***",serverResponse.answerCount())
        .println();
    serverResponse
        .getAnswers()
        .forEach(this::printAnswerResourceRecord);
  }

  private void printAdditionals() {
    if (serverResponse.additionalCount() == 0)
      return;//nothing to print

    printer.println();
    printer
        .printf("***Additional Section (%d records)***",
            serverResponse.additionalCount())
        .println();
    serverResponse
        .getAdditionals()
        .forEach(this::printAdditionalResourceRecord);
  }

  private void printAnswerResourceRecord(ResourceRecord record) {
    switch (record.getType()) {
      case MX:
        printer.printf("MX \t %s \t %s \t %d \t %s",
            record.getData(), record.getPreference(), record.getTTL(),
            authoritativeToString(serverResponse));
        break;
      case A:
        printer.printf("IP \t %s \t %d \t %s",
            record.getData(), record.getTTL(),
            authoritativeToString(serverResponse));
        break;
      case NS:
        printer.printf("NS \t %s \t %d \t %s",
            record.getData(), record.getTTL(),
            authoritativeToString(serverResponse));
        break;
      case CNAME:
        printer.printf("CNAME \t %s \t %d \t %s",
            record.getData(), record.getTTL(),
            authoritativeToString(serverResponse));
        break;
    }
    printer.println();
  }

  private void printAdditionalResourceRecord(ResourceRecord record) {
    if (record.getType() == QueryType.A) {
      printer.printf("%s \t IP \t %s \t %d \t %s",
          record.getName(), record.getData(), record.getTTL(),
          authoritativeToString(serverResponse));
    } else {
      printAnswerResourceRecord(record);
    }
    printer.println();
  }

  private static String authoritativeToString(ServerResponse serverResponse) {
    return serverResponse.authoritative() ? "auth" : "nonauth";
  }
}
