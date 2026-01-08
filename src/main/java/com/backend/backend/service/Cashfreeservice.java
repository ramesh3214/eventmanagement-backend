package com.backend.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.backend.backend.dto.CashfreeResponseWrapper;
import com.backend.backend.dto.OrderMeta;
import com.backend.backend.dto.OrderRequestdto;
import com.backend.backend.dto.PaymentResponse;

import java.util.Map;
import java.util.UUID;

@Service
public class Cashfreeservice {

  @Value("${cashfree.client-id}")
  private String clientId;

  @Value("${cashfree.client-secret}")
  private String clientSecret;

  @Value("${cashfree.api-version}")
  private String apiVersion;

  @Value("${cashfree.base-url}")
  private String baseUrl;

  // @Value("${cashfree.return-url}")
  // private String returnUrl; 

  private final RestTemplate restTemplate = new RestTemplate();

  // ===========================
  // CREATE ORDER
  // ===========================
  public PaymentResponse createOrder(OrderRequestdto orderRequest) {

    // 1️⃣ Generate order_id
    if (orderRequest.getOrder_id() == null || orderRequest.getOrder_id().isEmpty()) {
        orderRequest.setOrder_id("ORDER_" + UUID.randomUUID());
    }

    // 2️⃣ Set return_url correctly
    OrderMeta meta = new OrderMeta();
    meta.setReturn_url("https://super-malabi-d5a941.netlify.app/event-success");

    orderRequest.setOrder_meta(meta);

    String url = baseUrl + "/orders";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("x-client-id", clientId);
    headers.set("x-client-secret", clientSecret);
    headers.set("x-api-version", apiVersion);
    headers.set("x-request-id", UUID.randomUUID().toString());
    headers.set("x-idempotency-key", UUID.randomUUID().toString());

    HttpEntity<OrderRequestdto> entity = new HttpEntity<>(orderRequest, headers);

    ResponseEntity<PaymentResponse> response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            entity,
            PaymentResponse.class
    );

    if (response.getBody() == null) {
        throw new RuntimeException("Empty Cashfree response");
    }

    return response.getBody();
}


  // ===========================
  // VERIFY PAYMENT
  // ===========================
  public String verifyPayment(String orderId) {

    String url = baseUrl + "/orders/" + orderId;

    HttpHeaders headers = new HttpHeaders();
    headers.set("x-client-id", clientId);
    headers.set("x-client-secret", clientSecret);
    headers.set("x-api-version", apiVersion);

    HttpEntity<Void> entity = new HttpEntity<>(headers);

    ResponseEntity<Map> response = restTemplate.exchange(
        url,
        HttpMethod.GET,
        entity,
        Map.class
    );

    if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
      Object orderStatus = response.getBody().get("order_status"); // ✅ FIXED

      if (orderStatus != null) {
        return orderStatus.toString(); // PAID / ACTIVE / FAILED
      }
    }

    return "UNKNOWN";
  }
}
