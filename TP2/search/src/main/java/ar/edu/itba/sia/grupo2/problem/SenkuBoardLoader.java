package ar.edu.itba.sia.grupo2.problem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SenkuBoardLoader {
	private static final Map<Character, SenkuContent> symbolMap;
	static {
		Map<Character, SenkuContent> aMap = new HashMap<>();
		aMap.put('X', SenkuContent.INVALID);
		aMap.put('O', SenkuContent.PEG);
		aMap.put('_', SenkuContent.EMPTY);
		symbolMap = Collections.unmodifiableMap(aMap);
	}

	private static SenkuContent[][] parse(final String filePath) {
		SenkuContent[][] board = new SenkuContent[0][0];
		String line;
		int row = 0;

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			while((line = br.readLine()) != null) {

				if (row == 0)
					// Squared boards
					board = new SenkuContent[line.length()][line.length()];

				char[] chars = line.toCharArray();
				for (int col = 0; col < board.length; col++) {
					if (!symbolMap.containsKey(chars[col]))
						throw new IllegalArgumentException("Invalid character in board file");

					board[row][col] = symbolMap.get(chars[col]);
				}
				row++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return board;
	}

	public static SenkuBoard load(final String boardPath) {
		return new SenkuBoard(parse(boardPath));
	}

	public static void main (String[] args) {
		SenkuBoard b = load("boards/board4.txt");
		System.out.println(b);
		System.out.println(Arrays.toString(b.getBoundaries()));
		System.out.println(b.getDimension());
		System.out.println(b.getCellCount());
		System.out.println(b.getEmptyCount());
		System.out.println(b.getPegCount());
		System.out.println(b.getTarget());
		for (int i = 0; i < b.getDimension(); i++) {
			for (int j = 0; j < b.getDimension(); j++) {
				System.out.println(b.getContent(i, j));
			}
		}
	}
}
