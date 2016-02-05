Input:
You are provided with a file input.txt that describes the current state of the game. 

<Task#> Greedy=1, MiniMax=2, Alpha-Beta=3, Competition=4
<Your player: 1 or 2>
<Cutting off depth>
<Board state for player-2> <Board state for player-1> <#stones in player-2’s mancala> <#stones in player-1’s mancala> Example:
2
1
3
3 3 3
3 3 3
0
0
 You will be playing as player-1. Remember that player-1 is assigned the lower side of the
board.
 The cut off depth for MiniMax is given as 2. You should ignore the cut off depth if the task is
Greedy.
 Line-4 represents the board state for player-2, i.e. the upper side of the board. Each number
is separated by a single white space.
 Line-5 represents the board state for player-1, i.e. the upper side of the board. Each number
is separated by a single white space.
 Line-6 gives you the number of stones in player-2’s mancala.
 Line-7 gives you the number of stones in player-1’s mancala.
