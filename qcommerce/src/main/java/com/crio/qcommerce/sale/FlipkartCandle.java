package com.crio.qcommerce.sale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"transaction_id","external_transaction_id","user_id",
            "transaction_date","transaction_status","amount"})
public class FlipkartCandle implements Candle {
    
  @JsonProperty("transaction_id")
  Double transaction_id;
  @JsonProperty("external_transaction_id")
  String external_transaction_id;
  @JsonProperty("user_id")
  String user_id;
  @JsonProperty("transaction_date")
  String transaction_date;
  @JsonProperty("transaction_status")
  String transaction_status;
  @JsonProperty("amount")
  Double amount;


  @Override
  public String getStatus() {
    return this.transaction_status;
  }

  @Override
  public Double getAmount() {
    return this.amount;
  }

  @Override
  public LocalDate getDate() {
    return LocalDate.parse(this.transaction_date);
  }
    
}