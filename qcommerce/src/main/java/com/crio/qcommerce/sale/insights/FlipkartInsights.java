package com.crio.qcommerce.sale.insights;

import com.crio.qcommerce.contract.exceptions.AnalyticsException;
import com.crio.qcommerce.contract.insights.SaleAggregate;
import com.crio.qcommerce.contract.insights.SaleAggregateByMonth;
import com.crio.qcommerce.sale.FlipkartCandle;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class FlipkartInsights {

  public SaleAggregate getObjects(File csvFile, int year) throws AnalyticsException {

    // MappingIterator<flipkartcandle> fIterator = new
    // ObjectMapper().readerWithTypedSchemaFor(flipkartcandle.class).readValues(csvFile);

    List<FlipkartCandle> flist;
    try {
      flist = getList(csvFile);
      SaleAggregate saleAggregate = getAggregate(flist, year);

      return saleAggregate;

    } catch (IOException e) {
      throw new AnalyticsException("invalid");
    }

  }

  public SaleAggregate getAggregate(List<FlipkartCandle> flist, int year)
           throws AnalyticsException {
    String c = "complete";
    String p = "paid";
    String s = "shipped";
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
      for (FlipkartCandle f: flist) {
        if (f.getDate().getYear() == year) {
          if (f.getStatus().equals(c) || f.getStatus().equals(p) || f.getStatus().equals(s)) {
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

  public List<FlipkartCandle> getList(File csvFile) throws IOException, AnalyticsException {
    CsvMapper csvMapper = new CsvMapper();
    CsvSchema schema = csvMapper.typedSchemaFor(FlipkartCandle.class).withHeader();

    csvMapper.enable(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE);

    try {
      MappingIterator<FlipkartCandle> iter =
               csvMapper.readerFor(FlipkartCandle.class).with(schema).readValues(csvFile);
      return iter.readAll();
    } catch (Exception e) {
      throw new AnalyticsException("invalid");
    }


    // ObjectReader oReader = new CsvMapper().reader(flipkartcandle.class);
    // try (Reader reader = new FileReader(csvFile)) {
    //     MappingIterator<flipkartcandle> iter = oReader.readValues(reader);
    //     while(iter.hasNext()){
    //         flipkartcandle f = iter.next();
    //         System.out.println(f);
    //     }
    // }
        
    //return null;
  }

}