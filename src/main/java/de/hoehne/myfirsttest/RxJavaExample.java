package de.hoehne.myfirsttest;

import static java.lang.System.err;
import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import de.hoehne.myfirsttest.JavaStreamExamples.CounterMap;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

public class RxJavaExample {

	public static void main(String[] args) throws Exception{
		simpleStream();
		countingWords();
	}

	private static void simpleStream() {
		Observable.//
				from(Arrays.asList("Hello", "World", "Test")).//
				filter(word -> word.contains("l")).//
				map(word -> word.toUpperCase() + " ").//
				reduce(new StringBuilder(), StringBuilder::append).//
				subscribe(out::print, e -> {
				}, () -> out.println("!"));
	}

	public static void countingWords() throws Exception {
		Observable.//
		create(new LineReader(new FileInputStream("./src/main/resources/text.txt"))).
		flatMap(line -> Observable.from(line.split(" "))).//
		filter(word -> !word.trim().equals("")).//
		filter(word -> word.matches("\\w+")).//
		filter(word -> word.matches("\\D+")).//
		map(String::toLowerCase).//
		reduce(new CounterMap(), CounterMap::put).//
		flatMap(sortedMap -> Observable.from(sortedMap.container.entrySet())).//
		subscribe(value -> out.printf("Word %25s %5d times%n", value.getKey(), value.getValue()), err::println);

	}

	private static class LineReader implements OnSubscribe<String> {

		private final BufferedReader reader;

		LineReader(InputStream is) {
			this.reader = new BufferedReader(new InputStreamReader(is));
		}

		@Override
		public void call(Subscriber<? super String> t) {

			try {
				String nextLine;

				while (!t.isUnsubscribed() && (nextLine = reader.readLine()) != null) {
					t.onNext(nextLine);
				}

				if (!t.isUnsubscribed()) {
					t.onCompleted();
				}
			} catch (Exception e) {
				t.onError(e);
			}

		}

	}
	
	public static class CounterMap {
		private Map<String, Integer> container = new HashMap<String, Integer>();

		public CounterMap put(String word) {
			container.putIfAbsent(word, 0);
			container.put(word, container.get(word) + 1);
			return this;
		}

		public CounterMap put(String word, int add) {
			container.putIfAbsent(word, 0);
			container.put(word, container.get(word) + add);
			return this;
		}

		public Set<Entry<String, Integer>> entrySet() {
			return container.entrySet();
		}

		public void putAll(CounterMap map) {
			for (Entry<String, Integer> element : map.container.entrySet()) {
				put(element.getKey(), element.getValue());
			}
		}
	}

}
