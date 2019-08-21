import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

	enum Direction {
		North, South, East, West
	}

	public static void main(String args[]) throws IOException {
		int max = 0; // matrix sorainak v. oszlopainak szama
		int iIdx = 0;
		int jIdx = 0;
		int remainingThings = 0;
		ArrayList<Integer> path = new ArrayList<Integer>();

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		// init
		String s = in.readLine();
		String[] parts = s.split("\\s+");
		max = parts.length;

		int[][] maze = new int[max][max];
		int[][] hasReached = new int[max][max];

		for (int i = 0; i < max; i++) {
			for (int j = 0; j < max; j++) {
				hasReached[i][j] = 0;
			}
		}

		for (int i = 0; i < max; i++)
			maze[0][i] = Integer.parseInt(parts[i]);

		// ciklus
		for (int i = 1; i < max; i++) {
			s = in.readLine();
			parts = s.split("\\s+");
			for (int k = 0; k < max; k++)
				maze[i][k] = Integer.parseInt(parts[k]);
		}

		remainingThings = Integer.parseInt(in.readLine());

		iIdx = 0;
		jIdx = 0;

		boolean east = true;
		boolean south = true;
		boolean north = true;
		boolean west = true;
		int actual = 0;

		path.add(0);
		path.add(0);

		// labirintus mezobol ut meghatarozasa

		while (true) {

			// targy felvetele
			if (maze[iIdx][jIdx] >= 16) {
				System.out.println("felvesz");
				maze[iIdx][jIdx] -= 16;
				remainingThings -= 1;
			}

			actual = maze[iIdx][jIdx];

			// ne menjen ki a labirintusbol
			if (jIdx - 1 < 0)
				west = false;

			if (iIdx + 1 == max)
				south = false;

			if (jIdx + 1 == max)
				east = false;

			if (iIdx - 1 < 0)
				north = false;

			// tiltások
			if (actual >= 8) {
				west = false;
				actual -= 8;
			}

			if (actual >= 4) {
				south = false;
				actual -= 4;
			}

			if (actual >= 2) {
				east = false;
				actual -= 2;
			}

			if (actual == 1) {
				north = false;
				actual -= 1;
			}

			Direction nextDir = null;

			if (remainingThings == 0 && iIdx == max - 1 && jIdx == max - 1) {
				break;

			} else {

				// ha valamelyik szomszedban van targy

				if (iIdx > 0 && maze[iIdx - 1][jIdx] > 16) {
					if (north)
						nextDir = Direction.North;
				} else {

					if (jIdx > 0 && maze[iIdx][jIdx - 1] > 16) {
						if (west)
							nextDir = Direction.West;
					} else {

						if (iIdx < max - 1 && maze[iIdx + 1][jIdx] > 16) {
							if (south)
								nextDir = Direction.South;
						} else {

							if (jIdx < max - 1 && maze[iIdx][jIdx + 1] > 16) {
								if (east)
									nextDir = Direction.East;
							}
						}
					}
				}

				// ha paros sokszor volt itt

				if (!((iIdx == 0) && (jIdx == 0))) {
					if (iIdx > 0) {
						if (hasReached[iIdx - 1][jIdx] != 0) {
							north = false;
				//			hasReached[iIdx - 1][jIdx]--;
						}
					}

					if (jIdx > 0) {
						if (hasReached[iIdx][jIdx - 1] != 0) {
							west = false;
				//			hasReached[iIdx][jIdx - 1]--;
						}
					}

					if (iIdx < max - 1) {
						if (hasReached[iIdx + 1][jIdx] != 0) {
							south = false;
					//		hasReached[iIdx + 1][jIdx]--;

						}
					}

					if (jIdx < max - 1) {
						if (hasReached[iIdx][jIdx + 1] != 0) {
							east = false;
					//		hasReached[iIdx][jIdx + 1]--;
						}
					}

				}

				if (nextDir == null) {
					if (south)
						nextDir = Direction.South;
					else {
						if (east)
							nextDir = Direction.East;
						else {
							if (west)
								nextDir = Direction.West;
							else
								nextDir = Direction.North;
						}
					}
				}
			}

			switch (nextDir)

			{
			case South:
				iIdx++;
				break;
			case West:
				jIdx--;
				break;
			case North:
				iIdx--;
				break;
			case East:
				jIdx++;
				break;
			default:
				break;
			}

			path.add(iIdx, jIdx);

			hasReached[iIdx][jIdx]++;
			System.out.println(iIdx + " " + jIdx);

			north = true;
			east = true;
			west = true;
			south = true;
		}
	}
}
