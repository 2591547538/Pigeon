import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SD {
		public final static Map<String, SocketChannel> socketMap = Collections.synchronizedMap(new HashMap<String, SocketChannel>()) ;
	public static void main(String[] args) {
	socketMap.put("Null", null);
		HandlerSelectionKey handler = new HandlerHandlerSelectionKeyImpl();
		try {
			
			ServerSocketChannel server = ServerSocketChannel.open();
			server.configureBlocking(false);
			server.bind(new InetSocketAddress( 5600));
	
			Selector selector = Selector.open();
			server.register(selector, SelectionKey.OP_ACCEPT);
			while (true) {
				int keys = selector.select();
				if (keys > 0) {
					Iterator<SelectionKey> it = selector.selectedKeys().iterator();
				while (it.hasNext()) {
					SelectionKey key = it.next();
						it.remove();
						handler.handler(key, selector);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static interface HandlerSelectionKey {
		public void handler(SelectionKey key, Selector selector) throws IOException;

	}
	public static class HandlerHandlerSelectionKeyImpl implements HandlerSelectionKey {
		@Override
		public void handler(SelectionKey key, Selector selector) throws IOException {
			int keyState = selectionKeyState(key);
			switch (keyState) {
			case SelectionKey.OP_ACCEPT:
				ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
				accept(serverSocketChannel, selector);
				break;
			case SelectionKey.OP_READ:
				SocketChannel readSocketChannel = (SocketChannel) key.channel();
				read(readSocketChannel, selector);
				break;
			}
		}
		private int selectionKeyState(SelectionKey key) {
			if (key.isAcceptable()) {
				return SelectionKey.OP_ACCEPT;
			} else if (key.isReadable()) {
				return SelectionKey.OP_READ;
			}
			return -1;
		}
		private void accept(ServerSocketChannel serverSocketChannel, Selector selector) throws IOException {
			SocketChannel socketChannel = serverSocketChannel.accept();
			socketChannel.configureBlocking(false);
			socketChannel.register(selector, SelectionKey.OP_READ);
		}

		private void read(SocketChannel socketChannel, Selector selector) throws FileNotFoundException {
			ByteBuffer readBuffer = ByteBuffer.allocate(4096);
			ByteBuffer sendBuffer = ByteBuffer.allocate(4096);
			int readBytes;
			try {
				readBytes = socketChannel.read(readBuffer);
				String msg = "";				
				if (readBytes > 0&&readBytes<4095) {	
					msg = new String(readBuffer.array(), 0, readBytes);			
					if (msg.startsWith("aaaa")) {
						String[] s = msg.split("\\|\\|\\|");
					
						if (socketMap.containsKey(s[1])) {
							socketMap.remove(s[1]);
							socketMap.put(s[1], socketChannel);
						} else {
							socketMap.put(s[1], socketChannel);
						}
						String send = msg+"\n";
						sendBuffer.clear();
						sendBuffer = ByteBuffer.wrap(send.getBytes("UTF-8"));
						socketChannel.write(sendBuffer);
					} 
					else if (msg.startsWith("ssss")) {
					
						String[] s = msg.split("\\|\\|\\|");
						String send = msg+"\n";
						if(socketMap.containsKey(s[2])) {
						sendBuffer.clear();
						sendBuffer = ByteBuffer.wrap(send.getBytes("UTF-8"));
						SocketChannel v = socketMap.get(s[2]);
						if(v.isConnected()) {
							v.write(sendBuffer);
						}
						}else {}
					}
					else if (msg.startsWith("tttt")) {
					
						String send = msg+"\n";
					  	   sendBuffer.clear();
							sendBuffer = ByteBuffer.wrap(send.getBytes("UTF-8"));
						for(Map.Entry<String,SocketChannel > entry:socketMap.entrySet()){
							SocketChannel v = entry.getValue();
							if(v==null) {continue;}
				           if(v.isConnected()) {
				        		v.write(sendBuffer);		
				           }
				        }
					}  
				} else if (readBytes < 0) {			
					socketChannel.socket().shutdownInput();
					socketChannel.socket().close();	}
			} catch (IOException e) {	e.printStackTrace();	}
		}
	}
}