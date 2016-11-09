package de.hoehne.rxjava;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.rx.rxjava.RxObservable;

import rx.Observable;

public class AsyncRestClients {

	public static void main(String[] args) throws Exception {
		getNews();
	}

	public static void getNews() throws Exception {

		final Observable<String> reply = createRestCallObservabal(ClientBuilder.newClient(), String.class, "http://catdir.loc.gov/catdir/enhancements/fy0711/2006051179-s.html");

		reply.//
		onErrorReturn(error -> "War halt Scheiße: " + error.toString()).//
		flatMap(text -> Observable.from(text.split("<div>"))).//
		map(text -> text.replace("</div>", "\n")).//
		forEach(System.out::println);

	}

	public static <T> Observable<T> createRestCallObservabal(Client client, Class<T> clazz, String uri) {
		return RxObservable.from(client).target(uri).request().rx().get(clazz);
	}
}
