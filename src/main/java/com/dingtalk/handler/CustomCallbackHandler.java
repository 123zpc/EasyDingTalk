package com.dingtalk.handler;

import com.dingtalk.model.request.CardCallbackRequest;
import com.dingtalk.model.response.CardCallbackResponse;

import java.util.Map;

/**
 * 回调处理器接口
 */
public interface CustomCallbackHandler {
    CardCallbackResponse handleCallback(CardCallbackRequest request, Map<String, Object> params);
}