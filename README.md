# Tic Tac Toe AI (Vanilla Web)

A small, responsive, accessible Tic Tac Toe game with an AI opponent. Built with vanilla HTML/CSS/JS.

## Features

- Adjustable difficulty: Easy, Medium, Hard
- Choose your symbol (X/O) and who starts (You/AI)
- Smart AI using minimax (alpha-beta) + heuristics
- Scoreboard with persistent storage (localStorage)
- Keyboard and screen-reader friendly
- Mobile responsive UI

## Run

This is a static site. You can open `index.html` directly in a modern browser, or serve it locally:

```bash
# Python 3
python3 -m http.server 8000
# then open http://localhost:8000
```

## Play

1. Pick your symbol and starter
2. Choose difficulty
3. Click or press Enter/Space on a cell to play
4. Restart anytime; reset scores from the scoreboard

## Notes

- Scores persist in `localStorage` under the key `ttt_scoreboard_v1`.
- The AI uses a mix of random moves and shallow search on Easy, deeper limited search on Medium, and full minimax with alpha-beta on Hard.

# Tic-Tac-Toe Game Using Mini-Max Algorithm

**Downloaded from my old GitHub Account(https://github.com/VageeshaDatta)**






<p align="center">
<img width="700" alt="Screenshot 2022-04-07 at 10 30 02 PM" src="https://user-images.githubusercontent.com/54971204/162359027-b7bbca33-815a-469e-ac5f-0e1ae91ad0f5.png">
</p>


## Introduction:



## What is Mini-Max Algorithm and How does it work ?
Mini-Max algorithm is a **Recursive Bactracking Algorithm**, which makes optimal decision for current state based on each of its possible successor states.
It is generally used in 2-player based games such as TicTacToe, Backgammon, Chess, and Mancala etc which are played in turn-based. In general the result or **Terminal** state of these games is either **"Win"** or **"Lose"** or **"Draw"** .One of the palyer is called as **Minimizer** and other player is called as **Maximizer**.

**Minimizer**:Minimizer or **MIN** player is a player, who always tries to minimize the opponents score.

**Maximizer**:Maximizer or **MAX** player is a player, who always tries to get maximium score possible.


## Understanding the Code
Important Functions to understand in the code:
```
public static int minmax (int[][]a,boolean ismaximizing)
{
      .
      .
      .
}
```

**minmax()** function returns the bestscore for each move
- The input of the function of is **"current state"** of the tictactoe game and **"boolean value(**True** or **False**)"** of the current player i.e is the player trying to maximize the score or minimize the score?


```
public static int minmax (int[][]a,boolean ismaximizing)
{

      if(wincheck(a)==1){
          return 100;
      }
      else if(wincheck(a)==2){
          return -100;
      }
      else if(checkdraw()){
          return 0;
      }
      . 
      . 
      .
      .
      .
}
```
- Checking the currrent state of TicTacToe game whether if any one of the palyer had already Won or Lost or a Draw.
- If it is not satisfying any one of the above 3 conditions, then we continue to calculate the bestscore for current player.
```
public static int minmax (int[][]a,boolean ismaximizing)
{
      . 
      . 
      .
      .
      .
      if(ismaximizing)
      {
          bestscore=-10000;
          for (int i = 0; i < 3; i++)
          {
           	for (int j = 0; j < 3; j++)
	           {
	             if (a[i][j] == -1)
	             {
		              a[i][j] = 1;
		              score = minmax(a,false);
                a[i][j] = -1;
		              if (score>bestscore)
		              { 
                  bestscore = score;
		              }
	             }
	           }
          }
      return bestscore;
      }
      
      else
      {
          bestscore=10000;
          for (int i = 0; i < 3; i++)
          {
	   for (int j = 0; j < 3; j++)
	   {
	     if (a[i][j] == -1)
	     {
		a[i][j] = 0;
		score = minmax(a,true);
                a[i][j] = -1;
                if (score<bestscore)
		  {
                   bestscore = score;
		  }
	     }
	   }
          }
      return bestscore;
      }

}
```
- If the current player is trying to maximize the score, then we will place **"X"** in the position that produces the highest value for **"MIN"** of current state.

- If the current player is trying to minimize the score, then we will place **"O"** in the position that produces the minimum value for **"MAX"** of the current state


## Example of Tic-Tac-Toe Game Tree
<p align = "center">
 <img width="985" alt="Screenshot 2022-04-08 at 7 47 21 PM" src="https://user-images.githubusercontent.com/54971204/162454481-0e268ba1-742f-4a3b-8b10-681d94e20e92.png">

</p>


## Screenshots of played Game:

If **"Computer"** starts the game i.e first move is made by **"X"** . 

<p align="center">
	<img width="500" alt="Screenshot 2022-04-07 at 10 30 14 PM" src="https://user-images.githubusercontent.com/54971204/162490594-5f0069f7-086c-4081-96d0-4ca6520a00f5.png">

</p>

Then second move is made by opponent player i.e by **"O"** .

<p align = "center">
	<img width="500" alt="Screenshot 2022-04-07 at 10 30 44 PM" src="https://user-images.githubusercontent.com/54971204/162491208-c56c02b9-c24b-41dc-84f2-5b4eebc7fcba.png">

</p>
	

And so on,

<p align = "center">
	<img width="500" alt="Screenshot 2022-04-07 at 10 30 51 PM" src="https://user-images.githubusercontent.com/54971204/162491605-33101e2c-7627-435a-9ec0-f455bb7eef4e.png">
</p>

Finally, **X** Wins! i.e Computer wins the Game.

<p align = "center">
	<img width="500" alt="Screenshot 2022-04-07 at 10 31 01 PM" src="https://user-images.githubusercontent.com/54971204/162491686-0755b8c1-aa46-42c6-9c05-8cc53cb2f8f1.png">
</p>	

## Results and Conclusion
- As you can see that if **"Computer"** starts the game, then there are always 2 possibilities that the game can end for the opponent player either  **"Draw"** or player **"Loses"** , because the computer always makes an optimal move in each step of the game, therfore making it impossible for opponent player to **"Win"**. 



## How to Run the Code?

- If you dont' have the **Java** software already, you can dowload the Standard Edition(SE) from [here](https://www.oracle.com/java/technologies/downloads/) by following the step by step instructions.
- Once you have downloaded the software, to ensure that you have downloaded the software correctly you can verify in the "Terminal" if you are using Linux or in "Command prompt" if you are using Windows. 
- Using the below code you can check the software version and can also see whether it is installed on your laptop or PC.

```
java --version
```
### Demo output
<p>
	<img width="569" alt="Screenshot 2022-04-08 at 10 11 13 PM" src= "https://user-images.githubusercontent.com/54971204/162488088-b2d69aaa-5123-45df-ada9-118ecf98dad7.png">

</p>

- If you are getting an error, download the software again usign the resources below:
### If you are using Windows,
- YouTube video:https://youtu.be/IJ-PJbvJBGs
- Website: [JavaWebsite](https://docs.oracle.com/javase/10/install/installation-jdk-and-jre-microsoft-windows-platforms.htm#JSJIG-GUID-61460339-5500-40CC-9006-D4FC3FBCFC0D)

### If you are using Mac,
- YouTube video:https://youtu.be/pxi3iIy4F5A
- Website: [JavaWebsite](https://docs.oracle.com/javase/10/install/installation-jdk-and-jre-linux-platforms.htm#JSJIG-GUID-A35B89D1-7EBB-4463-B293-55C8E9713357)

- After downloading the software, you can clone repository to download the zip file.

This repository contains two source code files

- **start.java**: This file contains the code that displays the first page, where you can select which player can start the game i.e either **Player** or **Computer**


- **TicTacToe.java**:This file contains the various functions that are a part of computing **minimax** value in each state of the game. 

Download both the files including images and run the file  **start.java**








## References
- Book: Artificial Intelligence: A Modern Approach by Peter Norvig and Stuart J. Russell
- Website: https://cdn.cs50.net/ai/2020/spring/lectures/0/lecture0.pdf




