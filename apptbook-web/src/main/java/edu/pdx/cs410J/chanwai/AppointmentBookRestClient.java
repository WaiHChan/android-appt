package edu.pdx.cs410J.chanwai;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class AppointmentBookRestClient extends HttpRequestHelper {
  private static final String WEB_APP = "apptbook";
  private static final String SERVLET = "appointments";

  private final String url;

  /**
   * Creates a client to the appointment book REST service running on the given host and port
   *
   * @param hostName The name of the host
   * @param port     The port
   */
  public AppointmentBookRestClient(String hostName, int port) {
    this.url = String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET);
  }

  /**
   * Returns the appointment for the given owner
   */
  public AppointmentBook getAppointments(String owner) throws IOException, ParserException {
    Response response = getToMyURL(Map.of("owner", owner));
    throwExceptionIfNotOkayHttpStatus(response);
    String text = response.getContent();
    TextParser parser = new TextParser(new StringReader(text));
    return parser.parse();
  }

  /**
   * Returns the appointments based on the date and time given
   * @param owner the owner of the appointment
   * @param begin the begin date and time for searching
   * @param end the end date and time for searching
   * @return a list of appointments within the range of time
   * @throws IOException
   * @throws ParserException
   */
  public AppointmentBook getAppointmentsBasedOnDate(String owner, String begin, String end) throws IOException, ParserException {
    Response response = getToMyURL(Map.of("owner", owner, "start", begin, "end", end));
    throwExceptionIfNotOkayHttpStatus(response);
    String text = response.getContent();
    TextParser parser = new TextParser(new StringReader(text));
    return parser.parse();
  }

  /**
   * It calls the doPost method in Servlet to create a new appointment
   * @param owner the owner of the appointment
   * @param description the description of the appointment
   * @param begin the begin date and time of the appointment
   * @param end the end date and time of the appointment
   * @throws IOException
   */
  public void createAppointment(String owner, String description, String begin, String end) throws IOException {
    Response response = postToMyURL(Map.of("owner", owner, "description", description, "start", begin, "end", end));
    throwExceptionIfNotOkayHttpStatus(response);
  }

  /**
   * A helper function to call doGet
   * @param appointmentInfo appointment information
   * @return Return the get method
   * @throws IOException
   */
  @VisibleForTesting
  Response getToMyURL(Map<String, String> appointmentInfo) throws IOException {
    return get(this.url, appointmentInfo);
  }

  /**
   * A helper function to call doPost
   * @param appointmentInfo appointment information
   * @return Return the post method
   * @throws IOException
   */
  @VisibleForTesting
  Response postToMyURL(Map<String, String> appointmentInfo) throws IOException {
    return post(this.url, appointmentInfo);
  }

  /**
   * Return the response of the get/ post method, and throw exception if error occur.
   * @param response the response from get or post method
   * @return code that indicate if success or failure
   */
  private Response throwExceptionIfNotOkayHttpStatus(Response response) {
    int code = response.getCode();
    if (code == 404){
      String message = "Unable to find the owner.";
      throw new RestException(code, message);
    }else if (code != HTTP_OK) {
      String message = response.getContent();
      throw new RestException(code, message);
    }
    return response;
  }

}
