package cn.itheima.test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {

	// 添加(更新)索引
	@Test
	public void testAddOrUpdate() throws SolrServerException, IOException {
		// 建立httpSolrServer对象,用于连接solr服务
		// 参数baseURL:指定solr服务地址
		HttpSolrServer server = new HttpSolrServer("http://127.0.0.1:8082/solr/");

		// 建立文档对象
		SolrInputDocument document = new SolrInputDocument();
		// id域
		document.addField("id", "9527");
		document.addField("name", "solrj is a good thing and springmvc");

		// 使用httpsolrserver对象,执行添加
		server.add(document);

		// 提交
		server.commit();
	}

	// 根据id删除索引
	@Test
	public void deleteIndexById() throws SolrServerException, IOException {
		// 建立httpSolrServer对象,用于连接solr服务
		// 参数baseURL:指定solr服务地址
		HttpSolrServer server = new HttpSolrServer("http://127.0.0.1:8082/solr/");

		// 执行删除
		server.deleteById("9527");

		// 提交
		server.commit();

	}

	// 根据条件删除索引
	@Test
	public void deleteIndexByQuery() throws SolrServerException, IOException {
		// 建立httpSolrServer对象,用于连接solr服务
		// 参数baseURL:指定solr服务地址
		HttpSolrServer server = new HttpSolrServer("http://127.0.0.1:8082/solr/");

		// 执行删除
		server.deleteByQuery("name:solrj");

		// 提交
		server.commit();

	}

	// 查询索引
	@Test
	public void testQuery() throws SolrServerException, IOException {
		// 建立httpSolrServer对象,用于连接solr服务
		// 参数baseURL:指定solr服务地址
		HttpSolrServer server = new HttpSolrServer("http://127.0.0.1:8082/solr/");

		// 建立查询对象
		SolrQuery query = new SolrQuery("*:*");

		// 执行搜索
		QueryResponse response = server.query(query);

		// 获取查询结果集
		SolrDocumentList results = response.getResults();

		// 处理结果集
		System.out.println("结果数量:" + results.getNumFound());
		for (SolrDocument doc : results) {
			System.out.println("-------------------");
			System.out.println("id域" + doc.get("id"));
			System.out.println("name域" + doc.get("name"));
		}

		// 提交
		server.commit();

	}
	
	// 指定solrcore访问
		@Test
		public void testQuery2() throws SolrServerException, IOException {
			// 建立httpSolrServer对象,用于连接solr服务
			// 参数baseURL:指定solr服务地址
			HttpSolrServer server = new HttpSolrServer("http://127.0.0.1:8082/solr/collection2");


			// 建立查询对象
			SolrQuery query = new SolrQuery("*:*");

			// 执行搜索
			QueryResponse response = server.query(query);

			// 获取查询结果集
			SolrDocumentList results = response.getResults();

			// 处理结果集
			System.out.println("结果数量:" + results.getNumFound());
			for (SolrDocument doc : results) {
				System.out.println("-------------------");
				System.out.println("id域" + doc.get("id"));
				System.out.println("name域" + doc.get("name"));
			}


			// 提交
			server.commit();

		}
		
		// 第三天课程内容，solrj高级查询
		@Test
		public void testSeniorQuery() throws SolrServerException{
			//建立HttpSolrServer服务对象,连接solr服务
			HttpSolrServer server=new HttpSolrServer("http://127.0.0.1:8082/solr/collection1");
			
			//建立查询对象
			SolrQuery query=new SolrQuery();
			
			//设置查询表达式q
			query.setQuery("花儿朵朵");
			
			//设置过滤查询条件fq
			query.setFilterQueries("product_price:[* TO 20]");
			
			//设置排序sort
			query.setSort("product_price", ORDER.asc);
			
			//设置分页
			query.setStart(0);
			query.setRows(10);
			
			//设置显示的域列表fl
			query.setFields("id","product_name","product_price","product_catalog_name");
			
			//设置默认搜索域df
			query.set("df", "product_name");
			
			//设置数据响应格式
			query.set("wt", "json");
			
			//设置高亮显示
			query.setHighlight(true);
			query.addHighlightField("product_name");
			query.setHighlightSimplePre("<font color='red'>");
			query.setHighlightSimplePost("</font>");
			
			//设置分片统计
			query.setFacet(true);
			query.addFacetField("product_catalog_name");
			
			
			
			//执行搜索
			QueryResponse response = server.query(query);
			
			//获取查询结果集数据
			SolrDocumentList results = response.getResults();
			
			//获取高亮显示数据
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			
			//获取分片统计数据
			List<FacetField> facetFields = response.getFacetFields();
			// 查看分片统计数据===================================start
			System.out.println("分片统计开始=======================");
			for (FacetField ff : facetFields) {
				System.out.println("分片统计的域:"+ff.getName()+",有多少个组:"+ff.getValueCount());
				
				//组内信息
				List<Count> list = ff.getValues();
				for (Count c : list) {
					System.out.println("当前组名称:"+c.getName()+",数量:"+c.getCount());
				}
			}
			System.out.println("分片统计结束=======================");
			// 查看分片统计数据===================================end
			
			//处理结果集
			//打印实际查询到的数据
			System.out.println("实际查询到的数据:"+results.getNumFound());
			
			for (SolrDocument doc : results) {
				System.out.println("--------------------------");
				//获取商品id
				String pid = doc.get("id").toString();
				
				//获取商品名称
				String pname="";
				List<String> list = highlighting.get(pid).get("product_name");
				if (list!=null&&list.size()>0) {
					pname=list.get(0);
				}else{
					pname=doc.get("product_name").toString();
				}
				
				//获取商品价格
				String pprice = doc.get("product_price").toString();
				
				//获取商品分类名称
				String pcname = doc.get("product_catalog_name").toString();
				
				System.out.println("商品id："+pid);
				System.out.println("商品名称："+pname);
				System.out.println("商品价格："+pprice);
				System.out.println("商品分类名称："+pcname);
			}
		}
}
