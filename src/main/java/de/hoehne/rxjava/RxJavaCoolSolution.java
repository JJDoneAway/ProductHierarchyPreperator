package de.hoehne.rxjava;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.rx.rxjava.RxObservable;

import de.hoehne.rxjava.SampleProducServer.Price;
import de.hoehne.rxjava.SampleProducServer.Product;
import de.hoehne.rxjava.SampleProducServer.Recommendation;
import rx.Observable;

public class RxJavaCoolSolution {
	private final static Client client = ClientBuilder.newClient();

	public static void main(String[] args) throws Exception {

		SampleProducServer.runServer();
		final long start = System.currentTimeMillis();
		try {

			final Observable<Product[]> reply = getProducts();

			reply.//
					flatMap(Observable::from).//
					flatMap(product -> //
					Observable.zip(//
							getRecommendations(product), //
							getPrices(product), //
							RxJavaCoolSolution::processResult//
					)//
					).//
					toSortedList().//
					toBlocking().//
					single().//
					forEach(System.out::println);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			long millis = System.currentTimeMillis() - start;
			DateFormat fmt = new SimpleDateFormat(":mm:ss.SSS");
			fmt.setTimeZone(TimeZone.getTimeZone("UTC"));
			System.out.println("It took " + (millis / 3600000/* hours */) + fmt.format(new Date(millis)));
			System.exit(0);
		}

	}

	private static String processResult(Recommendation recommendation, Price price) {
		return String.format("Product %-4d with recommendation %d for %.2f€", price.getProductId(),
				recommendation.getRecommendation(), price.getPrice());
	}

	private static Observable<Price> getPrices(Product product) {
		return createRestCallObservabal(client, Price.class, "http://localhost:8080/price/" + product.getId()).//
				onErrorReturn(error -> {
					error.printStackTrace();
					return new Price(product.getId());
				});
	}

	private static Observable<Recommendation> getRecommendations(Product product) {
		return createRestCallObservabal(client, Recommendation.class,
				"http://localhost:8080/recommendations/" + product.getId()).//
						onErrorReturn(error -> {
							error.printStackTrace();
							return new Recommendation(product.getId());
						});
	}

	private static Observable<Product[]> getProducts() {
		return createRestCallObservabal(client, Product[].class, "http://localhost:8080/products")
				.onErrorReturn(error -> {
					error.printStackTrace();
					return new Product[] {};
				});
	}

	/**
	 * Get the GET
	 * 
	 * @param client
	 *            the http client
	 * @param clazz
	 *            the return type
	 * @param uri
	 *            the REST URL to the GET endpoint
	 * 
	 * @return the asynchronous call back
	 */
	public static <T> Observable<T> createRestCallObservabal(Client client, Class<T> clazz, String uri) {
		return RxObservable.from(client).target(uri).request().rx().get(clazz);
	}
}
