package Testing;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import cn.roidlin.bookkeepingbook.R;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class SocketDemo extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_demo);
        Button btn_accept = (Button) findViewById(R.id.btnsend);
        btn_accept.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        new Thread(){
            @Override
            public void run() {
                try {
                    acceptServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void acceptServer() throws IOException {

        Socket socket = new Socket("192.168.2.17",2203);

        //获取输出流
        OutputStream os = socket.getOutputStream();//字节流输出
        PrintWriter pw = new PrintWriter(os);//将输出流包装为打印流
        //获取客户端的ip地址
        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();

        pw.write("客户端：~" +ip+ "~接入服务器！！");
        pw.flush();

        socket.shutdownOutput();
        socket.close();

    }
}