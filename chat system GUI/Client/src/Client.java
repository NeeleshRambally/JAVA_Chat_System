import java.net.*;
import java.io.*;
import java.util.*;

public class Client extends javax.swing.JFrame 
{
    String username, address = "localhost";
    ArrayList<String> users = new ArrayList();
    int port = 2222;
    Boolean isConnected = false;
    ArrayList<String> a=new ArrayList();
    Socket sock;
    BufferedReader reader;
    PrintWriter writer;
    
    //--------------------------//
    
    public void ListenThread() 
    {
         Thread IncomingReader = new Thread(new IncomingReader());
         IncomingReader.start();
    }
    
    //--------------------------//
    
    public void userAdd(String data) 
    {
         users.add(data);
    }
    
    //--------------------------//
    
    public void userRemove(String data) 
    {
         ChatWindow.append(data + " is now offline.\n");
    }
    
    //--------------------------//
    
    public void writeUsers() 
    {
         String[] tempList = new String[(users.size())];
         users.toArray(tempList);
         for (String token:tempList) 
         {
             //users.append(token + "\n");
         }
    }
    
    //--------------------------//
    
    public void sendDisconnect() 
    {
        String bye = (username + ": :Disconnect");
        try
        {
            writer.println(bye); 
            writer.flush(); 
        } catch (Exception e) 
        {
            ChatWindow.append("Could not send Disconnect message.\n");
        }
    }

    //--------------------------//
    
    public void Disconnect() 
    {
        try 
        {
            ChatWindow.append("Disconnected.\n");
            sock.close();
        } catch(Exception ex) {
            ChatWindow.append("Failed to disconnect. \n");
        }
        isConnected = false;
        tfUn.setEditable(true);

    }
    
    public Client() 
    {
      
        initComponents();
      
           
    }
        public String checkProf(String message){
        String retString="";
        String arr[]={"Fuck","Bitch","Asshole","Fucking","Bitches"};
        Scanner read=new Scanner(message);
        while(read.hasNext()){
            String check=read.next();
            for(int i =0; i <arr.length;i++){
               if(check.equalsIgnoreCase(arr[i])){ 
                   check="\n"+"*"+"\n"+"(Bad Language isnt allowed in this Public chatroom)\n";
               }
            }
            retString=retString+" "+check;
        }
        return retString;
    }
    //--------------------------//
    
    public class IncomingReader implements Runnable
    {
        @Override
        public void run() 
        {
            String[] data;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";
            try 
            {
                while ((stream = reader.readLine()) != null) 
                {
                    
                    
                     data = stream.split(":");

                    //*********
                        String str=data[0] + " : " + checkProf(data[1]);
                        if(str.contains(" has connected")){
                        Scanner h=new Scanner(str).useDelimiter(":");
                        while(h.hasNext()){
                            a.add(h.next());
                            h.next();
                         } 
                        }//add to the list
                        else if(str.contains("disconnected.")){
                              Scanner h=new Scanner(str).useDelimiter(":");
                        while(h.hasNext()){
                            a.remove(h.next());
                            h.next();
                         } 
                           
                        }
                      
                    //*********
                     if (data[2].equals(chat)) 
                     {
                        ChatWindow.append(data[0] + " : " + checkProf(data[1]) + "\n");
                        ChatWindow.setCaretPosition(ChatWindow.getDocument().getLength());
                   
                     } 
                     else if (data[2].equals(connect))
                     {
                        ChatWindow.removeAll();
                        userAdd(data[0]);
                       
                        
                     } 
                     else if (data[2].equals(disconnect)) 
                     {
                         userRemove(data[0]);
                     } 
                     else if (data[2].equals(done)) 
                     {
                        //users.setText("");
                        writeUsers();
                        users.clear();
                     }
                }
           }catch(Exception ex) { }
        }
    }

    //--------------------------//
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        UserName = new javax.swing.JLabel();
        tfUn = new javax.swing.JTextField();
        Connect = new javax.swing.JButton();
        DC = new javax.swing.JButton();
        FacelessLogin = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ChatWindow = new javax.swing.JTextArea();
        WriteWindow = new javax.swing.JTextField();
        Send = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Public Client");
        setName("client"); // NOI18N
        setResizable(false);

        UserName.setText("Username :");

