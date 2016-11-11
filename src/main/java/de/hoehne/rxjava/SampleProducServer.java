package de.hoehne.rxjava;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.sun.net.httpserver.HttpServer;

/**
 * Sample server getting you a sample set of products with some sample
 * descriptions and sample prices;
 * 
 * @author johannes
 *
 */
@SuppressWarnings("restriction")
@Path("/")
public class SampleProducServer {

	private static Product[] products = new Product[10];

	static {
		for (int i = 0; i < products.length; i++) {
			products[i] = new Product();
		}
	}

	public static void main(String[] args) {
		System.out.println("Start");
		runServer();
		System.out.println("Stop");

	}

	public static HttpServer runServer() {
		URI baseUri = UriBuilder.fromUri("http://localhost/").port(8080).build();
		ResourceConfig config = new ResourceConfig(SampleProducServer.class);
		return JdkHttpServerFactory.createHttpServer(baseUri, config);
	}

	@GET
	@Path("products")
	public Product[] getProducts() {
		sleep();
		return products;
	}

	@GET
	@Path("recommendations/{id}")
	public Recommendation getRecommendation(@PathParam("id") Integer productId) {
		sleep();
		return new Recommendation(productId);
	}

	@GET
	@Path("price/{id}")
	public Price getPrice(@PathParam("id") Integer productId) {
		sleep();
		final Price price = new Price(productId);
		return price;
	}

	private void sleep() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@XmlRootElement
	public static class Product {
		private Integer id = new Random().nextInt(9999);

		@XmlElement
		public Integer getId() {
			return id;
		}

		@Override
		public String toString() {
			return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", id).toString();
		}

	}

	@XmlRootElement
	public static class Recommendation {
		@XmlElement
		private Integer productId;
		@XmlElement
		private Integer recommendation;

		public Recommendation() {
		}

		public Recommendation(Integer productId) {
			this.productId = productId;
			this.recommendation = new Random().nextInt(8) + 1;
		}

		public Integer getProductId() {
			return productId;
		}

		public Integer getRecommendation() {
			return recommendation;
		}
	}

	@XmlRootElement
	public static class Price {
		@XmlElement
		private Integer productId;
		@XmlElement
		private BigDecimal price;

		public Price() {
		}

		public Price(Integer productId) {
			this.productId = productId;
			this.price = BigDecimal.valueOf(new Random().nextDouble() * 100 + 1);
		}

		public Integer getProductId() {
			return productId;
		}

		public BigDecimal getPrice() {
			return price;
		}
	}

}
