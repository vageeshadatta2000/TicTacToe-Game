import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;



public class TicTacToe extends JFrame implements ActionListener{
	Random random = new Random();
	JPanel title_panel = new JPanel();
	JPanel title_panel1 = new JPanel();
	JPanel button_panel = new JPanel();
	JLabel textfield = new JLabel();
	JLabel textfield1 = new JLabel();
	JButton[] buttons = new JButton[9];
	JButton b1 = new JButton();
	JButton b2 = new JButton();
	boolean player1_turn;
	public static int cscore=0;
	public static int pscore=0;
	public static int turn=0;
	public static int tic[][] = {{-1,-1,-1},{-1,-1,-1},{-1,-1,-1}};;
	public static int main[][] = {{0,1,2},{3,4,5},{6,7,8}};
public static boolean checkdraw(){
		 for (int i = 0; i < 3; i++)
		 {
 for (int j = 0; j < 3; j++)
	 {
		 if (tic[i][j] == -1){
				 return false;
		 }
	 }
		 }
		 return true;
 }


	TicTacToe(int s){

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800,800);
		getContentPane().setBackground(new Color(50,50,50));
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		Container c=getContentPane();
    c.setLayout(null);
		textfield.setBackground(new Color(25,25,25));
		textfield.setForeground(Color.CYAN);

		textfield.setFont(new Font("Ink Free",Font.BOLD,75));
		textfield.setHorizontalAlignment(JLabel.CENTER);
		textfield.setText("Tic-Tac-Toe");
		textfield.setOpaque(true);

		textfield1.setBackground(new Color(25,25,25));
		textfield1.setForeground(Color.PINK);

		textfield1.setFont(new Font("Ink Free",Font.BOLD,50));
		textfield1.setHorizontalAlignment(JLabel.CENTER);
		textfield1.setText("Score    "+"  X : "+cscore+"      O : "+pscore);
		textfield1.setOpaque(true);

		title_panel.setLayout(new BorderLayout());
		title_panel.setBounds(0,0,800,100);

		title_panel1.setLayout(new BorderLayout());
		title_panel1.setBounds(0,90,800,100);

		button_panel.setLayout(new GridLayout(3,3));
		button_panel.setOpaque(true);

		button_panel.setBounds(195,200,400,400);


		for(int i=0;i<9;i++) {
			buttons[i] = new JButton();
			button_panel.add(buttons[i]);
			buttons[i].setFont(new Font("MV Boli",Font.BOLD,60));


			buttons[i].setFocusable(false);
			buttons[i].addActionListener(this);

		}
    b1.setText("Replay");
		b1.setBounds(128,635,230,40);
		b1.setFocusable(false);
		b1.addActionListener(this);
		c.add(b1);

		b2.setText("Back");
		b2.setBounds(435,635,230,40);
		b2.setFocusable(false);
		b2.addActionListener(this);
		c.add(b2);

		title_panel.add(textfield);
		c.add(title_panel,BorderLayout.NORTH);
		title_panel1.add(textfield1);
		c.add(title_panel1,BorderLayout.NORTH);

		c.add(button_panel);


    turn=s;
		firstTurn(s);

		setVisible(true);
		setResizable(false);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==b2){
      start n=new start();
      n.setVisible(true);

			for(int i=0;i<3;i++)
			{
				for(int j=0;j<3;j++){
					tic[i][j]=-1;
				}
			}
			for(int i=0;i<9;i++) {
				buttons[i].setText("");
				buttons[i].setBackground(Color.WHITE);
				buttons[i].setOpaque(false);
buttons[i].setContentAreaFilled(false);
buttons[i].setBorderPainted(false);

				buttons[i].setEnabled(true);
			}
			cscore=0;pscore=0;
			textfield1.setText("Score    "+"  X : "+cscore+"      O : "+pscore);
		   this.setVisible(false);
			 this.dispose();
    }
		if(e.getSource()==b1){
      for(int i=0;i<3;i++)
			{
				for(int j=0;j<3;j++){
					tic[i][j]=-1;
				}
			}
			for(int i=0;i<9;i++) {
				buttons[i].setText("");
				buttons[i].setBackground(new JButton().getBackground());

				buttons[i].setEnabled(true);
			}

	firstTurn(turn);
    }

