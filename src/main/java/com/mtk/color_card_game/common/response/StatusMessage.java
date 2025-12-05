package com.mtk.color_card_game.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusMessage {
    private boolean success;
    private String status;
    private int statusCode;
    private Object result;
    private String message;
    private String request;

    public StatusMessage(boolean success, String status, int statusCode, Object result, String message) {
        this.success = success;
        this.status = status;
        this.statusCode = statusCode;
        this.result = result;
        this.message = message;
    }
}
