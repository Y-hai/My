package ·É»ú´óÕ½;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class Score {
	public static void outPut(int sc) throws IOException {
		String score = String.valueOf(sc);
		FileOutputStream out = new FileOutputStream("score.txt", true);
		score = score + "\n";
		out.write(score.getBytes());
		out.close();
	}

	public static int inPut() throws IOException {
		FileReader reader = new FileReader("score.txt");
		BufferedReader score = new BufferedReader(reader);
		int a = 0;
		String b = null;
		while ((b = score.readLine()) != null) {
			if (Integer.valueOf(b) > a) {
				a = Integer.valueOf(b);
			}
		}
		reader.close();
		return a;
	}
}
