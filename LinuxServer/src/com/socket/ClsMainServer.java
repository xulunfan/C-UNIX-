package com.socket;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class ClsMainServer {
//找到客户端的主机号，将客户端的名字放入json数组
	public static void main(String[] args) throws IOException {
		int port = 1234;
		TcpServer server = new TcpServer(port) {

			@Override
			public void onConnect(SocketTransceiver client) {
				printInfo(client, "Connect");
			}

			@Override
			public void onConnectFailed() {
				System.out.println("Client Connect Failed");
			}
			  public String hashKeyForDisk(String key) {
			        String cacheKey;
			        try {
			            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			            mDigest.update(key.getBytes());
			            cacheKey = bytesToHexString(mDigest.digest());
			        } catch (NoSuchAlgorithmException e) {
			            cacheKey = String.valueOf(key.hashCode());
			        }
			        return cacheKey;
			    }
			  private String bytesToHexString(byte[] bytes) {
			        StringBuilder sb = new StringBuilder();
			        for (int i = 0; i < bytes.length; i++) {
			            String hex = Integer.toHexString(0xFF & bytes[i]);
			            if (hex.length() == 1) {
			                sb.append('0');
			            }
			            sb.append(hex);
			        }
			        return sb.toString();
			    }


			@Override
			public void onReceive(SocketTransceiver client, String s) throws IOException {
			
				printInfo(client, "Send Data: " + s);//s是json文本
				String file = "/home/xlf/桌面/Android/cunix_project/output.json";
			//	String file = "/home/xlf/桌面/Android/cunix_project/output"+hashKeyForDisk(client.getInetAddress().getHostAddress())+".json";
			      // 创建文件
			  //    file.createNewFile();
			      // creates a FileWriter Object
			      FileWriter writer = new FileWriter(file); 
			      // 向文件写入内容
			      writer.write(s); 
			     // writer.flush();
			      writer.close();
				client.send(s);
			}

			@Override
			public void onDisconnect(SocketTransceiver client) {
				printInfo(client, "Disconnect");
			}

			@Override
			public void onServerStop() {
				System.out.println("--------Server Stopped--------");
			}
		};
		System.out.println("--------Server Started--------");
		server.start();
	}

	static void printInfo(SocketTransceiver st, String msg) {
	System.out.println("Client " + st.getInetAddress().getHostAddress());
		System.out.println("  " + msg);

	}

}
