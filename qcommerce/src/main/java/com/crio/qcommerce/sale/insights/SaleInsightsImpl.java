package com.crio.qcommerce.sale.insights;

import com.crio.qcommerce.contract.exceptions.AnalyticsException;
import com.crio.qcommerce.contract.insights.SaleAggregate;
import com.crio.qcommerce.contract.insights.SaleInsights;
import com.crio.qcommerce.contract.resolver.DataProvider;
import java.io.File;
import java.io.IOException;
// import java.util.List;


public class SaleInsightsImpl implements SaleInsights {

  @Override
  public SaleAggregate getSaleInsights(DataProvider dataProvider, int year) 
            throws IOException, AnalyticsException {
    File csvFile = dataProvider.resolveFile();
    String vendorName = dataProvider.getProvider();

    if (vendorName == "flipkart") {
      FlipkartInsights fInsights = new FlipkartInsights();
      return fInsights.getObjects(csvFile, year);
    } else if (vendorName == "amazon") {
      AmazonInsights aInsights = new AmazonInsights();
      return aInsights.getObjects(csvFile, year);
    } else if (vendorName == "ebay") {
      EbayInsights eInsights = new EbayInsights();
      return eInsights.getObjects(csvFile, year);
    }

    return null;
  }

    

    
}