if(player1_turn){
		for(int i=0;i<9;i++) {
			if(e.getSource()==buttons[i]) {
				if(player1_turn) {
					if(buttons[i].getText()=="") {
						buttons[i].setForeground(new Color(255,0,0));
						buttons[i].setText("O");
						player1_turn=false;
						textfield.setText("X turn");
						for(int l=0;l<3;l++){
							for(int m=0;m<3;m++){
								if(main[l][m]==i){
									tic[l][m]=0;
									break;
								}
							}
						}


						check();
					}
	}
	}


}}
if(player1_turn==false){
int xi,xj,loc;
long start = System.currentTimeMillis();
int[] p = heuristic (tic);
long end = System.currentTimeMillis();
float sec = (end - start) / 1000F;
if(sec>10){
	cscore-=1;
		textfield1.setText("Score    "+"  X : "+cscore+"      O : "+pscore);
}

xi = p[0];
xj = p[1];
tic[xi][xj] = 1;
loc=main[xi][xj];
if(buttons[loc].getText()=="") {
	buttons[loc].setForeground(new Color(0,0,255));
	buttons[loc].setText("X");
	textfield.setText("O turn");
	player1_turn=true;
	check();

}
}
}


	public void firstTurn(int s) {
		if(s==0){
			player1_turn=false;
			textfield.setText("X Computer turn");
		  JButton ab=new JButton("Button");

      ab.addActionListener(this);
			ab.doClick();
}
else{
	player1_turn=true;
	textfield.setText("O Player turn");
}
	}

	public void check() {
		//check X win conditions
		if(
				(buttons[0].getText()=="X") &&
				(buttons[1].getText()=="X") &&
				(buttons[2].getText()=="X")
				) {
			cscore++;
				textfield1.setText("Score    "+"  X : "+cscore+"      O : "+pscore);
			xWins(0,1,2);
		}
		if(
				(buttons[3].getText()=="X") &&
				(buttons[4].getText()=="X") &&
				(buttons[5].getText()=="X")
				) {
					cscore++;
						textfield1.setText("Score    "+"  X : "+cscore+"      O : "+pscore);
			xWins(3,4,5);
		}
		if(
				(buttons[6].getText()=="X") &&
				(buttons[7].getText()=="X") &&
				(buttons[8].getText()=="X")
				) {
					cscore++;
						textfield1.setText("Score    "+"  X : "+cscore+"      O : "+pscore);
			xWins(6,7,8);
		}
		if(
				(buttons[0].getText()=="X") &&
				(buttons[3].getText()=="X") &&
				(buttons[6].getText()=="X")
				) {
					cscore++;
						textfield1.setText("Score    "+"  X : "+cscore+"      O : "+pscore);
			xWins(0,3,6);
		}
		if(
				(buttons[1].getText()=="X") &&
				(buttons[4].getText()=="X") &&
				(buttons[7].getText()=="X")
				) {
					cscore++;
						textfield1.setText("Score    "+"  X : "+cscore+"      O : "+pscore);
			xWins(1,4,7);
		}
		if(
				(buttons[2].getText()=="X") &&
				(buttons[5].getText()=="X") &&
				(buttons[8].getText()=="X")
				) {
					cscore++;
						textfield1.setText(" Score  "+"  X : "+cscore+"   O : "+pscore);
			xWins(2,5,8);
		}
		if(
				(buttons[0].getText()=="X") &&
				(buttons[4].getText()=="X") &&
				(buttons[8].getText()=="X")
				) {
					cscore++;
						textfield1.setText(" Score  "+"  X : "+cscore+"   O : "+pscore);
			xWins(0,4,8);
		}
		if(
				(buttons[2].getText()=="X") &&
				(buttons[4].getText()=="X") &&
				(buttons[6].getText()=="X")
				) {
					cscore++;
						textfield1.setText(" Score  "+"  X : "+cscore+"   O : "+pscore);
			xWins(2,4,6);
		}
		//check O win conditions
		if(
				(buttons[0].getText()=="O") &&
				(buttons[1].getText()=="O") &&
				(buttons[2].getText()=="O")
				) {
					pscore++;
						textfield1.setText(" Score  "+"  X : "+cscore+"   O : "+pscore);
			oWins(0,1,2);
		}
		if(
				(buttons[3].getText()=="O") &&
				(buttons[4].getText()=="O") &&
				(buttons[5].getText()=="O")
				) {
						pscore++;
							textfield1.setText(" Score  "+"  X : "+cscore+"   O : "+pscore);
			oWins(3,4,5);
		}
		if(
				(buttons[6].getText()=="O") &&
				(buttons[7].getText()=="O") &&
				(buttons[8].getText()=="O")
				) {
						pscore++;
							textfield1.setText(" Score  "+"  X : "+cscore+"   O : "+pscore);
			oWins(6,7,8);
		}
		if(
				(buttons[0].getText()=="O") &&
				(buttons[3].getText()=="O") &&
				(buttons[6].getText()=="O")
				) {
						pscore++;
							textfield1.setText(" Score  "+"  X : "+cscore+"   O : "+pscore);
			oWins(0,3,6);
		}
		if(
				(buttons[1].getText()=="O") &&
				(buttons[4].getText()=="O") &&
				(buttons[7].getText()=="O")
				) {
						pscore++;
							textfield1.setText(" Score  "+"  X : "+cscore+"   O : "+pscore);
			oWins(1,4,7);
		}
		if(
				(buttons[2].getText()=="O") &&
				(buttons[5].getText()=="O") &&
				(buttons[8].getText()=="O")
				) {
						pscore++;
							textfield1.setText(" Score  "+"  X : "+cscore+"   O : "+pscore);
			oWins(2,5,8);
		}
		if(
				(buttons[0].getText()=="O") &&
				(buttons[4].getText()=="O") &&
				(buttons[8].getText()=="O")
				) {
						pscore++;
							textfield1.setText(" Score  "+"  X : "+cscore+"   O : "+pscore);
			oWins(0,4,8);
		}
		if(
				(buttons[2].getText()=="O") &&
				(buttons[4].getText()=="O") &&
				(buttons[6].getText()=="O")
				) {
				pscore++;
					textfield1.setText(" Score  "+"  X : "+cscore+"   O : "+pscore);
			oWins(2,4,6);
		}
		int l=0;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
			if(tic[i][j]!=-1){
				l++;
}
			}
		}
		if(l==9 && wincheck(tic)==0){
			for(int i=0;i<9;i++) {
				buttons[i].setEnabled(false);
			}
			textfield.setText("DRAW");

		}
	}

	public static int wincheck (int[][]a)
  {
    // horizontal win
    if (a[0][0] == 1 && a[0][1] == 1 && a[0][2] == 1)
      {
	return 1;
      }
    if (a[0][0] == 0 && a[0][1] == 0 && a[0][2] == 0)
      {
	return 2;
      }
    if (a[1][0] == 1 && a[1][1] == 1 && a[1][2] == 1)
      {
	return 1;
      }
    if (a[1][0] == 0 && a[1][1] == 0 && a[1][2] == 0)
      {
	return 2;
      }
    if (a[2][0] == 1 && a[2][1] == 1 && a[2][2] == 1)
      {
	return 1;
      }
    if (a[2][0] == 0 && a[2][1] == 0 && a[2][2] == 0)
      {
	return 2;
      }
    // vertical win
    if (a[0][0] == 1 && a[1][0] == 1 && a[2][0] == 1)
      {
	return 1;
      }
    if (a[0][0] == 0 && a[1][0] == 0 && a[2][0] == 0)
      {
	return 2;
      }
    if (a[0][1] == 1 && a[1][1] == 1 && a[2][1] == 1)
      {
	return 1;
      }
    if (a[0][1] == 0 && a[1][1] == 0 && a[2][1] == 0)
      {
	return 2;
      }
    if (a[0][2] == 1 && a[1][2] == 1 && a[2][2] == 1)
      {
	return 1;
      }
    if (a[0][2] == 0 && a[1][2] == 0 && a[2][2] == 0)
      {
	return 2;
      }
    //diagonal win
    if (a[0][0] == 1 && a[1][1] == 1 && a[2][2] == 1)
      {
	return 1;
      }
    if (a[0][0] == 0 && a[1][1] == 0 && a[2][2] == 0)
      {
	return 2;
      }
    if (a[0][2] == 1 && a[1][1] == 1 && a[2][0] == 1)
      {
	return 1;
      }
    if (a[0][2] == 0 && a[1][1] == 0 && a[2][0] == 0)
      {
	return 2;
      }
    return 0;

  }



 public static int minmax (int[][]a,boolean ismaximizing){
      int bestscore,score;
      if(wincheck(a)==1){
          return 100;
      }
      else if(wincheck(a)==2){
          return -100;
      }
      else if(checkdraw()){
          return 0;
      }

      if(ismaximizing){
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

      else{
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

	public static int[] heuristic (int[][]a)
  {
    int[][] kl = a;
    int bestscore, score;
    int[] p = new int[2];
    bestscore = -10000;
    for (int i = 0; i < 3; i++)
      {
	for (int j = 0; j < 3; j++)
	  {
	    if (kl[i][j] == -1)
	      {
		kl[i][j] = 1;
		score = minmax(kl,false);

		if (score>bestscore)
		  {

		    bestscore = score;
		    p[0] = i;
		    p[1] = j;
		  }
		kl[i][j] = -1;


	      }

	  }
      }

    return p;

  }

	public void xWins(int a,int b,int c) {
		buttons[a].setBackground(Color.YELLOW);
		buttons[b].setBackground(Color.YELLOW);
		buttons[c].setBackground(Color.YELLOW);

		for(int i=0;i<9;i++) {
			buttons[i].setEnabled(false);
		}
		textfield.setText("X wins");
	}
	public void oWins(int a,int b,int c) {
		buttons[a].setBackground(Color.YELLOW);
		buttons[b].setBackground(Color.YELLOW);
		buttons[c].setBackground(Color.YELLOW);

		for(int i=0;i<9;i++) {
			buttons[i].setEnabled(false);
		}
		textfield.setText("O wins");
	}
	public static void main(String[] args) {
    TicTacToe p=new TicTacToe(turn);

  }
}
