package com.example.demo.Service;

import com.eatthepath.pushy.apns.ApnsClient;
import com.eatthepath.pushy.apns.ApnsClientBuilder;
import com.eatthepath.pushy.apns.ApnsPushNotification;
import com.eatthepath.pushy.apns.auth.ApnsSigningKey;
import com.eatthepath.pushy.apns.util.ApnsPayloadBuilder;
import com.eatthepath.pushy.apns.util.SimpleApnsPayloadBuilder;
import com.eatthepath.pushy.apns.util.SimpleApnsPushNotification;
import com.eatthepath.pushy.apns.util.TokenUtil;
import com.example.demo.entity.DeviceToken;
import com.example.demo.entity.Member;
import com.example.demo.entity.PushPayload;
import com.example.demo.repository.DeviceTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
public class ApnsPushService {


    private static ApnsClient apnsClient;

    private static DeviceTokenRepository deviceTokenRepository;


    @PostConstruct
    void init() {
        try {
            String teamId = "CFQ4ZSUWR7";
            String keyId = "2QPURU9C8F";
            apnsClient = new ApnsClientBuilder()
                    .setApnsServer(ApnsClientBuilder.PRODUCTION_APNS_HOST)
                    .setSigningKey(ApnsSigningKey.loadFromPkcs8File(new File("src/main/resources/apnsPushKey.p8"),
                            teamId, keyId))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public SimpleApnsPushNotification getPushNotification(PushPayload content, String deviceToken){
        final SimpleApnsPushNotification pushNotification;

        {
            final ApnsPayloadBuilder payloadBuilder = new SimpleApnsPayloadBuilder();
            payloadBuilder.setAlertTitle(content.getAlertTitle());
            payloadBuilder.setAlertBody(content.getAlertBody());
            payloadBuilder.setSoundFileName(content.getSound());

            final String payload = payloadBuilder.build();
            final String token = TokenUtil.sanitizeTokenString(deviceToken);

            pushNotification = new SimpleApnsPushNotification(token, "dmz.ssopa", payload);

            return pushNotification;
        }
    }

    public void sendPushByMember(List<Member> members, PushPayload content){
        ArrayList<String> tkns = new ArrayList<>();

        members.forEach(member -> {

            List<DeviceToken> tokens = deviceTokenRepository.findAllByMemberId(member);

            if(tokens.isEmpty()) {
                // ID에 해당하는 User 객체가 없는 경우
            }else {


                tokens.forEach(token -> {
                    tkns.add(token.getToken());
                });

                sendPush(tkns, content);

            }
        });
    }

    public void sendPush(List<String> deviceTokens, PushPayload payload) {

        List<ApnsPushNotification> collectionOfPushNotifications = new ArrayList<>();
        deviceTokens.forEach(deviceToken -> {
            SimpleApnsPushNotification pushNotification = getPushNotification(payload, deviceToken);
            collectionOfPushNotifications.add(pushNotification);
        });


        for (final ApnsPushNotification pushNotification : collectionOfPushNotifications) {
            final CompletableFuture sendNotificationFuture = apnsClient.sendNotification(pushNotification);

            sendNotificationFuture.whenComplete((response, cause) -> {
                // This will get called when the sever has replied. response will
                // be null and cause will be non-null if something went wrong when
                // sending the notification.
                System.out.println(response);

                if (response != null) {
                    // Handle the push notification response as before from here.
                } else {
                    // Something went wrong when trying to send the notification to the
                    // APNs server. Note that this is distinct from a rejection from
                    // the server, and indicates that something went wrong when actually
                    // sending the notification or waiting for a reply.
                    System.out.print("Failed to send push notification :");
                    System.out.println(cause);
                }
            });
        }
    }
}


