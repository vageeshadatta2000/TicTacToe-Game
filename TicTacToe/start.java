import java.awt.*;                      //awt packages like swing,event
import javax.swing.*;
import java.awt.event.*;
class start extends JFrame implements ActionListener{        //for action events
  JButton b1,b2;
  JLabel b,bn;
  Container c=getContentPane();       //used to place components
  start(){                             //constructor
    setTitle("TicTacToe");
    setSize(800,500);                   //(width,height)
    setLocationRelativeTo(null);        //to display window in center
    setDefaultCloseOperation(EXIT_ON_CLOSE);       //close operation of window
      c.setLayout(null); //to make window initially null
 c.setBackground(Color.WHITE);
    ImageIcon img=new ImageIcon("Logo.jpeg");   //inserting image

    b=new JLabel(img);

    b.setVerticalAlignment(JLabel.NORTH);
    b.setBounds(0,15,800,500);

    ImageIcon img1=new ImageIcon("background.jpeg");   //inserting image
    bn=new JLabel("",img1,JLabel.CENTER);

    bn.setBounds(0,0,800,500);



    b1=new JButton("COMPUTER");
    b1.setBounds(160,290,190,100);
    b1.setFocusable(false);
    b1.setFont(new Font("Ink Free",Font.BOLD,20));
    c.add(b1);
    b1.addActionListener(this);         //to perform actionevents by button

    b2=new JButton("PLAYER");
    b2.setBounds(445,290,190,100);
    b2.setFocusable(false);
    b2.setFont(new Font("Ink Free",Font.BOLD,20));
    c.add(b2);
    b2.addActionListener(this);
    c.add(b);
    c.add(bn);


    setVisible(true);
    setResizable(false);                 //visibility of window
  }
  public void actionPerformed(ActionEvent e){      //method
    if(e.getSource()==b1){
      int textMsg;
      textMsg=0;


      TicTacToe a=new TicTacToe(textMsg);
      a.setVisible(true);
      this.setVisible(false);
       this.dispose();
    }
    else if(e.getSource()==b2){
      int textMsg;
      textMsg=1;
      TicTacToe a=new TicTacToe(textMsg);
      a.setVisible(true);
      this.setVisible(false);
       this.dispose();
    }
  }

  public static void main(String[] args) {  //main method
    start o=new start();
  }
}
