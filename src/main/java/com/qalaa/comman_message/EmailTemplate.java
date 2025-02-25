package com.qalaa.comman_message;

public class EmailTemplate {

    public static String mailMessageForForgotPin(int otp, String email) {
        return "<html>" + "<head>" + "<style>" + "body { font-family: Arial, sans-serif; margin: 0; padding: 0; }"
                + ".container { max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e0e0e0; }"
                + ".header { background-color: #f5f5f5; padding: 10px; text-align: center; }"
                + ".content { padding: 20px; }"
                + ".footer { background-color: #f5f5f5; padding: 10px; text-align: center; }"
                + ".otp { font-size: 24px; font-weight: bold; text-align: center; color: #ff6600; }"
                + ".footer { background-color: #f5f5f5; padding: 20px; text-align: center; color: #777; }"
                + ".company-info { font-size: 14px; color: #007BFF; }" + "</style>" + "</head>" + "<body>"
                + "<div class='container'>" + "<div class='header'>" + "<h2>eBike PIN Reset</h2>" + "</div>"
                + "<div class='content'>" + "<p>Dear " + email + ",</p>"
                + "<p>We understand you've forgotten your eBike PIN, but don't worry; we're here to help you get back on the road. Here's a quick and secure process to reset your PIN:</p>"
                + "<p><strong>Step 1: Verify Your Identity</strong></p>"
                + "<p>We've generated a unique 4-digit OTP (One-Time Password) for you to confirm your identity. Please use this OTP to proceed.</p>"
                + "<p class='otp'>Your OTP: " + otp + "</p>" + "<p><strong>Step 2: Reset Your PIN</strong></p>"
                + "<p>Open your eBike app on your mobile device.</p>" + "<p>Go to the 'Change PIN' section.</p>"
                + "<p>Use the OTP provided in this email as your temporary existing PIN.</p>"
                + "<p>Now you can create a new 4-digit PIN.</p>" + "<p>Confirm the new PIN for added security.</p>"
                + "<p>Your eBike is now ready to go with the new PIN.</p>"
                + "<p>Please remember to keep your PIN confidential and do not share it with anyone. Your safety is our priority.</p>"
                + "<p>If you did not request this PIN reset or have any concerns about your eBike's security, please reach out to our support team at [Support Email or Phone Number].</p>"
                + "<p>Thank you for being part of our eBike community. Safe riding!</p>" + "</div>"
                + "<div class='footer'>" + "<p class='company-info'>Best Regards,</p>"
                + "<p class='company-info'>Monarch Innovation</p>"
                + "<p class='company-info'>[Your Contact Information]</p>"
                + "<p>This is an automated email. Please do not reply.</p>" + "</div>" + "</div>" + "</body>"
                + "</html>";
    }

    public static String mailMessageForGeneral(String otp, String email) {
        return "<html>" + "<head>" + "<style>" + "body { font-family: Arial, sans-serif; margin: 0; padding: 0; }"
                + ".container { max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #e0e0e0; }"
                + ".header { background-color: #f5f5f5; padding: 10px; text-align: center; }"
                + ".content { padding: 20px; }"
                + ".footer { background-color: #f5f5f5; padding: 10px; text-align: center; }" + "</style>" + "</head>"
                + "<body>" + "<div class='container'>" + "<div class='header'>" + "<h2>OTP Verification</h2>" + "</div>"
                + "<div class='content'>" + "<p>Dear " + email + ",</p>"
                + "<p>We have received a request to verify your identity. Please use the following One-Time Password (OTP) to proceed:</p>"
                + "<p style='font-size: 24px; font-weight: bold; text-align: center; color: #ff6600;'>" + otp + "</p>"
                + "<p>Please do not share this OTP with anyone for security reasons.</p>"
                + "<p>If you did not initiate this request, please ignore this email.</p>" + "<p>Best regards,</p>"
                + "<p>Qalaa</p>" + "</div>" + "<div class='footer'>"
                + "<p>This is an automated email. Please do not reply.</p>" + "</div>" + "</div>" + "</body>"
                + "</html>";

    }

    public static String mailMessageForInviteUser(String invitedEmail, String adminEmail) {
        return "<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<style>\n" + "  body {\n"
                + "    font-family: Arial, sans-serif;\n" + "    line-height: 1.6;\n" + "    padding: 20px;\n" + "  }\n"
                + "  .container {\n" + "    max-width: 600px;\n" + "    margin: 0 auto;\n"
                + "    background-color: #f9f9f9;\n" + "    padding: 30px;\n" + "    border-radius: 8px;\n" + "  }\n"
                + "  h1 {\n" + "    color: #333333;\n" + "  }\n" + "  p {\n" + "    color: #555555;\n" + "  }\n"
                + "  .button {\n" + "    display: inline-block;\n" + "    padding: 10px 20px;\n"
                + "    text-decoration: none;\n" + "    background-color: #007bff;\n" + "    color: white;\n"
                + "    border-radius: 5px;\n" + "  }\n" + "</style>\n" + "</head>\n" + "<body>\n"
                + "<div class=\"container\">\n" + "  <h1>Welcome to NINE09's eBike Family!</h1>\n" + "  <p>Dear "
                + invitedEmail + ",</p>\n"
                + "  <p>Congratulations and welcome to the NINE09 eBike community! We're thrilled to have you join us in experiencing the freedom and convenience of our innovative eBikes. "
                + adminEmail
                + " , the proud owner of our NINE09 eBike CX01, has granted you access to ride with us.</p>\n"
                + "  <p>To make your onboarding seamless, here's a simple guide to create your profile and start enjoying your eBike journey:</p>\n"
                + "  <ol>\n"
                + "    <li>Download the NINE09 App: Head over to the App Store or Google Play Store and search for \"NINE09\". Download our app onto your smartphone – it's the gateway to your eBike adventures.</li>\n"
                + "    <li>Signup/Sign in: Open the app and either sign up for a new account or sign in if you already have one. Use the email address this invitation was sent to.</li>\n"
                + "    <li>Access Your eBike: Once you're logged in, locate the eBike labeled as \"New\" in your app's bike list. Tap on it to get started with setting up your eBike profile.</li>\n"
                + "    <li>Customize Your Profile: Add your desired profile name and personalize it with an avatar. This adds a touch of uniqueness to your riding experience.</li>\n"
                + "    <li>Hit the Road: With your profile set up, you're all set to start riding your eBike! Feel the thrill and convenience NINE09 offers.</li>\n"
                + "  </ol>\n"
                + "  <p>Welcome aboard, and get ready to explore new paths with NINE09! We're delighted to have you as part of our eBike family.</p>\n"
                + "  <p>Happy Riding,</p>\n" + "  <p>The NINE09 Team</p>\n" + "</div>\n" + "</body>\n" + "</html>";

    }
}

