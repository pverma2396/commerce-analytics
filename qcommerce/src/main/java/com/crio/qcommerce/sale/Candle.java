package com.crio.qcommerce.sale;

import java.time.LocalDate;

public interface Candle {

  LocalDate getDate();

  String getStatus();

  Double getAmount();

}