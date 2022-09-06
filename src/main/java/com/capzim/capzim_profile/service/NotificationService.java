package com.capzim.capzim_profile.service;

import com.capzim.capzim_profile.config.MQConfig;
import com.capzim.capzim_profile.model.NotificationModel;
import com.capzim.capzim_profile.model.UserResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;


/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 * Date: 6/9/2022
 * Time: 09:06
 */


@RequiredArgsConstructor
@Slf4j
@Service
public class NotificationService {
    private final RabbitTemplate rabbitTemplate;

    @LoadBalanced
    private final RestTemplate restTemplate;

    public void sendTempPhoneNumberVerification(String verificationToken, String phoneNumber){


        NotificationModel notificationModel = new NotificationModel();
        notificationModel.setRecipient(phoneNumber);

        notificationModel.setMessage(
                "Your verification token is: " + verificationToken +
                        "\n\nThe token will expire after 4 minutes." +
                        "\n\nPlease do not share this secret token with anyone." +
                        "\n\nIf you did not initialize this process, no further action is required." +
                        "\n\nRegards," +
                        "\n\nCapZim"
        );

        notificationModel.setSubject("Verification Token");
        notificationModel.setType("phone");

        rabbitTemplate.convertAndSend(
                MQConfig.MESSAGE_EXCHANGE,
                MQConfig.MESSAGE_ROUTING_KEY,
                notificationModel
        );

    }

    public void sendTempEmailAddressVerification(String token, String email) {

        NotificationModel notificationModel = new NotificationModel();
        notificationModel.setRecipient(email);

        notificationModel.setMessage(
                "Your verification token is: " + token +
                        "\n\nThe token will expire after 4 minutes." +
                        "\n\nPlease do not share this secret token with anyone." +
                        "\n\nIf you did not initialize this process, no further action is required." +
                        "\n\nRegards," +
                        "\n\nCapZim"
        );

        notificationModel.setSubject("Verification Token");
        notificationModel.setType("email");

        rabbitTemplate.convertAndSend(
                MQConfig.MESSAGE_EXCHANGE,
                MQConfig.MESSAGE_ROUTING_KEY,
                notificationModel
        );
    }
}
