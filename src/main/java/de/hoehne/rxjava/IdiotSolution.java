package de.hoehne.rxjava;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import de.hoehne.rxjava.SampleProducServer.Product;
import de.hoehne.rxjava.SampleProducServer.Recommendation;

/**
 * Call all products, recommendations and prices in sequence is a idiots
 * solution
 * 
 * @author johannes
 *
 */
public class IdiotSolution {

	public static void main(String[] args) {
		try {
			SampleProducServer.runServer();

			final Client client = ClientBuilder.newClient();
			final WebTarget target = client.target("http://localhost:8080");

			final List<Product> response = target.path("products").request()
					.get(new GenericType<List<SampleProducServer.Product>>() {
					});

			for (Product product : response) {

				Recommendation recommendation = target.path("recommendation").path(product.getId().toString()).request()
						.get(new GenericType<SampleProducServer.Recommendation>() {
						});

				System.out.printf("Product %d with recommendation %d\n", product.getId(),
						recommendation.getRecommendation());

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

}
