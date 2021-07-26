package edu.pdx.cs410J.chanwai;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AppointmentBookServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class AppointmentBookServletTest {

  @Test
  void gettingAppointmentBookReturnsTextFormat() throws ServletException, IOException {
    String owner = "Jim";
    String description = "Eyes Check";
    String beginDate = "1/1/2019";
    String beginTime = "10:00";
    String amPm = "AM";
    String endDate = "1/1/2019";
    String endTime = "11:00";
    Date begin_date = null;
    Date end_date = null;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    try {
      begin_date = df.parse(beginDate + " " + beginTime + " " + amPm);
      end_date = df.parse(endDate + " " + endTime + " " + amPm);
    }catch (ParseException e){
    }

    AppointmentBookServlet servlet = new AppointmentBookServlet();
    AppointmentBook book = servlet.createAppointmentBook(owner);
    book.addAppointment(new Appointment(owner, description, begin_date, end_date));

    Map<String, String> queryParams = Map.of("owner", owner);
    StringWriter sw = invokeServletMethod(queryParams, servlet::doGet);

    String text = sw.toString();
    assertThat(text, containsString(owner));
    assertThat(text, containsString(description));
  }

  private StringWriter invokeServletMethod(Map<String, String> params, ServletMethodInvoker invoker) throws IOException, ServletException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    params.forEach((key, value) -> when(request.getParameter(key)).thenReturn(value));

    HttpServletResponse response = mock(HttpServletResponse.class);

    StringWriter sw = new StringWriter();
    when(response.getWriter()).thenReturn(new PrintWriter(sw));

    invoker.invoke(request, response);

    verify(response).setStatus(HttpServletResponse.SC_OK);
    return sw;
  }

  @Test
  void addAppointment() throws ServletException, IOException {
    AppointmentBookServlet servlet = new AppointmentBookServlet();

    String owner = "Jim";
    String description = "Eyes Check";
    String begin = "1/1/2019 12:30 PM";
    String end = "1/1/2019 1:00 PM";

    invokeServletMethod(Map.of("owner", owner, "description", description, "start", begin, "end", end), servlet::doPost);

    AppointmentBook book = servlet.getAppointmentBook(owner);
    assertThat(book, notNullValue());
    assertThat(book.getOwnerName(), equalTo(owner));

    Collection<Appointment> appointments = book.getAppointments();
    assertThat(appointments, hasSize(1));

    Appointment appointment = book.getAppointments().iterator().next();
    assertThat(appointment.getDescription(), equalTo(description));
  }

  private interface ServletMethodInvoker {
    void invoke(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
  }
}
