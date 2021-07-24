package edu.pdx.cs410J.chanwai;

import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Integration test that tests the REST calls made by {@link AppointmentBookRestClient}
 */
@TestMethodOrder(MethodName.class)
class AppointmentBookRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "1026");

  private AppointmentBookRestClient newAppointmentBookRestClient() {
    int port = Integer.parseInt(PORT);
    return new AppointmentBookRestClient(HOSTNAME, port);
  }

//  @Test
//  void test0RemoveAllAppointmentBooks() throws IOException {
//    AppointmentBookRestClient client = newAppointmentBookRestClient();
//    client.removeAllAppointmentBooks();
//  }
//
//  @Test
//  void test2CreateAppointmentBookWithOneAppointment() throws IOException {
//    AppointmentBookRestClient client = newAppointmentBookRestClient();
//    String owner = "Jim";
//    String description = "Eyes Check more";
//    String begin = "1/1/2019 10:30 AM";
//    String end = "1/1/2019 11:00 AM";
//    client.createAppointment(owner, description, begin, end);
//
//    String appointmentBookText = client.getAppointments(owner);
//    assertThat(appointmentBookText, containsString(owner));
//    assertThat(appointmentBookText, containsString(description));
//  }
//
//  @Test
//  void test4MissingRequiredParameterReturnsPreconditionFailed() throws IOException {
//    AppointmentBookRestClient client = newAppointmentBookRestClient();
//    HttpRequestHelper.Response response = client.postToMyURL(Map.of());
//    assertThat(response.getContent(), containsString("Precondition Failed"));
//    assertThat(response.getCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));
//  }

}
