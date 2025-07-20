// Specifies the package for service classes, which contain the core business logic of the application.
package com.haatmakaam.backend.service;

// Imports the TwilioConfig class to get access to Twilio credentials.
import com.haatmakaam.backend.config.TwilioConfig;
// Imports the main Twilio SDK class, which is needed to initialize the library.
import com.twilio.Twilio;
// Imports the Message class, used to create and send an SMS.
import com.twilio.rest.api.v2010.account.Message;
// Imports the PhoneNumber class, which represents a valid phone number for the Twilio API.
import com.twilio.type.PhoneNumber;
// Imports Spring's @Autowired annotation for automatic dependency injection.
import org.springframework.beans.factory.annotation.Autowired;
// Imports Spring's @Service annotation to declare this class as a business service.
import org.springframework.stereotype.Service;

// Imports Jakarta's @PostConstruct annotation, used for lifecycle callback methods.
import jakarta.annotation.PostConstruct;

/**
 * A service dedicated to handling OTP (One-Time Password) operations.
 * It uses the Twilio SDK to send OTP codes via SMS to users' phones.
 * This service is a key component for user verification and two-factor authentication flows.
 */
@Service // Declares this class as a Spring service, making it eligible for component scanning and dependency injection.
public class OtpService {

    // A final variable to hold the injected Twilio configuration. 'final' ensures it's initialized once.
    private final TwilioConfig twilioConfig;

    /**
     * Constructor for OtpService.
     * @param twilioConfig An instance of TwilioConfig, automatically provided by Spring's dependency injection framework.
     *                     This is the recommended way to inject dependencies (constructor injection).
     */
    @Autowired // Marks the constructor for Spring to automatically inject the required TwilioConfig bean.
    public OtpService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    /**
     * An initialization method that runs automatically after the service has been constructed and its dependencies have been injected.
     * It uses the credentials from TwilioConfig to initialize the Twilio SDK, making it ready for use.
     * This ensures that the SDK is initialized only once when the application starts up.
     */
    @PostConstruct // This annotation ensures that this method is executed after dependency injection is done to perform any initialization.
    public void initTwilio() {
        // Initializes the Twilio client with the Account SID and Auth Token from the configuration.
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    /**
     * Sends a given OTP code to a specified user's phone number.
     * @param userPhone The destination phone number, which should be in E.164 format (e.g., "+9779849042183").
     * @param otp The 6-digit OTP string to be sent in the message.
     * @return The unique message SID returned by Twilio upon successful sending, which can be used for logging or tracking.
     */
    public String sendOtp(String userPhone, String otp) {
        // Defines the body of the SMS message. Personalizing it with your app name is good practice.
        String messageBody = "Your HaatMaKaam OTP code is: " + otp;

        // Uses the Twilio SDK's Message.creator to build and send the SMS.
        Message message = Message.creator(
            new PhoneNumber(userPhone),                      // The recipient's phone number.
            new PhoneNumber(twilioConfig.getPhoneNumber()),  // Your Twilio phone number (the sender).
            messageBody                                      // The text of the message.
        ).create(); // The create() method sends the API request to Twilio.

        // Returns the unique identifier (SID) of the message.
        return message.getSid();
    }
}