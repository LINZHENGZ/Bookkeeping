package cn.roidlin.bookkeepingbook.testing;

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

        //鑾峰彇杈撳嚭娴?
        OutputStream os = socket.getOutputStream();//瀛楄妭娴佽緭鍑?
        PrintWriter pw = new PrintWriter(os);//灏嗚緭鍑烘祦鍖呰涓烘墦鍗版祦
        //鑾峰彇瀹㈡埛绔殑ip鍦板潃
        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();

        pw.write("客户端:~" + ip + "~接入服务器！");
        pw.flush();

        socket.shutdownOutput();
        socket.close();

    }
}
