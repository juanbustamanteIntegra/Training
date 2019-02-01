package services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

public class SESEmail {

  static final String FROM = "juan.bustamante@integrait.co";

  // static final String CONFIGSET = "ConfigSet";

  // The subject line for the email.
  static final String SUBJECT = "IntegraIT - Session Closed";

  // The HTML body for the email.
  static final String HTMLBODY = "<h1>Logout!</h1>"+
  "<p>You have recently closed session in IntegraIT Training App.";

  // The email body for recipients with non-HTML email clients.
  static final String TEXTBODY = "You have recently closed session in IntegraIT Training App.";

  // The subject line for the email.
  static final String SUBJECT2 = "IntegraIT - Sign Up";

  // The HTML body for the email.
  static final String HTMLBODY2 = "<h1>Welcome!</h1>"+
  "<p>You've just signed up to the IntegraIT Training App. See you soon!";

  // The email body for recipients with non-HTML email clients.
  static final String TEXTBODY2 = "You've just signed up to the IntegraIT Training App. See you soon!";

  public static void sendEmail(boolean out, String email){

    // Create a Properties object to contain connection configuration information.
    try {
      String body = out? HTMLBODY:HTMLBODY2;
      String textBodyEmail = out? TEXTBODY:TEXTBODY2;
      String subjectEmail = out? SUBJECT:SUBJECT2;

      AmazonSimpleEmailService client =
      AmazonSimpleEmailServiceClientBuilder.standard()
      .withRegion(Regions.US_EAST_1).build();
      SendEmailRequest request = new SendEmailRequest()
      .withDestination(
      new Destination().withToAddresses(email))
      .withMessage(new Message()
      .withBody(new Body()
      .withHtml(new Content()
      .withCharset("UTF-8").withData(body))
      .withText(new Content()
      .withCharset("UTF-8").withData(textBodyEmail)))
      .withSubject(new Content()
      .withCharset("UTF-8").withData(subjectEmail)))
      .withSource(FROM);
      // .withConfigurationSetName(CONFIGSET);
      client.sendEmail(request);
      System.out.println("Email sent!");
    } catch (Exception ex) {
      System.out.println("The email was not sent. Error message: "
      + ex.getMessage());
    }
  }
}
