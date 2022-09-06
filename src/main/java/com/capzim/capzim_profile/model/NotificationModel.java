package com.capzim.capzim_profile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * @author Johnson Andrew Siziba (sizibajohnsona@gmail.com,+263784310119)
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NotificationModel {
    private String recipient;
    private String subject;
    private String message;
    private String type;
}
