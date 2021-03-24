package com.crio.qcommerce.sale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"txn_id","username","transaction_status","transaction_date","amount"})
public class EbayCandle implements Candle {

  @JsonProperty("txn_id")
  int txn_id;
  @JsonProperty("username")
  String username;
  @JsonProperty("transaction_status")
  String transaction_status;
  @JsonProperty("transaction_date")
  String transaction_date;
  @JsonProperty("amount")
  int amount;

  @Override
  public LocalDate getDate() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
    LocalDate localDate = LocalDate.parse(transaction_date, formatter);
    return localDate;
  }

  @Override
  public String getStatus() {
    return transaction_status;
  }

  @Override
  public Double getAmount() {
    return (double) amount;
  }
    
}