import javax.swing.*;
import java.awt.*;

public class GUI {
    private JPanel root;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;


    public void move(String s){
        button1.setText(String.valueOf(s.charAt(0)));
        button4.setText(String.valueOf(s.charAt(1)));
        button7.setText(String.valueOf(s.charAt(2)));
        button2.setText(String.valueOf(s.charAt(3)));
        button5.setText(String.valueOf(s.charAt(4)));
        button8.setText(String.valueOf(s.charAt(5)));
        button3.setText(String.valueOf(s.charAt(6)));
        button6.setText(String.valueOf(s.charAt(7)));
        button9.setText(String.valueOf(s.charAt(8)));
    }

    public void setProperties(){
        button1.setFont(new Font("Arial",Font.PLAIN, 40));
        button2.setFont(new Font("Arial",Font.PLAIN, 40));
        button3.setFont(new Font("Arial",Font.PLAIN, 40));
        button4.setFont(new Font("Arial",Font.PLAIN, 40));
        button5.setFont(new Font("Arial",Font.PLAIN, 40));
        button6.setFont(new Font("Arial",Font.PLAIN, 40));
        button7.setFont(new Font("Arial",Font.PLAIN, 40));
        button8.setFont(new Font("Arial",Font.PLAIN, 40));
        button9.setFont(new Font("Arial",Font.PLAIN, 40));
        button1.setFocusable(false);
        button2.setFocusable(false);
        button3.setFocusable(false);
        button4.setFocusable(false);
        button5.setFocusable(false);
        button6.setFocusable(false);
        button7.setFocusable(false);
        button8.setFocusable(false);
        button9.setFocusable(false);


    }

    public void Finished(){
        JOptionPane.showMessageDialog(null, "Finished!", "Success", JOptionPane.PLAIN_MESSAGE);
    }

    public void totalMoves(int count){

        JOptionPane.showMessageDialog(null, "It took " + count + " moves!", "Moves", JOptionPane.PLAIN_MESSAGE);
    }

    public void InvalidInitialStateException(){
        JOptionPane.showMessageDialog(null, "Invalid initial state! Make sure to follow the exact format. There should be no extra spaces", "Error", JOptionPane.WARNING_MESSAGE);
    }

    public void SolutionNotPossibleException(){
        JOptionPane.showMessageDialog(null, "Solution is not possible!", "Error", JOptionPane.WARNING_MESSAGE);
    }

    public void FileNotFoundException(){
        JOptionPane.showMessageDialog(null, "File.txt not found!!", "Error", JOptionPane.WARNING_MESSAGE);
    }

    public JPanel getRoot(){return this.root;}

}
