package com.capstone.kelompok10.service.implementation;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.kelompok10.model.entity.UserEntity;
import com.capstone.kelompok10.model.payload.ResendToken;
import com.capstone.kelompok10.model.payload.ForgotPassword;
import com.capstone.kelompok10.model.payload.RegistrationRequest;
import com.capstone.kelompok10.repository.UserRepository;
import com.capstone.kelompok10.service.email.EmailSenderService;
import com.capstone.kelompok10.service.email.EmailValidatorService;
import com.capstone.kelompok10.service.interfaces.RegisterService;
import com.capstone.kelompok10.service.interfaces.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailValidatorService emailValidatorService;

    @Autowired
    UserService userService;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    PasswordEncoder crypt;

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public String register(RegistrationRequest request) {
        UserEntity user = new UserEntity();
        boolean isValidEmail = emailValidatorService.test(request.getEmail());
        if (isValidEmail && userRepository.findByUsername(request.getUsername()) == null && userRepository.findByEmail(request.getEmail()) == null){
            String token = UUID.randomUUID().toString();
            user.setUsername(request.getUsername());
            user.setPassword(crypt.encode(request.getPassword()));
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setAddress(request.getAddress());
            user.setToken(token);
            
            userRepository.save(user);
            String link = "https://www.api.rafdev.my.id/auth/confirm?token=" + token;
            emailSenderService.sendEmail(request.getEmail(), buildEmail(request.getUsername(), link));
            return token;
        } else {
            throw new IllegalStateException("Email not valid or Email or Username already used");
        }
    }


    @Override
    public String resendToken(ResendToken resendToken) {
        UserEntity user = userRepository.findByEmail(resendToken.getEmail());
        if (userRepository.findByEmail(resendToken.getEmail()) != null){
            user.setToken(null);
            userRepository.save(user);

            String token = UUID.randomUUID().toString();
            String username = user.getUsername().toString();
            String email = user.getEmail().toString();
            user.setEmail(email);
            user.setToken(token);
            userRepository.save(user);
            String link = "https://www.api.rafdev.my.id/auth/confirm?token=" + token;
            emailSenderService.sendEmail(resendToken.getEmail(), buildEmail(username, link));
            return token;
        } else {
            throw new IllegalStateException("Email not found");
        }
    }

    @Override
    public String resetPassword(ResendToken reset) {
        UserEntity user = userRepository.findByEmail(reset.getEmail());
        if (userRepository.findByEmail(reset.getEmail()) != null){
            user.setToken(null);
            userRepository.save(user);

            String token = UUID.randomUUID().toString();
            String username = user.getUsername().toString();
            String email = user.getEmail().toString();
            user.setEmail(email);
            user.setToken(token);
            userRepository.save(user);
            String link = "https://www.api.rafdev.my.id/auth/reset?token=" + token;
            emailSenderService.sendEmail(reset.getEmail(), buildEmail(username, link));
            return token;
        } else {
            throw new IllegalStateException("Email not found");
        }
    }

    @Override
    public boolean confirmToken(String token) {
        UserEntity user = userRepository.findByToken(token);
        if(user == null){
            return false;
        }else{
            user.setToken(null);
            userRepository.save(user);
            userService.addRoleToUser(user.getUsername(), "ROLE_USER");
        }
        return true;
    }

    @Override
    public boolean forgotPassword(String token, ForgotPassword forgotPassword) {
        UserEntity user = userRepository.findByToken(token);
        if(user == null){
            return false;
        }else{
            user.setToken(null);
            user.setPassword(crypt.encode(forgotPassword.getPassword()));
            userRepository.save(user);
        }
        return true;
    }

    @Override
    public String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
        "\n" +
        "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
        "\n" +
        "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
        "    <tbody><tr>\n" +
        "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
        "        \n" +
        "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
        "          <tbody><tr>\n" +
        "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
        "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
        "                  <tbody><tr>\n" +
        "                    <td style=\"padding-left:10px\">\n" +
        "                  \n" +
        "                    </td>\n" +
        "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
        "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
        "                    </td>\n" +
        "                  </tr>\n" +
        "                </tbody></table>\n" +
        "              </a>\n" +
        "            </td>\n" +
        "          </tr>\n" +
        "        </tbody></table>\n" +
        "        \n" +
        "      </td>\n" +
        "    </tr>\n" +
        "  </tbody></table>\n" +
        "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
        "    <tbody><tr>\n" +
        "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
        "      <td>\n" +
        "        \n" +
        "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
        "                  <tbody><tr>\n" +
        "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
        "                  </tr>\n" +
        "                </tbody></table>\n" +
        "        \n" +
        "      </td>\n" +
        "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
        "    </tr>\n" +
        "  </tbody></table>\n" +
        "\n" +
        "\n" +
        "\n" +
        "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
        "    <tbody><tr>\n" +
        "      <td height=\"30\"><br></td>\n" +
        "    </tr>\n" +
        "    <tr>\n" +
        "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
        "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
        "        \n" +
        "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n CC : Capstone Kelompok 10 <p>See you soon</p>" +
        "        \n" +
        "      </td>\n" +
        "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
        "    </tr>\n" +
        "    <tr>\n" +
        "      <td height=\"30\"><br></td>\n" +
        "    </tr>\n" +
        "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
        "\n" +
        "</div></div>";
    }
}
