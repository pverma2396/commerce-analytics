package com.crio.qcommerce.sale.insights;

import com.crio.qcommerce.contract.exceptions.AnalyticsException;
import com.crio.qcommerce.contract.insights.SaleAggregate;
import com.crio.qcommerce.contract.insights.SaleAggregateByMonth;
import com.crio.qcommerce.sale.EbayCandle;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class EbayInsights {

  public SaleAggregate getObjects(File csvFile, int year) throws AnalyticsException {

    List<EbayCandle> flist;

    try {
      flist = getList(csvFile);

      return getAggregate(flist, year);

    } catch (IOException e) {
      throw new AnalyticsException("invalid");
    }

  }


  public SaleAggregate getAggregate(List<EbayCandle> flist, int year) throws AnalyticsException {
    String c = "complete";
    String d = "Delivered";
    SaleAggregate saleAggregate = new SaleAggregate();
    saleAggregate.setTotalSales(0.00);
    //System.out.println(saleAggregate.getTotalSales());
    List<SaleAggregateByMonth> saleAggregateByMonths = new ArrayList<SaleAggregateByMonth>();
    for (int i = 0;i <= 12;i++) {
      SaleAggregateByMonth saleAggregateByMonth = new SaleAggregateByMonth(i, 0.00);
      saleAggregateByMonths.add(saleAggregateByMonth);
    }

    //saleAggregate.setAggregateByMonths(saleAggregateByMonths);

    try {
      for (EbayCandle f: flist) {
        if (f.getDate().getYear() == year) {
          if (f.getStatus().equals(c) || f.getStatus().equals(d)) {
            Double temp = saleAggregate.getTotalSales();
            Double amount = f.getAmount();
            Double monthAmount = amount 
                     + (saleAggregateByMonths.get(f.getDate().getMonthValue())).getSales();
            int index = f.getDate().getMonthValue();
            saleAggregate.setTotalSales(temp + f.getAmount());
            //System.out.println(saleAggregate.getTotalSales());
            (saleAggregateByMonths.get(index)).setSales(monthAmount);
          }
        }
      }
    
      List<SaleAggregateByMonth> result = new ArrayList<SaleAggregateByMonth>();
    
      for (int i = 1;i < 12;i++) {
        result.add(saleAggregateByMonths.get(i));
      }
    
      saleAggregate.setAggregateByMonths(result);
    
      return saleAggregate;
    } catch (Exception e) {
      throw new AnalyticsException("invalid");
    }
  }


  public List<EbayCandle> getList(File csvFile) throws IOException, AnalyticsException {
    CsvMapper csvMapper = new CsvMapper();
    CsvSchema schema = csvMapper.typedSchemaFor(EbayCandle.class).withHeader();

    csvMapper.enable(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE);

    MappingIterator<EbayCandle> iter = 
              csvMapper.readerFor(EbayCandle.class).with(schema).readValues(csvFile);

    return iter.readAll();
  }

}