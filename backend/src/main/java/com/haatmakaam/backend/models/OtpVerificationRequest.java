package com.haatmakaam.backend.models;

public record OtpVerificationRequest(
    String phone,
    String otp
) {
}