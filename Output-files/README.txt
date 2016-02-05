Output:
Greedy:
The program should output one file named “next_state.txt” showing the next state of the board after the greedy move in the following format.
 Line-1 represents the board state for player-2, i.e. the upper side of the board. Each number is separated by a single white space.
 Line-2 represents the board state for player-1, i.e. the upper side of the board. Each number is separated by a single white space.
 Line-3 gives you the number of stones in player-2’s mancala.
 Line-4 gives you the number of stones in player-1’s mancala.

MiniMax:
The program should output two files named “next_state.txt” showing the next state of the board after the greedy move and “traverse_log.txt” showing the traverse log of your program in the following format.
 The format of “next_state.txt” should be the same as shown above.
 The format of “traverse_log.txt” should be as shown below.
Node,Depth,Value 
Note: The MiniMax traverse log requires 3 columns. Each column is separated by “,” (a single comma). Three columns are node, depth and value. 
“Node”: is the name of the pit you chosen as the next move. For example, player-1 chooses pit “B2” as the first move to be explored as per the evaluation order. The depth of the node “B2” is 1. Then player-1 chooses pit “B3” as his/her next move as move from pit “B2” ends up putting the last stone in the Mancala of player-1 and according to the rules of the game, player-1 needs to make another move. As this is the turn from the same player, the depth of the node “B3” is also 1. Next, player-2 chooses pit “A2” as his/her move and the depth of the node “A2” becomes 2. “root” is the special name assigned to the root node.
“Depth”: is the depth of the node. The depth of root node is 0.
“Value”: is the value of the node. The value is initialized to “-Infinity” for the max node and “Infinity” for the min node. The value will be updated when its children return the value to the node. The value of the leaf nodes is the evaluated value, for example, node “A2” has value 1.
The algorithm traverses from root node. 
The log should show both when:
1) The algorithm traverses down to the node.
2) The value of the node is updated from its children.
For example, the log shows value of node “B3” when traversing from node “B2”. The log shows the node “B3” again when the node is updated from its children “A2”, “A3”, and “A4”. In general, the leaf nodes have no children, but if the move from the leaf node ends up putting the last stone in the corresponding player’s mancala, then that leaf node will have children as the player has to choose another move. Thus, for any node that has children, the log will show its value after each update. You can relate the reporting of the traverse log to DFS traversal of the Minimax game tree.

Alpha-Beta:
The program should output two files named “next_state.txt” showing the next state of the board after the greedy move and “traverse_log.txt” showing the traverse log of your program in the following format.
 The format of “next_state.txt” should be the same as shown above.
The format of “traverse_log.txt” will be similar to the one for MiniMax, but with two additional
columns.
Node,Depth,Value,Alpha,Beta ........
........
........
Note: The Alpha-Beta traverse log requires 5 columns. Each column is separated by “,” (a single comma). Five columns are node, depth, value, alpha, and beta. The description is same as with the MiniMax log. However, you need to show the alpha and beta values in the Alpha-Beta traverse log
