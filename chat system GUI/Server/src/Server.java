
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Server extends javax.swing.JFrame 
{
   ArrayList clientOutputStreams;
   ArrayList<String> users;
    Logger logger =Logger.getLogger("ServerLog");
        FileHandler fh;
   public class ClientHandler implements Runnable	
   {
       BufferedReader reader;
       Socket sock;
       PrintWriter client;

       public ClientHandler(Socket clientSocket, PrintWriter user) 
       {
            client = user;
            try 
            {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            }
            catch (Exception ex) 
            {
                ChatWindow.append("Unexpected error... \n");
            }

       }

       @Override
       public void run() 
       {
            String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat" ;
            String[] data;

            try 
            {
                while ((message = reader.readLine()) != null) 
                {
                    ChatWindow.append("Received: " + message + "\n");
                    data = message.split(":");
                    
                    for (String token:data) 
                    {
                        ChatWindow.append(token + "\n");
                    }

                    if (data[2].equals(connect)) 
                    {
                        BroadCast((data[0] + ":" + data[1] + ":" + chat));
                        userAdd(data[0]);
                    } 
                    else if (data[2].equals(disconnect)) 
                    {
                        BroadCast((data[0] + ":has disconnected." + ":" + chat));
                        removeUser(data[0]);
                    } 
                    else if (data[2].equals(chat)) 
                    {
                        BroadCast(message);
                    } 
                    else 
                    {
                        ChatWindow.append("No Conditions were met. \n");
                    }
                } 
             } 
             catch (Exception ex) 
             {
                ChatWindow.append("Lost a connection. \n");
                ex.printStackTrace();
                clientOutputStreams.remove(client);
             } 
	} 
    }

    public Server() 
    {
        initComponents();
                Thread starter = new Thread(new ServerStart());
        starter.start();
        
        ChatWindow.append("Server started...\n");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ChatWindow = new javax.swing.JTextArea();
        OnlineUsers = new javax.swing.JButton();
        Clear = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server");
        setName("server"); // NOI18N
        setResizable(false);

        ChatWindow.setEditable(false);
        ChatWindow.setBackground(new java.awt.Color(0, 0, 0));
        ChatWindow.setColumns(20);
        ChatWindow.setForeground(new java.awt.Color(51, 255, 0));
        ChatWindow.setLineWrap(true);
        ChatWindow.setRows(5);
        jScrollPane1.setViewportView(ChatWindow);

        OnlineUsers.setText("Online Users");
        OnlineUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OnlineUsersActionPerformed(evt);
            }
        });

        Clear.setText("Clear");
        Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearActionPerformed(evt);
            }
        });

        jButton1.setText("View LogFile");
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(OnlineUsers, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(OnlineUsers)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Clear))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void OnlineUsersActionPerformed(java.awt.event.ActionEvent evt) {                                            
        ChatWindow.append("\n Online users : \n");
        for (String current_user : users)
        {
            ChatWindow.append(current_user);
            ChatWindow.append("\n");
        }    
        
    }                                           

    private void ClearActionPerformed(java.awt.event.ActionEvent evt) {                                      
        ChatWindow.setText("");
    }                                     

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
try{
    ChatWindow.append("\nLog File : \n");
    FileInputStream fStream= new FileInputStream("ServerLog.log");
    BufferedReader br= new BufferedReader(new InputStreamReader(fStream));
    String str;
    while((str=br.readLine())!=null){
        ChatWindow.append(str+"\n");
    }

}catch(Exception e){// TODO add your handling code here:
    e.printStackTrace();
}
    }                                        

    public static void main(String args[]) 
    {
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() {
                new Server().setVisible(true);
            }
        });
    }
    
    public class ServerStart implements Runnable 
    {
        @Override
        public void run() 
        {
            clientOutputStreams = new ArrayList();
            users = new ArrayList();  

            try 
            {
                ServerSocket serverSock = new ServerSocket(2222);

                while (true) 
                {
				Socket clientSock = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
				clientOutputStreams.add(writer);

				Thread listener = new Thread(new ClientHandler(clientSock, writer));
				listener.start();
				ChatWindow.append("Got a connection. \n");
                }
            }
            catch (Exception ex)
            {
                ChatWindow.append("Error making a connection. \n");
            }
        }
    }
    
    public void userAdd (String data) throws IOException 
    {//************
        
        try{
        fh=new FileHandler("ServerLog.log");
        logger.addHandler(fh);
        SimpleFormatter formatter= new SimpleFormatter();
        fh.setFormatter(formatter);
        
        //print user to text file
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        ChatWindow.append("Before " + name + " added. \n");
        users.add(name);
        logger.info(name+" connected");
        ChatWindow.append("After " + name + " added. \n");
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList) 
        {
            message = (token + add);
            BroadCast(message);
        }
        BroadCast(done);
        }catch(SecurityException e){
            e.printStackTrace();
        }
      
    }
    
    public void removeUser (String data) 
    {
          try{
        fh=new FileHandler("ServerLog.log");
        logger.addHandler(fh);
        SimpleFormatter formatter= new SimpleFormatter();
        fh.setFormatter(formatter);
        //remove user from text file
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        users.remove(name);
        logger.info(name +" disconnected");
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList) 
        {
            message = (token + add);
            BroadCast(message);
        }
        BroadCast(done);
          }catch(IOException e){
              e.printStackTrace();
          }
          
    }
    public String checkProf(String message){
        String retString="";
        String arr[]={"Fuck","Bitch","Asshole","Fucking"};
        Scanner read=new Scanner(message);
        while(read.hasNext()){
            String check=read.next();
            for(int i =0; i <arr.length;i++){
               if(check.equalsIgnoreCase(arr[i])){ 
                   check="*(Bad Language isnt allowed in this Public chatroom)";
               }
            }
            retString=retString+" "+check;
        }
        return retString;
    }
    
    public void BroadCast(String message) 
    {
	Iterator it = clientOutputStreams.iterator();
//Check Profanity 
     String a=checkProf(message);
        while (it.hasNext()) 
        {
            try 
            {
                PrintWriter writer = (PrintWriter) it.next();
		writer.println(a);
		ChatWindow.append("Sending: " + a + "\n");
                writer.flush();
                ChatWindow.setCaretPosition(ChatWindow.getDocument().getLength());

            } 
            catch (Exception ex) 
            {
		ChatWindow.append("Error BroadCasting. \n");
            }
        } 
    }
    
    // Variables declaration - do not modify                     
    private javax.swing.JTextArea ChatWindow;
    private javax.swing.JButton Clear;
    private javax.swing.JButton OnlineUsers;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration                   
}
