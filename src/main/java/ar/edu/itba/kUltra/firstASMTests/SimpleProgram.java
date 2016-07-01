package ar.edu.itba.kUltra.firstASMTests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SimpleProgram {

	private static int fun1(int i1, int i2, int i3) {
		int r1 = i1 + i2 - i3 / i2;
		System.out.println(r1);
		i2 = i3 * r1;
		System.out.println("fun1" + i2);

		return i2;
	}

	private static String println(final String s) {
		System.out.println(s);
		return s;
	}

	private static String gets() {
		final List<Integer> l = new ArrayList<>();
		l.add(1);
		int i = l.get(0);

		try {
			BufferedReader br =
					new BufferedReader(new InputStreamReader(System.in));

			return br.readLine();

		} catch (IOException io) {
			return null;
		}
	}

	public static void main(String[] args) {
		String greet = "hello";

		int i = 0;


		while (i < 5) {
			println(greet);
			i = i + 1;
		}


		fun1(5, 7, 8);

		greet = "Hola";

		println(greet);
	}

}
