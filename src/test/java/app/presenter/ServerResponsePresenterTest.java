package app.presenter;

import app.config.QueryType;
import org.junit.Test;
import packet.ResourceRecordImpl;
import packet.ServerResponse;
import packet.ServerResponseImpl;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;

public class ServerResponsePresenterTest {

  @Test
  public void presentAnswer_NoAnswers() {
    ServerResponseImpl aTypeQueryResponse_NotFound_Stub =
        new ServerResponseImpl();
    aTypeQueryResponse_NotFound_Stub.setAnswerCount(0);
    aTypeQueryResponse_NotFound_Stub.setAdditionalCount(0);
    final String expected = "NOTFOUND\r\n";
    assertPrintedData(aTypeQueryResponse_NotFound_Stub, expected);
  }
  @Test
  public void presentMXTypeAnswer() {
    ResourceRecordImpl mxTypeAnswerStub = new ResourceRecordImpl();
    mxTypeAnswerStub.setType(QueryType.MX);
    mxTypeAnswerStub.setData("alt1.aspmx.l.google.com");
    mxTypeAnswerStub.setPreference(20);
    mxTypeAnswerStub.setTTL(100);
    ServerResponseImpl mxTypeQueryResponseStub = new ServerResponseImpl();
    mxTypeQueryResponseStub.setAnswerCount(1);
    mxTypeQueryResponseStub.setAuthoritative(true);
    mxTypeQueryResponseStub.addAnswer(mxTypeAnswerStub);
    final String expected =
        "***Answer Section (1 records)***\r\n" +
        "MX \t alt1.aspmx.l.google.com \t 20 \t 100 \t auth\r\n";
    assertPrintedData(mxTypeQueryResponseStub, expected);
  }

  @Test
  public void presentATypeAnswer() {
    ResourceRecordImpl aTypeAnswerStub = new ResourceRecordImpl();
    aTypeAnswerStub.setType(QueryType.A);
    aTypeAnswerStub.setData("132.216.177.160");
    aTypeAnswerStub.setTTL(672);
    ServerResponseImpl aTypeQueryResponseStub = new ServerResponseImpl();
    aTypeQueryResponseStub.setAnswerCount(1);
    aTypeQueryResponseStub.addAnswer(aTypeAnswerStub);
    aTypeQueryResponseStub.setAuthoritative(true);

    final String expected =
        "***Answer Section (1 records)***\r\n" +
        "IP \t 132.216.177.160 \t 672 \t auth\r\n";
    assertPrintedData(aTypeQueryResponseStub, expected);
  }

  @Test
  public void presentNSTypeAnswerAndAdditional() {
    ResourceRecordImpl nsTypeAnswerStub = new ResourceRecordImpl();
    nsTypeAnswerStub.setName("facebook.com");
    nsTypeAnswerStub.setType(QueryType.NS);
    nsTypeAnswerStub.setTTL(145164);
    nsTypeAnswerStub.setData("d.ns.facebook.com");
    ResourceRecordImpl nsTypeAdditionalStub = new ResourceRecordImpl();
    nsTypeAdditionalStub.setName("a.ns.facebook.com");
    nsTypeAdditionalStub.setType(QueryType.A);
    nsTypeAdditionalStub.setTTL(147325);
    nsTypeAdditionalStub.setData("69.171.239.12");
    ServerResponseImpl nsTypeQueryResponseStub = new ServerResponseImpl();
    nsTypeQueryResponseStub.setAuthoritative(false);
    nsTypeQueryResponseStub.setAnswerCount(1);
    nsTypeQueryResponseStub.setAdditionalCount(1);
    nsTypeQueryResponseStub.addAnswer(nsTypeAnswerStub);
    nsTypeQueryResponseStub.addAdditional(nsTypeAdditionalStub);

    final String expected =
        "***Answer Section (1 records)***\r\n" +
        "NS \t d.ns.facebook.com \t 145164 \t nonauth\r\n" +
        "\r\n***Additional Section (1 records)***\r\n" +
        "a.ns.facebook.com \t IP \t 69.171.239.12 \t 147325 \t nonauth\r\n";
    assertPrintedData(nsTypeQueryResponseStub, expected);
  }

  @Test
  public void presentCNAMETypeAnswer() {
    ResourceRecordImpl cnameTypeStub = new ResourceRecordImpl();
    cnameTypeStub.setType(QueryType.CNAME);
    cnameTypeStub.setTTL(2018);
    cnameTypeStub.setData("mcgill.brightspace.com");
    ServerResponseImpl cnameTypeResponseStub = new ServerResponseImpl();
    cnameTypeResponseStub.setAuthoritative(false);
    cnameTypeResponseStub.setAnswerCount(1);
    cnameTypeResponseStub.setAdditionalCount(0);
    cnameTypeResponseStub.addAnswer(cnameTypeStub);

    final String expected =
        "***Answer Section (1 records)***\r\n" +
            "CNAME \t mcgill.brightspace.com \t 2018 \t nonauth\r\n";
    assertPrintedData(cnameTypeResponseStub, expected);
  }

  private void assertPrintedData(ServerResponse stub, String expected) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ServerResponsePresenter presenter =
        new ServerResponsePresenter(stub, out);
    presenter.present();
    String presentedOutput = out.toString();
    assertEquals(expected, presentedOutput);
  }
}