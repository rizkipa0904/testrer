package com.capstone.kelompok10.service.interfaces;

import com.capstone.kelompok10.model.payload.ResendToken;
import com.capstone.kelompok10.model.payload.ForgotPassword;
import com.capstone.kelompok10.model.payload.RegistrationRequest;

public interface RegisterService {
    String register(RegistrationRequest request);
    boolean confirmToken(String token);
    String resendToken(ResendToken resendToken);
    String buildEmail(String name, String link);
    String resetPassword(ResendToken reset);
    boolean forgotPassword(String token, ForgotPassword forgotPassword);
}
