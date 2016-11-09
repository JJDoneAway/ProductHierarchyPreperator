package de.hoehne.rxjava;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
	private static Recommendation[] recomondations = new Recommendation[10];
	private static Price[] prices = new Price[10];

	static {
		for (int i = 0; i < products.length; i++) {
			products[i] = new Product();
			recomondations[i] = new Recommendation(products[i].getId());
			prices[i] = new Price(products[i].getId());

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
		for (int i = 0; i < recomondations.length; i++) {
			Recommendation recomondation = recomondations[i];
			if (recomondation.getProductId().equals(productId)) {
				return recomondation;
			}
		}

		System.out.println("Throw 404");
		throw new WebApplicationException("Product " + productId + " is not known.", 404);
	}

	@GET
	@Path("price/{id}")
	public Price getPrice(@PathParam("id") Integer productId) {
		sleep();
		for (int i = 0; i < prices.length; i++) {
			Price price = prices[i];
			if (price.getProductId().equals(productId)) {
				return price;
			}
		}

		System.out.println("Throw 404");
		throw new WebApplicationException("Product " + productId + " is not known.", 404);
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

	}

	@XmlRootElement
	public static class Recommendation {
		private Integer productId;
		private Integer recommendation = new Random().nextInt(8) + 1;

		public Recommendation() {
		}

		public Recommendation(Integer productId) {
			this.productId = productId;
		}

		@XmlElement
		public Integer getProductId() {
			return productId;
		}

		@XmlElement
		public Integer getRecommendation() {
			return recommendation;
		}
	}

	@XmlRootElement
	public static class Price {
		private Integer productId;
		private BigDecimal price = BigDecimal.valueOf(new Random().nextDouble() * 100 + 1);

		public Price() {
		}

		public Price(Integer productId) {
			this.productId = productId;
		}

		@XmlElement
		public Integer getProductId() {
			return productId;
		}

		@XmlElement
		public BigDecimal getPrice() {
			return price;
		}
	}

}
