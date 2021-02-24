package run.halo.app.utils;

import cn.edu.hfut.dmic.webcollector.crawldb.DBManager;
import cn.edu.hfut.dmic.webcollector.crawler.Crawler;
import cn.edu.hfut.dmic.webcollector.fetcher.Executor;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.RocksDBManager;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import run.halo.app.model.Trade;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class TradeCrawlerUtils {

    public static final String AASTOCKS_9923_YEAHKA = "http://www.aastocks.com/tc/stocks/analysis/blocktrade.aspx?symbol=09923";

    public static void doRequest() {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(AASTOCKS_9923_YEAHKA);
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                if (response.getStatusLine().getStatusCode() != 200) {
                    return;
                }
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                parseTradeJson(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseTradeJson(String content) throws com.fasterxml.jackson.core.JsonProcessingException {
        Scanner scanner = new Scanner(content);

        JsonFactory factory = new JsonFactory();
        factory.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        factory.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);

        ObjectMapper objectMapper = new ObjectMapper(factory);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("var _stat = ")) {
                String json = line.substring("var _stat = ".length(), line.lastIndexOf(";"));
                TradeList tradeList = objectMapper.readValue(json, TradeList.class);
                for (Trade trade : tradeList.data) {
                    System.out.println(objectMapper.writeValueAsString(trade));
                }
            }
        }
        scanner.close();
    }

    public static class TradeList {
        public List<Trade> data;
    }

    private static void doXXRequest() {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.aastocks.com/tc/stocks/analysis/blocktrade.aspx?symbol=09923")
                    .get();
            Elements elements = doc.getElementsByClass("highcharts-foc");

            System.out.println(elements.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title = doc.title();
        System.out.println(title);
    }

    private static void doXXCrawler() {
        Executor executor = (datum, next) -> {

            HtmlUnitDriver driver = new HtmlUnitDriver();
            driver.setJavascriptEnabled(true);

            driver.get(datum.url());

            List<WebElement> elementList = driver.findElementsByCssSelector(".highcharts-foc");
            for(WebElement element:elementList){
                System.out.println("title:"+element.getText());
            }
        };

        //创建一个基于伯克利DB的DBManager
        DBManager manager = new RocksDBManager("crawl");
        //创建一个Crawler需要有DBManager和Executor
        Crawler crawler = new Crawler(manager, executor);
        crawler.addSeed("http://www.aastocks.com/tc/stocks/analysis/blocktrade.aspx?symbol=09923");
        try {
            crawler.start(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
