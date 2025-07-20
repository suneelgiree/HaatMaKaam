// Specifies the package where this configuration class resides, helping to organize the project structure.
package com.haatmakaam.backend.config;

// Imports the annotation that enables mapping of external properties (e.g., from a .properties file) to a Java object.
import org.springframework.boot.context.properties.ConfigurationProperties;
// Imports the annotation that declares this class as a source of Spring bean definitions, making it a configuration component.
import org.springframework.context.annotation.Configuration;

/**
 * A configuration class that holds all the necessary credentials and settings for interacting with the Twilio API.
 * By using @ConfigurationProperties, Spring Boot automatically populates the fields of this class
 * from the application's configuration files (application.properties or application.local.properties).
 * This practice decouples configuration from code and enhances security.
 */
@Configuration // Marks this class as a Spring configuration component, allowing it to be managed by the Spring container.
@ConfigurationProperties(prefix = "twilio") // Binds any external properties starting with the "twilio" prefix to the fields of this class.
public class TwilioConfig {

    // Holds the Twilio Account SID. Spring Boot's relaxed binding automatically maps 'twilio.account-sid' to this 'accountSid' field.
    private String accountSid;

    // Holds the Twilio Auth Token. Maps from 'twilio.auth-token'. This is a secret and should be kept secure.
    private String authToken;

    // Holds your Twilio phone number. Maps from 'twilio.phone-number'. This is the number from which SMS messages will be sent.
    private String phoneNumber;

    // Getter method for the Account SID. Allows other parts of the application to access this value.
    public String getAccountSid() {
        return accountSid;
    }

    // Setter method for the Account SID. Allows Spring to inject the value from the properties file.
    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    // Getter method for the Auth Token.
    public String getAuthToken() {
        return authToken;
    }

    // Setter method for the Auth Token.
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    // Getter method for the Twilio phone number.
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Setter method for the Twilio phone number.
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}