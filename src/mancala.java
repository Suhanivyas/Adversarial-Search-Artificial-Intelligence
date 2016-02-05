//package mancala;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
class boardState {
	String nodeName = "";
	int play = -1;
	int len = 2 * mancala.numOfPits + 2;
	int[] stateP = new int[len];
	boardState[] children = new boardState[mancala.numOfPits];
	boolean isEmpty = false;
	int currPlayer = -1;
	int depth = 0;
	boolean IsExtraMoveState = false;
	int eVal = 0;
	void makeState(String[] p1, int mp1, String[] p2, int mp2) {
		int j = 0;
		for (int i = 0; i < p1.length; i++) {
			this.stateP[j++] = Integer.valueOf(p1[i]);
		}
		this.stateP[j++] = mp1;
		for (int i = p2.length - 1; i >= 0; i--) {
			this.stateP[j++] = Integer.valueOf(p2[i]);
		}
		this.stateP[j++] = mp2;


	}
	String printState(int alpha, int beta) throws IOException {
		String res = "";
		try {

			if (mancala.algo == 2) {
				//System.out.println(nodeName + "," + depth + "," + eVal);
				if (eVal == Integer.MIN_VALUE) res = nodeName + "," + depth + "," + "-Infinity";
				else if (eVal == Integer.MAX_VALUE) res = nodeName + "," + depth + "," + "Infinity";
				else res = nodeName + "," + depth + "," + eVal;
			}
			if (mancala.algo == 3) {
				String al = "";
				String be = "";
				if (alpha == Integer.MIN_VALUE) al = "-Infinity";
				else al = String.valueOf(alpha);
				if (beta == Integer.MAX_VALUE) be = "Infinity";
				else be = String.valueOf(beta);

				//System.out.println(nodeName + "," + depth + "," + eVal + "," + al + "," + be);
				if (eVal == Integer.MIN_VALUE) res = nodeName + "," + depth + "," + "-Infinity" + "," + al + "," + be;
				else if (eVal == Integer.MAX_VALUE) res = nodeName + "," + depth + "," + "Infinity" + "," + al + "," + be;
				else res = nodeName + "," + depth + "," + eVal + "," + al + "," + be;
			}

			mancala.bw.write(res);
			mancala.bw.newLine();


		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;

	}
}
public class mancala {
	public static String trav_log = "";
	public static int algo;
	public static int player;
	public static int cuttingEdge;
	public static int maxValue = Integer.MIN_VALUE;
	public static int numOfPits;
	public static List < String > myList = new ArrayList < String > ();
	public static int len;
	public static int currentPlayer = 0;
	public static boolean gameOver = false;
	public static boardState root;
	public static int depth = 0;
	public static ArrayList < boardState > returnState = new ArrayList < boardState > ();
	public static ArrayList < boardState > returnStateForOther = new ArrayList < boardState > ();
	static BufferedWriter bw = null;
	static BufferedWriter bw2 = null;
	public static void main(String args[]) throws IOException {
		try {
			String fileName = args[1];
			Scanner scanner = new Scanner(new File(fileName));
			//String fileName = "/Users/suhanivyas/Documents/workspace/mancala/src/mancala/input_4-3.txt";
			//Scanner scanner = new Scanner(new File(fileName));
			File file = new File("traverse_log.txt");
			File file2 = new File("next_state.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			if (!file2.exists()) {
				file2.createNewFile();
			}
			FileWriter fw = new FileWriter(file);
			FileWriter fw2 = new FileWriter(file2);
			bw = new BufferedWriter(fw);
			bw2 = new BufferedWriter(fw2);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				Scanner lineScanner = new Scanner(line);
				lineScanner.useDelimiter("\n");
				while (lineScanner.hasNext()) {
					String part = lineScanner.nextLine();
					myList.add(part);
				}
				lineScanner.close();
			}
			scanner.close();

			//Parsing the file
			boardState givenState = new boardState();
			givenState = parseFile();

			//assigning values
			len = numOfPits * 2 + 2;
			root = new boardState();
			root.len = givenState.len;
			int h = 0;
			for (int e: givenState.stateP) {
				root.stateP[h++] = e;
			}
			root.isEmpty = false;
			root.currPlayer = 1;
			root.depth = 0;
			root.IsExtraMoveState = false;
			root.eVal = Integer.MIN_VALUE;
			root.play = player;
			root.nodeName = "root";
			returnState.add(root);
			returnStateForOther.add(root);

			if (algo == 1) {
				//file.delete();
				cuttingEdge = 1;
				//String fir_res="Node,"+"Depth,"+"Value";
				//bw.write(fir_res);
				//bw.newLine();
				root.printState(Integer.MIN_VALUE, Integer.MAX_VALUE);
				int result = minmax(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
				String result_nextState = "";
				for (int w = len - 2; w > numOfPits; w--)
				result_nextState = result_nextState.concat((returnState.get(0).stateP[w] + " "));
				result_nextState = result_nextState.concat("\n");
				for (int w = 0; w < numOfPits; w++)
				result_nextState = result_nextState.concat((returnState.get(0).stateP[w] + " "));
				result_nextState = result_nextState.concat("\n");
				result_nextState = result_nextState.concat((returnState.get(0).stateP[len - 1] + " "));
				result_nextState = result_nextState.concat("\n");
				result_nextState = result_nextState.concat((returnState.get(0).stateP[numOfPits] + " "));
				bw2.write(result_nextState);
				//System.out.print(result_nextState);


				/*System.out.println("This is FINAL result!!" + result);
				System.out.println("Final State : ");

				for (int k: returnState.get(0).stateP) {
					System.out.print(k + " ");
				}
				System.out.println();

				System.out.println("Roots !!");
				for (int i = 0; i < root.stateP.length; i++)
				System.out.print(root.stateP[i] + " ");
				System.out.println();*/

			} else if (algo == 2) {
				String fir_res = "Node," + "Depth," + "Value";
				bw.write(fir_res);
				bw.newLine();
				root.printState(Integer.MIN_VALUE, Integer.MAX_VALUE);
				int result = minmax(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
				String result_nextState = "";
				for (int w = len - 2; w > numOfPits; w--)
				result_nextState = result_nextState.concat((returnState.get(0).stateP[w] + " "));
				result_nextState = result_nextState.concat("\n");
				for (int w = 0; w < numOfPits; w++)
				result_nextState = result_nextState.concat((returnState.get(0).stateP[w] + " "));
				result_nextState = result_nextState.concat("\n");
				result_nextState = result_nextState.concat((returnState.get(0).stateP[len - 1] + " "));
				result_nextState = result_nextState.concat("\n");
				result_nextState = result_nextState.concat((returnState.get(0).stateP[numOfPits] + " "));
				bw2.write(result_nextState);
				//System.out.print(result_nextState);


				/*System.out.println("This is FINAL result!!" + result);
				System.out.println("Final State : ");

				for (int k: returnState.get(0).stateP) {
					System.out.print(k + " ");
				}
				System.out.println();

				System.out.println("Roots !!");
				for (int i = 0; i < root.stateP.length; i++)
				System.out.print(root.stateP[i] + " ");
				System.out.println();*/
			} else if (algo == 3) {
				//System.out.println("Alpha beta algo!!!");
			//	System.out.print("Node," + "Depth," + "Value," + "Alpha," + "Beta");
				//System.out.println();
				String fir_res = "Node," + "Depth," + "Value," + "Alpha," + "Beta";
				bw.write(fir_res);
				bw.newLine();
				root.printState(Integer.MIN_VALUE, Integer.MAX_VALUE);
				int result = minmax(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
				String result_nextState = "";
				for (int w = len - 2; w > numOfPits; w--)
				result_nextState = result_nextState.concat((returnState.get(0).stateP[w] + " "));
				result_nextState = result_nextState.concat("\n");
				for (int w = 0; w < numOfPits; w++)
				result_nextState = result_nextState.concat((returnState.get(0).stateP[w] + " "));
				result_nextState = result_nextState.concat("\n");
				result_nextState = result_nextState.concat((returnState.get(0).stateP[len - 1] + " "));
				result_nextState = result_nextState.concat("\n");
				result_nextState = result_nextState.concat((returnState.get(0).stateP[numOfPits] + " "));
				bw2.write(result_nextState);
				//System.out.print(result_nextState);


				/*System.out.println("This is FINAL result!!" + result);
				System.out.println("Final State : ");

				for (int k: returnState.get(0).stateP) {
					System.out.print(k + " ");
				}
				System.out.println();

				System.out.println("Roots !!");
				for (int i = 0; i < root.stateP.length; i++)
				System.out.print(root.stateP[i] + " ");
				System.out.println();*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) bw.close();


			if (bw2 != null) bw2.close();

		}
	}
	public static boardState parseFile() {
		algo = Integer.valueOf(myList.get(0));
		player = Integer.valueOf(myList.get(1));
		cuttingEdge = Integer.valueOf(myList.get(2));
		String p2State[] = myList.get(3).split("\\s+");
		numOfPits = p2State.length;
		String p1State[] = myList.get(4).split("\\s+");
		boardState givenState = new boardState();
		givenState.makeState(p1State, Integer.valueOf(myList.get(6)), p2State, Integer.valueOf(myList.get(5)));
		return givenState;
	}

	public static int minmax(boardState currentState, int A, int B) throws IOException {
		try {
			if (!currentState.IsExtraMoveState && currentState.depth == cuttingEdge && root.play == 1) {
				return currentState.stateP[numOfPits] - currentState.stateP[(numOfPits * 2) + 1];
			} else if (!currentState.IsExtraMoveState && currentState.depth == cuttingEdge && root.play == 2) {
				return currentState.stateP[(numOfPits * 2) + 1] - currentState.stateP[numOfPits];
			}
			int bestScore = 0;
			if (currentState.play == 2) {
				bestScore = currentState.eVal;
				int q = 0;
				int[] p2Temp = new int[len];
				for (int k = numOfPits + 1; k < len; k++)
				p2Temp[q++] = currentState.stateP[k];
				for (int k = 0; k <= numOfPits; k++)
				p2Temp[q++] = currentState.stateP[k];
				int w = 0;
				for (int y: p2Temp)
				currentState.stateP[w++] = y;
				for (int o = numOfPits - 1; o >= 0; o--) {
					/*if(currentState.alpha>currentState.beta)
						return currentState.eVal;*/
					if (currentState.stateP[o] != 0) {
						if (currentState.currPlayer == 1) {
							int score = createChildren(currentState, o, A, B);
							if (score > bestScore) {
								//System.out.println("Value of max just entered" + maxValue);

								bestScore = score;
								currentState.eVal = bestScore;
								if (algo == 3) {
									if (score >= B) {
										currentState.printState(A, B);
										break;
									}
								}
								A = Math.max(A, score);

								if (currentState == root && currentState.children[o].IsExtraMoveState == false) {
								//	System.out.println("Entered in rootif");

									returnState.set(0, currentState.children[o]);
								} else if (currentState.depth == root.depth + 1 && currentState.children[o].depth == (root.depth + 1) && currentState.IsExtraMoveState == true && currentState.children[o].IsExtraMoveState == false) {
								//	System.out.println("Entered in middleif");
									if (score >= maxValue) {
										//System.out.println("eNtered max value!!!");

										//System.out.println("Max value=" + maxValue);
										returnStateForOther.set(0, currentState.children[o]);

									//	System.out.println("returnStateForOther= ");
										//for (int r: currentState.children[o].stateP)
										//System.out.print(r + " ");
									}


									//System.out.println();
									maxValue = Math.max(maxValue, score);
								} else if (currentState == root && currentState.children[o].IsExtraMoveState == true) {
									//System.out.println("Entered in 2nd root if");

									returnState.set(0, returnStateForOther.get(0));

								}

							}
							boardState tempPrintO = new boardState();
							tempPrintO = copyState(currentState, tempPrintO);
							tempPrintO.printState(A, B);
							//currentState.printState(A,B);
						} else if (currentState.currPlayer == 0) {
							int score = createChildren(currentState, o, A, B);
							if (score < bestScore) {
								//System.out.println("Value of max just entered" + maxValue);
								//System.out.println("Entered in condition");
								bestScore = score;
								currentState.eVal = bestScore;
								if (algo == 3) {
									if (A >= score) {
										currentState.printState(A, B);
										break;
									}
								}
								B = Math.min(score, B);
								if (currentState == root && currentState.children[o].IsExtraMoveState == false) {
								//	System.out.println("Entered in rootif");
									returnState.set(0, currentState.children[o]);
								} else if (currentState.depth == root.depth + 1 && currentState.children[o].depth == (root.depth + 1) && currentState.IsExtraMoveState == true && currentState.children[o].IsExtraMoveState == false) {
								//	System.out.println("Entered in middleif");
									if (score >= maxValue) {
									//	System.out.println("eNtered max value!!!");
									//	System.out.println("Max value=" + maxValue);
										returnStateForOther.set(0, currentState.children[o]);
									}


									//System.out.println("returnStateForOther= ");
									//for (int r: currentState.children[o].stateP)
									//System.out.print(r + " ");
									//System.out.println();
									maxValue = Math.max(maxValue, score);

								} else if (currentState == root && currentState.children[o].IsExtraMoveState == true) {
									System.out.println("Entered in 2nd root if");
									returnState.set(0, returnStateForOther.get(0));


								}


							}
							boardState tempPrint = new boardState();
							tempPrint = copyState(currentState, tempPrint);
							tempPrint.printState(A, B);
						}
					}
				}
				int t = 0;
				int[] p6Temp = new int[len];
				for (int k = numOfPits + 1; k < len; k++)
				p6Temp[t++] = currentState.stateP[k];
				for (int k = 0; k <= numOfPits; k++)
				p6Temp[t++] = currentState.stateP[k];
				int r = 0;
				for (int y: p6Temp)
				currentState.stateP[r++] = y;


			} else if (currentState.play == 1) {
				bestScore = currentState.eVal;
				for (int o = 0; o < numOfPits; o++) {

					if (currentState.stateP[o] != 0) {
						if (currentState.currPlayer == 1) {
							int score = createChildren(currentState, o, A, B);
							if (score > bestScore) {
								//System.out.println("Value of max just entered" + maxValue);

								bestScore = score;
								currentState.eVal = bestScore;
								if (algo == 3) {
									if (score >= B) {
										currentState.printState(A, B);
										break;
									}
								}
								A = Math.max(A, score);

								if (currentState == root && currentState.children[o].IsExtraMoveState == false) {
								//	System.out.println("Entered in rootif");
									returnState.set(0, currentState.children[o]);
								} else if (currentState.depth == root.depth + 1 && currentState.children[o].depth == (root.depth + 1) && currentState.children[o].IsExtraMoveState == false) {
									//System.out.println("Entered in middleif");
									if (score >= maxValue) {
									//	System.out.println("eNtered max value!!!");
									//	System.out.println("Max value=" + maxValue);
										returnStateForOther.set(0, currentState.children[o]);
									}


									//System.out.println("returnStateForOther= ");
								//	for (int r: currentState.children[o].stateP)
									//System.out.print(r + " ");
									//System.out.println();
									maxValue = Math.max(maxValue, score);

								} else if (currentState == root && currentState.children[o].IsExtraMoveState == true) {
								//	System.out.println("Entered in 2nd root if");
									returnState.set(0, returnStateForOther.get(0));


								}

							}
							currentState.printState(A, B);
						} else if (currentState.currPlayer == 0) {
							int score = createChildren(currentState, o, A, B);
							if (score < bestScore) {
								//System.out.println("Value of max just entered" + maxValue);

								bestScore = score;
								currentState.eVal = bestScore;
								if (algo == 3) {
									if (A >= score) {
										currentState.printState(A, B);
										break;
									}
								}
								B = Math.min(score, B);

								if (currentState == root && currentState.children[o].IsExtraMoveState == false) {
								//	System.out.println("Entered in rootif");

									returnState.set(0, currentState.children[o]);
								} else if (currentState.depth == root.depth + 1 && currentState.children[o].depth == (root.depth + 1) && currentState.children[o].IsExtraMoveState == false) {
									//System.out.println("Entered in middleif");

									if (score >= maxValue) {
										//System.out.println("eNtered max value!!!");
										//System.out.println("Max value=" + maxValue);
										returnStateForOther.set(0, currentState.children[o]);
									}


									//System.out.println("returnStateForOther= ");
								//	for (int r: currentState.children[o].stateP)
									//System.out.print(r + " ");
									//System.out.println();
									maxValue = Math.max(maxValue, score);

								} else if (currentState == root && currentState.children[o].IsExtraMoveState == true) {
								//	System.out.println("Entered in 2nd root if");

									returnState.set(0, returnStateForOther.get(0));

								}

							}
							currentState.printState(A, B);
						}
					}
				}
			}
			return bestScore;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 850;

	}

	public static int createChildren(boardState currentState, int i, int A, int B) throws IOException {
		try {
			int ret = 0;
			if (!currentState.IsExtraMoveState && currentState.depth == cuttingEdge && root.play == 1) {
				return currentState.stateP[numOfPits] - currentState.stateP[(numOfPits * 2) + 1];
			} else if (!currentState.IsExtraMoveState && currentState.depth == cuttingEdge && root.play == 2) {
				return currentState.stateP[(numOfPits * 2) + 1] - currentState.stateP[numOfPits];
			} else {
				boardState child = new boardState();
				if (currentState.play == 1) {
					child = makeChild(currentState, i, currentState.depth);
					child.nodeName = conversion(i);
					currentState.children[i] = child;
					if (!child.isEmpty) {
						if (child.depth != cuttingEdge && currentState.currPlayer == 1) {
							if (currentState.currPlayer != child.currPlayer) child.eVal = Integer.MAX_VALUE;
							else child.eVal = Integer.MIN_VALUE;
						} else if (child.depth != cuttingEdge && currentState.currPlayer == 0) {
							if (currentState.currPlayer != child.currPlayer) child.eVal = Integer.MIN_VALUE;
							else child.eVal = Integer.MAX_VALUE;
						} else if (child.depth == cuttingEdge && child.IsExtraMoveState == true && currentState.currPlayer == 1) {
							if (currentState.currPlayer != child.currPlayer) child.eVal = Integer.MAX_VALUE;
							else child.eVal = Integer.MIN_VALUE;
						} else if (child.depth == cuttingEdge && child.IsExtraMoveState == true && currentState.currPlayer == 0) {
							if (currentState.currPlayer != child.currPlayer) child.eVal = Integer.MIN_VALUE;
							else child.eVal = Integer.MAX_VALUE;
						}
					}
					child.printState(A, B);


					ret = minmax(child, A, B);
				} else if (currentState.play == 2) {
					//Copying for Player2
					child = makeChild(currentState, i, currentState.depth);
					//Recopying for Player2
					int[] p2RetTemp = new int[len];
					int j = 0;
					for (int o = numOfPits + 1; o < len; o++)
					p2RetTemp[j++] = child.stateP[o];
					for (int o = 0; o <= numOfPits; o++)
					p2RetTemp[j++] = child.stateP[o];
					int t = 0;
					for (int y: p2RetTemp)
					child.stateP[t++] = y;
					child.nodeName = "A" + (1 + (numOfPits - i));
					if (!child.isEmpty) {
						if (child.depth != cuttingEdge && currentState.currPlayer == 1) {
							if (currentState.currPlayer != child.currPlayer) child.eVal = Integer.MAX_VALUE;
							else child.eVal = Integer.MIN_VALUE;
						} else if (child.depth != cuttingEdge && currentState.currPlayer == 0) {
							if (currentState.currPlayer != child.currPlayer) child.eVal = Integer.MIN_VALUE;
							else child.eVal = Integer.MAX_VALUE;
						} else if (child.depth == cuttingEdge && child.IsExtraMoveState == true && currentState.currPlayer == 1) {
							if (currentState.currPlayer != child.currPlayer) child.eVal = Integer.MAX_VALUE;
							else child.eVal = Integer.MIN_VALUE;

						} else if (child.depth == cuttingEdge && child.IsExtraMoveState == true && currentState.currPlayer == 0) {
							if (currentState.currPlayer != child.currPlayer) child.eVal = Integer.MIN_VALUE;
							else child.eVal = Integer.MAX_VALUE;
						}
					}
					child.printState(A, B);
					currentState.children[i] = child;

					if (!child.isEmpty) {
						ret = minmax(child, A, B);
					} else if (child.isEmpty) {
						ret = child.eVal;

					}
				}
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 850;

	}
	public static boardState makeChild(boardState currentState, int i, int d) {
		boardState child = new boardState();
		int[] temp1 = new int[len];
		int f = 0;
		for (int q: currentState.stateP)
		temp1[f++] = q;
		if (temp1[i] != 0) {
			int count = temp1[i];
			temp1[i] = 0;
			while (count > 0) {
				if (i + 1 == numOfPits && count == 1) {
					temp1[i + 1] += 1;
					count--;
					i++;
					child.IsExtraMoveState = true;
				} else if (i + 1 == len - 1) {
					i = 0;
					if (temp1[i] == 0 && i >= 0 && i < numOfPits && count == 1) {
						temp1[numOfPits] = temp1[numOfPits] + 1 + temp1[(numOfPits * 2) - i];
						temp1[(numOfPits * 2) - i] = 0;
						count--;
						i++;
					} else {
						temp1[i] += 1;
						count--;

					}
				} else if (temp1[i + 1] == 0 && i >= 0 && i < numOfPits && count == 1) {
					i++;
					temp1[numOfPits] = temp1[numOfPits] + 1 + temp1[(numOfPits * 2) - i];
					temp1[(numOfPits * 2) - i] = 0;
					count--;
				} else {
					i++;
					temp1[i] += 1;
					count--;
				}
			}
			if (child.IsExtraMoveState == true) {
				child.play = currentState.play;
			} else {
				if (currentState.play == 1) child.play = 2;
				else if (currentState.play == 2) child.play = 1;
			}
			boolean otherContainsZero = true;
			boolean containsZero = true;
			for (int w = 0; w < numOfPits; w++) {
				if (temp1[w] != 0) containsZero = false;
			}
			if (containsZero) {
				int mankala = 0;
				for (int e = numOfPits + 1; e < temp1.length - 1; e++) {
					mankala += temp1[e];
					temp1[e] = 0;
				}
				temp1[temp1.length - 1] += mankala;
			} else {

				for (int u = numOfPits + 1; u < temp1.length - 1; u++) {
					if (temp1[u] != 0) otherContainsZero = false;
				}
				if (otherContainsZero) {
					int otherMankala = 0;
					for (int fi = 0; fi < numOfPits; fi++) {
						otherMankala += temp1[fi];
						temp1[fi] = 0;
					}
					temp1[numOfPits] += otherMankala;

				}
			}
			int k = 0;
			for (int o: temp1)
			child.stateP[k++] = o;

			if (child.IsExtraMoveState == true && currentState.IsExtraMoveState == true) {
				child.depth = d;
			} else if (child.IsExtraMoveState == true && currentState.IsExtraMoveState == false) {
				child.depth = d + 1;
			} else if (child.IsExtraMoveState == false && currentState.IsExtraMoveState == true) {
				child.depth = d;
			} else if (child.IsExtraMoveState == false && currentState.IsExtraMoveState == false) {
				child.depth = d + 1;
			}
			if (containsZero || otherContainsZero) {
				child.IsExtraMoveState = false;
				child.isEmpty = true;
				if (currentState.play == 1 && root.play == 1) {
					child.eVal = temp1[numOfPits] - temp1[(numOfPits * 2) + 1];
				} else if (currentState.play == 1 && root.play == 2) {
					child.eVal = temp1[(numOfPits * 2) + 1] - temp1[numOfPits];
				} else if (currentState.play == 2 && root.play == 1) {
					child.eVal = temp1[(numOfPits * 2) + 1] - temp1[numOfPits];

				} else if (currentState.play == 2 && root.play == 2) {
					child.eVal = temp1[numOfPits] - temp1[(numOfPits * 2) + 1];
				}

			} else if (child.depth == cuttingEdge && !child.IsExtraMoveState) {
				if (currentState.play == 1 && root.play == 1) {
					child.eVal = temp1[numOfPits] - temp1[(numOfPits * 2) + 1];
				} else if (currentState.play == 1 && root.play == 2) {
					child.eVal = temp1[(numOfPits * 2) + 1] - temp1[numOfPits];
				} else if (currentState.play == 2 && root.play == 1) {
					child.eVal = temp1[(numOfPits * 2) + 1] - temp1[numOfPits];

				} else if (currentState.play == 2 && root.play == 2) {
					child.eVal = temp1[numOfPits] - temp1[(numOfPits * 2) + 1];
				}
			} else {
				if (child.IsExtraMoveState) {
					child.currPlayer = currentState.currPlayer;
					child.eVal = currentState.eVal;
				} else {
					if (currentState.currPlayer == 1) {
						child.eVal = Integer.MAX_VALUE;
						child.currPlayer = 0;
					} else {
						child.eVal = Integer.MIN_VALUE;
						child.currPlayer = 1;
					}
				}
			}
		} else if (temp1[i] == 0) {
			int k = 0;
			for (int o: temp1)
			child.stateP[k++] = o;
			child.depth = d + 1;
		}
		return child;
	}
	public static boardState copyState(boardState source, boardState target) {
		target.nodeName = source.nodeName;
		target.play = source.play;
		int t = 0;
		int[] tempStateP = new int[len];
		for (int k = numOfPits + 1; k < len; k++)
		tempStateP[t++] = source.stateP[k];
		for (int k = 0; k <= numOfPits; k++)
		tempStateP[t++] = source.stateP[k];
		int r = 0;
		for (int y: tempStateP)
		target.stateP[r++] = y;
		target.isEmpty = source.isEmpty;
		target.currPlayer = source.currPlayer;
		target.depth = source.depth;
		target.IsExtraMoveState = source.IsExtraMoveState;
		target.eVal = source.eVal;
		return target;
	}

	public static String conversion(int i) {
		String ret = "";

		if (i <= numOfPits) {
			ret = "B";
			for (int d = 0; d < numOfPits; d++) {
				if (i == d) {
					i = i + 2;
					ret += i;
					break;
				}
			}
		} else if (i > numOfPits) {
			ret = "A";
			for (int d = numOfPits + 1; d < len; d++) {
				if (i == d) {
					i = len - i;
					ret += i;
					break;
				}
			}
		}
		return ret;

	}
}
