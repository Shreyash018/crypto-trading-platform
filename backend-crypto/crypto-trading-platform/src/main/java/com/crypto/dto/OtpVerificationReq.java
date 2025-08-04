
package com.crypto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OtpVerificationReq {
    @Email
    private String email;

    @NotBlank
    private String otp;
}
