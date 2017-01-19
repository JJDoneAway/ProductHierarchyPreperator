package de.hoehne.streams;

import static java.lang.System.out;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class JavaStreamExamples {

	public static void main(String[] args) throws Exception {
		// sum_short_nice();
		countingWords();

		// try_peek();
	}

	/**
	 * using the reduce function very verbose
	 */
	public static void sum_verbose() {

		final BigInteger reduce = Stream.of("1", "2", "3", "4").reduce(BigInteger.ZERO,
				new BiFunction<BigInteger, String, BigInteger>() {

					@Override
					public BigInteger apply(BigInteger collectorObject, String wertAusStream) {
						out.println("collectorObject = " + System.identityHashCode(collectorObject) + " / "
								+ collectorObject);

						final BigInteger t = collectorObject.add(new BigInteger(wertAusStream));
						out.println("t = " + System.identityHashCode(t));
						return t;
					}
				}, new BinaryOperator<BigInteger>() {

					@Override
					public BigInteger apply(BigInteger t, BigInteger u) {
						out.println("t = " + System.identityHashCode(t));
						out.println("u = " + System.identityHashCode(u));

						final BigInteger ret = t.add(u);
						out.println("ret = " + System.identityHashCode(ret));

						return ret;
					}
				});

		out.println(reduce);
	}

	/**
	 * using reduce with lambdas verbose
	 */
	public static void sum_short() {
		final BigInteger reduce = Stream.of("1", "2", "3", "4").//
				parallel().//
				reduce(BigInteger.ZERO,
						(lumpenSammler, wertAusStream) -> lumpenSammler.add(new BigInteger(wertAusStream)),
						(collector_a, collector_b) -> collector_a.add(collector_b));
		out.println(reduce);
	}

	/**
	 * using reduce as short as possible
	 */
	public static void sum_short_nice() {

		final BigInteger reduce = Stream.of("1", "2", "3", "4")//
				.parallel()//
				.map(BigInteger::new)//
				.reduce(BigInteger.ZERO, BigInteger::add, BigInteger::add);

		out.println(reduce);
	}

	public static void try_peek() {

		final BigInteger reduce = Stream.of("1", "2", "3", "4")//
				.parallel()//
				.peek(out::println).map(BigInteger::new)//
				.reduce(BigInteger.ZERO, BigInteger::add, BigInteger::add);

		out.println(reduce);
	}

	/**
	 * As we want to count words we use collect but not reduce
	 * 
	 * http://www.nosid.org/java8-stream-reduce-vs-collect.html
	 * 
	 * @throws Exception
	 */
	public static void countingWords() throws Exception {

		final CounterMap result = Files.lines(Paths.get("./src/main/resources/text.txt"))//
				.parallel()//
				.flatMap(Pattern.compile("[^\\p{L}]")::splitAsStream)//
				.filter(word -> !word.trim().equals(""))//
				.filter(word -> word.matches("\\w+"))//
				.filter(word -> word.matches("\\D+"))//
				.map(String::toLowerCase)//
				.collect(CounterMap::new, CounterMap::put, CounterMap::putAll);

		out.println("Whole list of words sorted alphabetically");
		out.println("-----------------------------------------");
		result.entrySet().stream().sorted((i, j) -> i.getKey().compareTo(j.getKey()))
				.forEachOrdered(value -> out.printf("Word %25s %5d times%n", value.getKey(), value.getValue()));
		out.println("-----------------------------------------");
		out.println("Whole list of words sorted by occurrence");
		out.println("-----------------------------------------");
		result.entrySet().stream().sorted((i, j) -> i.getValue().compareTo(j.getValue()))
				.forEachOrdered(value -> out.printf("Word %25s %5d times%n", value.getKey(), value.getValue()));

	}

	public static class CounterMap {
		private Map<String, Integer> container = new HashMap<String, Integer>();

		public void put(String word) {
			container.putIfAbsent(word, 0);
			container.put(word, container.get(word) + 1);
		}

		public void put(String word, int add) {
			container.putIfAbsent(word, 0);
			container.put(word, container.get(word) + add);
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