        tfUn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfUnActionPerformed(evt);
            }
        });

        Connect.setText("Connect");
        Connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnectActionPerformed(evt);
            }
        });

        DC.setText("Disconnect");
        DC.setEnabled(false);
        DC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DCActionPerformed(evt);
            }
        });

        FacelessLogin.setText("Anonymous Login");
        FacelessLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FacelessLoginActionPerformed(evt);
            }
        });

        ChatWindow.setEditable(false);
        ChatWindow.setBackground(new java.awt.Color(0, 0, 0));
        ChatWindow.setColumns(20);
        ChatWindow.setFont(new java.awt.Font("Lucida Sans", 1, 14)); // NOI18N
        ChatWindow.setForeground(new java.awt.Color(51, 255, 0));
        ChatWindow.setLineWrap(true);
        ChatWindow.setRows(5);
        jScrollPane1.setViewportView(ChatWindow);

        WriteWindow.setText("Offline");
        WriteWindow.setEnabled(false);

        Send.setText("SEND");
        Send.setEnabled(false);
        Send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendActionPerformed(evt);
            }
        });

        jButton1.setText("Online Cients");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(WriteWindow, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(Send, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(UserName, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tfUn, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(31, 31, 31)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(FacelessLogin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Connect, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(DC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(UserName)
                            .addComponent(tfUn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Connect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FacelessLogin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DC)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(287, 287, 287)
                        .addComponent(Send, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(WriteWindow)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void tfUnActionPerformed(java.awt.event.ActionEvent evt) {                                     
    
    }                                    

    private void ConnectActionPerformed(java.awt.event.ActionEvent evt) {                                        
        if (isConnected == false) 
        {
            username = tfUn.getText();
            tfUn.setEditable(false);
        

            try 
            {
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(username + ":has connected.:Connect");
                writer.flush(); 
                jButton1.setEnabled(true);
                DC.setEnabled(true);
                isConnected = true; 
                WriteWindow.setText("");
                WriteWindow.setEnabled(true);
                Send.setEnabled(true);
            } 
            catch (Exception ex) 
            {
                ChatWindow.append("Cannot Connect! Try Again. \n");
                tfUn.setEditable(true);
                   
            }
            
            ListenThread();
            
        } else if (isConnected == true) 
        {
            ChatWindow.append("You are already connected. \n");
        }
    }                                       

    private void DCActionPerformed(java.awt.event.ActionEvent evt) {                                   
        sendDisconnect();
        Disconnect();
        DC.setEnabled(false);
        jButton1.setEnabled(false);
        WriteWindow.setText("");
        WriteWindow.setText("Offline");
        WriteWindow.setEnabled(false);
         Send.setEnabled(false);
    }                                  

    private void FacelessLoginActionPerformed(java.awt.event.ActionEvent evt) {                                              
        tfUn.setText("");
        if (isConnected == false) 
        {
            String anon="Faceless";
            Random generator = new Random(); 
            int i = generator.nextInt(99) + 1;
            String is=String.valueOf(i);
            anon=anon.concat(is);
            username=anon;
            tfUn.setText(anon);
            tfUn.setEditable(false);
            try 
            {
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(anon + ":has connected.:Connect");
                writer.flush(); 
                isConnected = true; 
                jButton1.setEnabled(true);
                DC.setEnabled(true);
                isConnected = true;
                WriteWindow.setText("");
                WriteWindow.setEnabled(true);
                Send.setEnabled(true);
            } 
            catch (Exception ex) 
            {
                ChatWindow.append("Cannot Connect! Try Again. \n");
                tfUn.setEditable(true);
            }
            
            ListenThread();
            
        } else if (isConnected == true) 
        {
            ChatWindow.append("You are already connected. \n");
        }        
    }                                             

    private void SendActionPerformed(java.awt.event.ActionEvent evt) {                                     
        String nothing = "";
        if ((WriteWindow.getText()).equals(nothing)) {
            WriteWindow.setText("");
            WriteWindow.requestFocus();
        } else {
            try {
               writer.println(username + ":" + WriteWindow.getText() + ":" + "Chat");
               writer.flush();
            } catch (Exception ex) {
                ChatWindow.append("Message was not sent. \n");
            }
            WriteWindow.setText("");
            WriteWindow.requestFocus();
        }

        WriteWindow.setText("");
        WriteWindow.requestFocus();
    }                                    

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handlin    
        ChatWindow.append("\n Online users : \n");
     for(int i =0;i<a.size();i++){
      ChatWindow.append(a.get(i)+"\n");
      
         
     }
    }                                        

    public static void main(String args[]) 
    {
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                new Client().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify                     
    private javax.swing.JTextArea ChatWindow;
    private javax.swing.JButton Connect;
    private javax.swing.JButton DC;
    private javax.swing.JButton FacelessLogin;
    private javax.swing.JButton Send;
    private javax.swing.JLabel UserName;
    private javax.swing.JTextField WriteWindow;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField tfUn;
    // End of variables declaration                   
}
