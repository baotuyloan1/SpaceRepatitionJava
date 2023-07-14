package com.example.dto.mobile;

import com.example.payload.response.UserInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author BAO 7/14/2023
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseMobile extends UserInfoResponse {



    private String jwtToken;
}
