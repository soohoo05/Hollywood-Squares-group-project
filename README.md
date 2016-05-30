# Hollywood-Squares-group-project

This was a group project for "Software engineering" class.

Simulates a game of the TV show "Hollywood Squares" (or tic tac toe).

Flow of the program:

-Multiplayer or Single player (and play against computer).
-Login (we used a text file for a database) or create a new user
-Play game or check your score (User database saves scores for users made with both games played and games won)

Play game flow:

-Secret square is created (if a user chooses the square during the valid round, they will get a "prize" at the end).
-Choose a square. Computer asks question, "celebrity" answers question, user agrees or disagrees.
-If answer was correct, current player gets the square, else opponent gets the square (as long as opponent doesn't win the game).
-Program checks if current player won, else changes to next players turn.
-First player to win 2 rounds wins the game.
-After winner is announced, users score for games played and games won is updated in the user database.

