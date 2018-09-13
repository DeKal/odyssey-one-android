package ClientSocket;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.util.Log;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketImpl;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;


/**
 * Created by leehoa on 12/2/16.
 */

public class ClientSocket {
    private String serverMessage;
    /**
     * Specify the Server Ip Address here. Whereas our Socket Server is started.
     * */
    public static final String SERVERIP = "192.168.4.1"; // your computer IP address
    public static final int SERVERPORT = 1234;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;
    Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private boolean connection = false;

    String IP = "192.168.0.138"; // your computer IP address
    int PORT = 9999;

    /**
     *  Constructor of the class. OnMessagedReceived listens for the messages received from server
     */
    public ClientSocket(final OnMessageReceived listener,String IP, int PORT)
    {
        mMessageListener = listener;
        this.IP = IP;
        this.PORT = PORT;
    }

    /**
     * Sends the message entered by client to the server
     * @param message text entered by client
     */
    public void sendMessage(String message){
        if (out != null && !out.checkError()) {
            System.out.println("message: "+ message);
            out.println(message);
            out.flush();
        }
    }

    public void sendMessage(byte[] message){
        if (out != null && !out.checkError()) {
            System.out.println(message);
            out.println(message);
            out.flush();
        }
    }
    public  boolean sendBytes(byte[] myByteArray,int i) throws IOException {
        try {
            sendBytes(myByteArray, 0, myByteArray.length);
        }catch (Exception e){
            Log.e("Tan log sendBYtes", e.toString());
            return false;
        }
        return true;
    }
    public  void sendBytes(byte[] myByteArray) throws IOException {
        try {
            sendBytes(myByteArray, 0, myByteArray.length);
        }catch (Exception e){
            Log.e("Tan log sendBYtes", e.toString());
        }

    }
//    public boolean sendBytes(ByteBuffer myByteArray) throws IOException {
//
//
//    }
    // private DeviceInfo.SERIAL_CMD TxCmdCode;
    //private UInt16 TxSeq;


    public boolean WriteData(ByteBuffer _sendBuf){
        try {
            DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dout.write(_sendBuf.array());
            dout.close();
        }catch (Exception e)
        {
            return false;
        }
        return true;
    }

    public void sendBytes(byte[] myByteArray, int start, int len) throws IOException {
        if (len < 0) {
            throw new IllegalArgumentException("Negative length not allowed");
        }
        if (start < 0 || start >= myByteArray.length)
            throw new IndexOutOfBoundsException("Out of bounds: " + start);
        // Other checks if needed.

        // May be better to save the streams in the support class;
        // just like the socket variable.
        OutputStream out = socket.getOutputStream();
        DataOutputStream dos =new DataOutputStream(out);
        // dos.writeInt(len);
        if (len > 0) {
            //  buffer.flip();
            dos.write(myByteArray,start,len);
            //  buffer.clear();
            //    Log.e("SentBytes", "Array: " + Arrays.toString(myByteArray) + "Lengh: " + myByteArray.length );

            //Log.e("Sent", "Array: " + myByteArray.clone() + " start:  " + start +" lenght: " + len );
            //  Log.e("sent1", "Lenght: " + myByteArray.length + "  " );
        }
        else
        {

        }
        //   Log.e("sendBytes", "Da Send " + myByteArray);

    }

    public void stopsocket() throws IOException {
        if(connection == true )
        {
            socket.close();
        }else
        {
            Log.e("TCP SI Error", "SI: Error Tan");
        }
    }
    public boolean isConnect(){
        // Log.e("TCP SI Client", "SI: Connecting...");
//        if (connection ==true)
//        {
//            boolean input = socket.isInputShutdown();
//            boolean output = socket.isOutputShutdown();
//            boolean connect = socket.isConnected();
//            boolean bound = socket.isBound();
//        }
        //  boolean input = socket.isInputShutdown();
        ////  boolean output = socket.isOutputShutdown();
        //  boolean connect = socket.isConnected();
        //  boolean bound = socket.isBound();
        //   Log.e("RESPONSE FROM SERVER", "Is Input Shutdown: " + input + " Is Outputshutdown: " + output+" Is Connect: "+connect+" Is bound: " +bound );
        return connection == true;
    }

    public void stopClient() throws IOException {
//        mRun = false;
//        if(out !=null){
//            out.flush();
//            out.close();
//        }
//        if(in!=null)
//        {
//            in.close();
//        }

        try {
            socket.shutdownOutput();
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        socket=null;
//        in=null;
//        out=null;
    }
    public void run() {
        mRun = true;

        try {
            //here you must put your computer's IP address.
            InetAddress serverAddr = InetAddress.getByName(IP);
            Log.e("TCP SI Client", "SI: Connecting...");
            connection = false;
            //create a socket to make the connection with the server
            socket = new Socket(serverAddr, PORT);

            try {

                //send the message to the server
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                Log.e("TCP SI Client", "SI: Sent.");
                Log.e("TCP SI Client", "SI: Done.");
                connection = true;
//                serverMessage = in.readLine();

                //receive the message which the server sends back
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                //in this while the client listens for the messages sent by the server
//                while (mRun) {
//                   //
//
//                    if (serverMessage != null && mMessageListener != null) {
//                        //call the method messageReceived from MyActivity class
//                        //smMessageListener.messageReceived(serverMessage);
//                        //Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + serverMessage + "'");
//                    }
//                    serverMessage = null;
//                }
//                connection = true;
            }
            catch (Exception e)
            {

                connection = false;
                Log.e("TCP SI Error", "SI: Error", e);
                e.printStackTrace();
            }
            finally
            {
                connection = true;

                //the socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                //socket.close();
            }

        } catch (Exception e) {
            connection = false;
            Log.e("TCP SI Error", "SI: Error", e);
        } finally {
//            if (socket != null){
//                try {
//                    socket.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//
//            if (out != null){
//                out.close();
//            }
//
//            if (in != null){
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
        }
    }

    public boolean connectServer() {
        return connection;
    }

    public interface OnMessageReceived {
        void messageReceived(String message);

    }
}
