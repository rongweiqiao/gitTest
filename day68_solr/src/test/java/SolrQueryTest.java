import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class SolrQueryTest {

    @Test
    public void test1(){
        System.out.println("我在改代码");
        System.out.println("我在敲代码");
    }
    @Test
    public void test() throws Exception {

        String url="http://localhost:8080/solr/item";
        SolrServer solrServer = new HttpSolrServer(url);
        SolrQuery query = new SolrQuery();
        //query.set("q","*:*");
        //query.setQuery("product_name:花儿");
        query.setQuery("家天下");
        query.set("df","product_keywords");
        query.addFilterQuery("product_catalog_name:时尚卫浴");
        query.addFilterQuery("product_price:[0 TO 20]");
        query.setSort("product_price", SolrQuery.ORDER.desc);
        query.setStart(20);
        query.setRows(20);

        query.setHighlight(true);
        query.addHighlightField("product_name");
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");

        QueryResponse response = solrServer.query(query);
        SolrDocumentList results = response.getResults();
        System.out.println("总记录数:"+results.getNumFound());
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        for (SolrDocument document : results) {
            String id = (String) document.getFieldValue("id");
            System.out.println("id:"+id);
            String productName = (String) document.getFieldValue("product_name");
            System.out.println("productName:"+productName);
            String productCatalogName = (String) document.getFieldValue("product_catalog_name");
            System.out.println("productCatalogName:"+productCatalogName);
            String productDescription = (String) document.getFieldValue("product_description");
            System.out.println("productDescription:"+productDescription);
            float productPrice = (float) document.getFieldValue("product_price");
            System.out.println("productPrice:"+productPrice);

            System.out.println("------------------------------");
            Map<String, List<String>> map = highlighting.get(id);
            List<String> list = map.get("product_name");
            if(list!=null&&list.size()>0){
                String name = list.get(0);
                System.out.println(name);
            }

        }


    }
}
