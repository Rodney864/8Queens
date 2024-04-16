**Eight Queens Problem with Hill-Climbing Algorithm**

**Instructions**
The objective of this program is to place 8 queens on an 8x8 board where none of the queens are in conflict with each other. 
The solution is implemented using the Hill-Climbing algorithm with random restarts.

**Problem Overview & Algorithm Description**

The 8-Queens problem requires placing 8 queens on a board with 8 rows and columns so that no queen occupies the same row, column, or diagonal as another queen. 
The Hill-Climbing with random restart algorithm starts with a random starting state where each queen is placed in a random row of each column.

From there, the algorithm checks if the state is a goal state (no queens are in conflict). 
If not, it evaluates all possible neighbor states by moving each column’s queen through the rows of its column and generating a heuristic value for each of those states.

When all neighbor states have been generated, it checks if any states were generated with a lower heuristic value than the current state. 
If a better state was not found, it indicates the local minima and performs a random restart. 
If a better (lower heuristic) state was found, that state becomes the current state, and the process repeats on that state.
