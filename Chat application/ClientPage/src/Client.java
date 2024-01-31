package ClientPage.ClientPage.src;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Scrollable;
public class Client extends JFrame{

    Socket socket;
    BufferedReader br;
    PrintWriter out;
    private JLabel clientside=new JLabel("Client Area");
    private JTextArea textArea=new JTextArea();
    private JTextField textField=new JTextField();
    private Font font=new Font("Roboto",Font.PLAIN,20);
    public Client(){

        try{
            System.out.println("Sending request to server");
            socket=new Socket("127.0.0.1",7777);
            System.out.println("connection done.");
             
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());

            createGUI2();
            handleEvents();
            startReading();
            // startWriting();

        }
        catch(Exception e){
            e.printStackTrace();
        }


    }
    private void handleEvents() {

        textField.addKeyListener((new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
              
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
               
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==10){
                    String contentToSend= textField.getText();

                    textArea.append("Me : "+contentToSend+"\n");
                    out.println(contentToSend);
                    out.flush();
                    textField.setText("");
                    textField.requestFocus();
                }
                
            }
            
        }));

    }


    private void createGUI2() {

        setTitle("Client Messager[END]");
        setSize(585,780);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        
        Container c=this.getContentPane();
        clientside.setFont(font);
        textField.setFont(font);
        ImageIcon i1  = new ImageIcon(ClassLoader.getSystemResource("whatsapp3.png"));
        Image i3 = i1.getImage().getScaledInstance(50, 50,Image.SCALE_DEFAULT);
        ImageIcon i2 = new ImageIcon(i3);
        JLabel la1 = new JLabel(i2);
        la1.setBounds(10,10,100,50);
        c.add(la1);
        clientside.setBounds(240,20,100,50);
        c.add(clientside);
        
        textField.setBounds(10,680,420,50);
        c.add(textField);
        ImageIcon i11  = new ImageIcon(ClassLoader.getSystemResource("call.png"));
        Image i31 = i11.getImage().getScaledInstance(30, 30,Image.SCALE_DEFAULT);
        ImageIcon i21 = new ImageIcon(i31);
        JLabel la11 = new JLabel(i21);
        la11.setBounds(440,690,30,30);
        c.add(la11);

        ImageIcon i12  = new ImageIcon(ClassLoader.getSystemResource("camera.png"));
        Image i32 = i12.getImage().getScaledInstance(30, 30,Image.SCALE_DEFAULT);
        ImageIcon i22 = new ImageIcon(i32);
        JLabel la12 = new JLabel(i22);
        la12.setBounds(480,690,30,30);
        c.add(la12);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("attach.png"));
        Image i33 = i13.getImage().getScaledInstance(30, 30,Image.SCALE_DEFAULT);
        ImageIcon i23 = new ImageIcon(i33);
        JLabel la13 = new JLabel(i23);
        la13.setBounds(520,690,30,30);
        c.add(la13);

        ImageIcon i14  = new ImageIcon(ClassLoader.getSystemResource("setting.png"));
        Image i34 = i14.getImage().getScaledInstance(50, 50,Image.SCALE_DEFAULT);
        ImageIcon i24 = new ImageIcon(i34);
        JLabel la14 = new JLabel(i24);
        la14.setBounds(480,10,100,50);
        c.add(la14);
        
        textArea=new ImageJTextArea(new File("C:\\Users\\ASUS\\Desktop\\Coderspacket\\ChatApplication\\background.png"));
        
        textArea.setBounds(10,70,550,600);
        textArea.setFont(font);
        textArea.setForeground(Color.white);
        textArea.setEditable(false);
        
        c.add(textArea);
        // c.add(textArea);
        

        
        


    setVisible(true);
    }
    private void startReading() {

        Runnable r1=()->{

            System.out.println("Reader start . . .");
            try{
            while(true){
                
                
                    String msg=br.readLine();
                    if(msg.equalsIgnoreCase("exit") || msg==null){
                        System.out.println("Server has terminated the meeting . . . ");
                        JOptionPane.showMessageDialog(this,"Server has Terminated the chat");
                        textField.setEnabled(false);
                        socket.close();
                        break;
                    }
                    System.out.println("Server : "+msg);
                    textArea.append("Server : "+msg+"\n");
                

                
            }
        }
        catch(Exception e){
             
        }
       

        };

        new Thread(r1).start();
    }
    private void startWriting() {

        System.out.println("Writer start . . .");

        Runnable r2=()->{

            try {
            while(!socket.isClosed()){
               
                    BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
                    String msg2=in.readLine();
                    out.println(msg2);
                    out.flush();
                    if(msg2.equalsIgnoreCase("exit")){
                        
                         socket.close();
                         break;
                    }
                    
                    
                
            }
        } catch (Exception e) {
            
                   
        }
        System.out.println("Connection is Closed ");

        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        new Client();
    }
    
}
class ImageJTextArea extends JTextArea
{
File image;
    public ImageJTextArea(File image)
    {
        this.image=image;
        setOpaque(false);
        
    }
    
    public void paintComponent(final Graphics g)
    {
        try
        {
        // Scale the image to fit by specifying width,height
        g.drawImage(new ImageIcon(image.getAbsolutePath()).getImage(),0,0,550,600,this);
        super.paintComponent(g);
        }catch(Exception e){}
    }
}

