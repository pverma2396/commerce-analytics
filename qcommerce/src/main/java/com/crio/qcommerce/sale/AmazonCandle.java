package com.crio.qcommerce.sale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDate;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"transaction_id","ext_txn_id","userid","status","date","amount"})
public class AmazonCandle implements Candle {

  @JsonProperty("transaction_id")
  Double transaction_id;
  @JsonProperty("ext_txn_id")
  String ext_txn_id;
  @JsonProperty("userid")
  String userid;
  @JsonProperty("status")
  String status;
  @JsonProperty("date")
  String date;
  @JsonProperty("amount")
  Double amount;

  @Override
  public LocalDate getDate() {
    return LocalDate.parse(date);
  }

  @Override
  public String getStatus() {
    return status;
  }

  @Override
  public Double getAmount() {
    return amount;
  }
    
}